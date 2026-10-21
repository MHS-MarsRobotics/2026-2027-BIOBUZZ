package com.gobilda.prism;

import com.gobilda.prism.animations.AnimationBase;
import com.gobilda.prism.animations.Blink;
import com.gobilda.prism.animations.DroidScan;
import com.gobilda.prism.animations.PoliceLights;
import com.gobilda.prism.animations.Pulse;
import com.gobilda.prism.animations.Rainbow;
import com.gobilda.prism.animations.Snakes;
import com.gobilda.prism.animations.Solid;
import com.gobilda.prism.types.Color;
import com.gobilda.prism.types.LayerHeight;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

@I2cDeviceType()
@DeviceProperties(
        name = "goBILDA® Prism LED Driver",
        xmlTag = "goBILDAPrism",
        description = "goBILDA® Prism Addressable LED Controller"
)
public class Prism extends I2cDeviceSynchDevice<I2cDeviceSynchSimple> {

    public static final I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x38);

    private int stripLength = 60;
    private int targetFPS = 60;

    public Prism(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);
        deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);
        super.registerArmingStateCallback(false);
    }

    public Prism(I2cDeviceSynchSimple deviceClient) {
        this(deviceClient, true);
    }

    @Override
    protected boolean doInitialize() {
        return true;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.GoBilda;
    }

    @Override
    public String getDeviceName() {
        return "goBILDA® Prism LED Driver";
    }

    public int getDeviceID() {
        byte[] data = deviceClient.read(0x00, 1);
        if (data != null && data.length > 0) {
            return data[0] & 0xFF;
        }
        return 3;
    }

    public void setStripLength(int length) {
        this.stripLength = length;
        byte[] data = new byte[]{0, 0, (byte) length, 0x01};
        writeRegister(0x06, data);
    }

    public int getStripLength() {
        return stripLength;
    }

    public void setTargetFPS(int fps) {
        this.targetFPS = fps;
        byte top8 = (byte) (((fps >> 8) | 0x80) & 0xFF);
        byte btm8 = (byte) (fps & 0xFF);
        byte[] data = new byte[]{btm8, top8, 0, 0};
        writeRegister(0x06, data);
    }

    public boolean insertAnimation(LayerHeight height, AnimationBase animation) {
        if (height == LayerHeight.LAYER_DISABLED || animation == null) {
            return false;
        }
        return true;
    }

    public boolean insertAndUpdateAnimation(LayerHeight height, AnimationBase animation) {
        if (height == LayerHeight.LAYER_DISABLED || animation == null) {
            return false;
        }

        int slotReg = 0x08 + height.getValue();

        // 1. Write Animation Selection Sub-register (0x00)
        writeSubRegisterByte(slotReg, 0x00, animation.getAnimationType());

        // 2. Write Brightness Sub-register (0x01)
        writeSubRegisterByte(slotReg, 0x01, animation.getBrightness());

        // 3. Write StartIndex (0x02) and StopIndex (0x03)
        writeSubRegisterByte(slotReg, 0x02, animation.getStartIndex());
        writeSubRegisterByte(slotReg, 0x03, animation.getStopIndex());

        // 4. Write Animation-specific parameters
        if (animation instanceof Solid) {
            Solid solid = (Solid) animation;
            Color c = solid.getPrimaryColor();
            if (c != null) {
                writeRegister(slotReg, new byte[]{0x04, (byte) c.red, (byte) c.green, (byte) c.blue});
            }
        } else if (animation instanceof Snakes) {
            Snakes snakes = (Snakes) animation;
            Color[] colors = snakes.getColors();
            int count = colors != null ? Math.min(10, colors.length) : 0;

            // Sub-register 0x04: ColorsUsed
            writeSubRegisterByte(slotReg, 0x04, count);

            // Sub-register 0x05: Color Array Packet
            if (count > 0) {
                byte[] colorPacket = new byte[1 + count * 3];
                colorPacket[0] = 0x05; // sub-register 0x05
                for (int i = 0; i < count; i++) {
                    colorPacket[1 + i * 3] = (byte) colors[i].red;
                    colorPacket[1 + i * 3 + 1] = (byte) colors[i].green;
                    colorPacket[1 + i * 3 + 2] = (byte) colors[i].blue;
                }
                writeRegister(slotReg, colorPacket);
            }

            // Sub-registers 0x07 (Direction), 0x08 (SpacingBetween), 0x0B (SnakeLength)
            writeSubRegisterByte(slotReg, 0x07, snakes.isForwardDirection() ? 1 : 0);
            writeSubRegisterByte(slotReg, 0x08, snakes.getSpacingBetween());
            writeSubRegisterByte(slotReg, 0x0B, snakes.getSnakeLength());

        } else if (animation instanceof Blink) {
            Blink blink = (Blink) animation;
            Color c1 = blink.getPrimaryColor();
            Color c2 = blink.getSecondaryColor();
            if (c1 != null) {
                writeRegister(slotReg, new byte[]{0x04, (byte) c1.red, (byte) c1.green, (byte) c1.blue});
            }
            if (c2 != null) {
                writeRegister(slotReg, new byte[]{0x05, (byte) c2.red, (byte) c2.green, (byte) c2.blue});
            }
        } else if (animation instanceof Pulse) {
            Pulse pulse = (Pulse) animation;
            Color c1 = pulse.getPrimaryColor();
            Color c2 = pulse.getSecondaryColor();
            if (c1 != null) {
                writeRegister(slotReg, new byte[]{0x04, (byte) c1.red, (byte) c1.green, (byte) c1.blue});
            }
            if (c2 != null) {
                writeRegister(slotReg, new byte[]{0x05, (byte) c2.red, (byte) c2.green, (byte) c2.blue});
            }
        } else if (animation instanceof DroidScan) {
            DroidScan scan = (DroidScan) animation;
            Color c1 = scan.getPrimaryColor();
            if (c1 != null) {
                writeRegister(slotReg, new byte[]{0x04, (byte) c1.red, (byte) c1.green, (byte) c1.blue});
            }
            writeSubRegisterByte(slotReg, 0x07, scan.getEyeWidth());
            writeSubRegisterByte(slotReg, 0x08, scan.getTrailWidth());
        } else if (animation instanceof Rainbow) {
            writeSubRegisterByte(slotReg, 0x08, 25);
        } else if (animation instanceof PoliceLights) {
            writeRegister(slotReg, new byte[]{0x07, (byte) Color.RED.red, (byte) Color.RED.green, (byte) Color.RED.blue});
            writeRegister(slotReg, new byte[]{0x08, (byte) Color.WHITE.red, (byte) Color.WHITE.green, (byte) Color.WHITE.blue});
            writeRegister(slotReg, new byte[]{0x09, (byte) Color.BLUE.red, (byte) Color.BLUE.green, (byte) Color.BLUE.blue});
        }

        return true;
    }

    public void clearAllAnimations() {
        byte[] clearData = new byte[]{0x00, 0x00, 0x00, 0x02};
        writeRegister(0x06, clearData);
    }

    private void writeSubRegisterByte(int slotReg, int subReg, int value) {
        byte[] data = new byte[]{(byte) subReg, (byte) value};
        writeRegister(slotReg, data);
    }

    public void writeRegister(int mainReg, byte[] data) {
        deviceClient.write(mainReg, data);
    }
}
