package org.firstinspires.ftc.teamcode.samples;

import android.graphics.Color;
import com.qualcomm.hardware.sparkfun.SparkFunLEDStick;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "goBILDA / Qwiic I2C LED Example", group = "TeleOp")
public class GoBildaPrismExample extends LinearOpMode {

    private SparkFunLEDStick ledStick;

    @Override
    public void runOpMode() {
        // "led_stick" must match the device name in Robot Configuration under I2C Devices
        ledStick = hardwareMap.get(SparkFunLEDStick.class, "led_stick");

        // Set initial brightness (0 to 31) and default color
        ledStick.setBrightness(15);
        ledStick.setColor(Color.GREEN);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Control colors using Gamepad buttons
            if (gamepad1.a) {
                ledStick.setColor(Color.BLUE);
            } else if (gamepad1.b) {
                ledStick.setColor(Color.RED);
            } else if (gamepad1.y) {
                ledStick.setColor(Color.YELLOW);
            } else if (gamepad1.x) {
                // Set individual LED colors in an array (for 10 LEDs)
                int[] customColors = {
                        Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA,
                        Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.WHITE
                };
                ledStick.setColors(customColors);
            } else if (gamepad1.left_bumper) {
                ledStick.turnAllOff();
            } else {
                ledStick.setColor(Color.GREEN);
            }

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
