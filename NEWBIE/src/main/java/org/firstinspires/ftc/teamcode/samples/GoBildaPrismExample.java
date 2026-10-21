package org.firstinspires.ftc.teamcode.samples;

import com.gobilda.prism.Prism;
import com.gobilda.prism.animations.Blink;
import com.gobilda.prism.animations.DroidScan;
import com.gobilda.prism.animations.PoliceLights;
import com.gobilda.prism.animations.Rainbow;
import com.gobilda.prism.animations.Snakes;
import com.gobilda.prism.animations.Solid;
import com.gobilda.prism.types.Color;
import com.gobilda.prism.types.LayerHeight;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "goBILDA Prism LED Example", group = "Samples")
public class GoBildaPrismExample extends LinearOpMode {

    private Prism prism;

    @Override
    public void runOpMode() {
        // "prism" must match the name in Driver Station Robot Configuration
        prism = hardwareMap.get(Prism.class, "prism");

        // Configure LED strip parameters
        prism.setStripLength(60);
        prism.setTargetFPS(60);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Default background: Solid Green
        prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, new Solid(Color.GREEN));

        while (opModeIsActive()) {
            // Button X: Multi-color Sections Moving continuously in One Constant Direction
            if (gamepad1.x) {
                Color[] sectionColors = new Color[]{
                        Color.RED,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.WHITE,
                        Color.PURPLE
                };
                // 6 LEDs per color section, 3 blank LEDs between, 0.8 speed
                Snakes movingSections = new Snakes(sectionColors, 4, 1, 0.4f);
                movingSections.setForwardDirection(true); // Moves in one constant direction
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, movingSections);
                telemetry.addData("Pattern", "Button X -> Multi-Color Moving Sections");

            // Button A: Cylon / Droid Scanner Effect (Blue Eye)
            } else if (gamepad1.a) {
                DroidScan scan = new DroidScan(Color.BLUE);
                scan.setSpeed(0.8f);
                scan.setEyeWidth(5);
                scan.setTrailWidth(8);
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, scan);
                telemetry.addData("Pattern", "Button A -> Droid Scanner (Blue)");

            // Button B: Flashing Warning (Red / Black Blink)
            } else if (gamepad1.b) {
                Blink blink = new Blink(Color.RED, Color.BLACK);
                blink.setPeriod(300);
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, blink);
                telemetry.addData("Pattern", "Button B -> Flashing Red Warning");

            // Button Y: Flowing Rainbow
            } else if (gamepad1.y) {
                Rainbow rainbow = new Rainbow();
                rainbow.setSpeed(0.8f);
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, rainbow);
                telemetry.addData("Pattern", "Button Y -> Flowing Rainbow");

            // DPAD Up: Police Emergency Lights
            } else if (gamepad1.dpad_up) {
                PoliceLights police = new PoliceLights();
                police.setPeriod(200);
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, police);
                telemetry.addData("Pattern", "DPAD Up -> Police Strobe");

            // DPAD Down: Solid White Light
            } else if (gamepad1.dpad_down) {
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, new Solid(Color.WHITE));
                telemetry.addData("Pattern", "DPAD Down -> Solid White");

            // Left Bumper: Turn Off / Clear
            } else if (gamepad1.left_bumper) {
                prism.clearAllAnimations();
                telemetry.addData("Pattern", "Left Bumper -> Lights Off");

            // Right Bumper / Idle: Solid Green
            } else if (gamepad1.right_bumper) {
                prism.insertAndUpdateAnimation(LayerHeight.LAYER_0, new Solid(Color.GREEN));
                telemetry.addData("Pattern", "Right Bumper -> Solid Green");
            }

            telemetry.addData("Prism Device ID", prism.getDeviceID());
            telemetry.update();
        }
    }
}
