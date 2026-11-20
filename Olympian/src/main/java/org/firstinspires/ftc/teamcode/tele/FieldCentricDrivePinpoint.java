package org.firstinspires.ftc.teamcode.tele;

// Imports for Road Runner
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

// Imports for FTC SDK
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

// Import your own MecanumDrive class
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

@TeleOp(name = "Main Drive ")
public class FieldCentricDrivePinpoint extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the MecanumDrive class, which handles hardware setup and PinpointLocalizer
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        DcMotor flywheel = hardwareMap.dcMotor.get("flywheel");
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        CRServo feeder = hardwareMap.crservo.get("feeder");
        CRServo feeder2 = hardwareMap.crservo.get("feeder2");
        CRServo corner_SR = hardwareMap.crservo.get("flower");

        boolean isFieldCentric = true;
        boolean lastBackPressed = false;

        boolean flywheelActive = false;
        boolean lastYPressed = false;

        // Base flywheel power and D-pad increment step
        double basePower = 0.48;
        double powerStep = 0.02;
        boolean lastDpadUpPressed = false;
        boolean lastDpadDownPressed = false;

        boolean intakeActive = false;
        boolean lastBPressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // CRITICAL: Update the localizer's position estimate from the Pinpoint computer in every loop
            drive.updatePoseEstimate();

            Pose2d currentPose = drive.localizer.getPose();
            double botHeading = currentPose.heading.toDouble();

            // Gamepad 1 Back Button: Toggle between Field-Centric and Robot-Centric Drive Modes
            if (gamepad1.back && !lastBackPressed) {
                isFieldCentric = !isFieldCentric;
            }
            lastBackPressed = gamepad1.back;

            // Reset heading on options button press
            if (gamepad1.options) {
                if (drive.localizer instanceof PinpointLocalizer) {
                    ((PinpointLocalizer) drive.localizer).driver.resetPosAndIMU();
                }
                drive.localizer.setPose(new Pose2d(currentPose.position, 0));
            }

            double speedMultiplier;
            if (gamepad1.right_bumper) {
                speedMultiplier = 0.25;
            } else if (gamepad1.right_trigger > 0) {
                speedMultiplier = 0.5;
            } else {
                speedMultiplier = 1.0;
            }

            // Driver joystick inputs
            double forward = -gamepad1.left_stick_y * speedMultiplier;
            double strafe = -gamepad1.left_stick_x * speedMultiplier;
            double rx = -gamepad1.right_stick_x * speedMultiplier;

            Vector2d driveVector;

            if (isFieldCentric) {
                // Rotate movement vector counter to robot's heading for Field-Centric drive
                double rotationAngle = -botHeading;
                double rotatedX = forward * Math.cos(rotationAngle) - strafe * Math.sin(rotationAngle);
                double rotatedY = forward * Math.sin(rotationAngle) + strafe * Math.cos(rotationAngle);
                driveVector = new Vector2d(rotatedX, rotatedY);
            } else {
                // Use raw forward/strafe inputs directly for Robot-Centric drive
                driveVector = new Vector2d(forward, strafe);
            }

            drive.setDrivePowers(new PoseVelocity2d(
                    driveVector,
                    rx
            ));

            // Gamepad 2 Back Button Hold: Reverses direction of mechanisms when held
            double dirMultiplier = gamepad2.back ? -1.0 : 1.0;

            // Gamepad 2: Base Flywheel Power Adjustment (Dpad Up / Dpad Down)
            if (gamepad2.dpad_up && !lastDpadUpPressed) {
                basePower = Math.min(1.0, basePower + powerStep);
            }
            lastDpadUpPressed = gamepad2.dpad_up;

            if (gamepad2.dpad_down && !lastDpadDownPressed) {
                basePower = Math.max(0.0, basePower - powerStep);
            }
            lastDpadDownPressed = gamepad2.dpad_down;

            // Gamepad 2 Left Stick Y Modifier (Pushing stick UP adds positive power, DOWN reduces power)
            double modifier = -gamepad2.left_stick_y * 0.25; // Stick modifier range ±0.25

            // Total Flywheel Power = Base Power + Left Stick Modifier
            double totalPower = Math.max(0.0, Math.min(1.0, basePower + modifier));

            // Toggle Intake Control (Gamepad 2 B) - Direction reversed when gamepad2.back held
            if (gamepad2.b && !lastBPressed) {
                intakeActive = !intakeActive;
            }
            lastBPressed = gamepad2.b;

            if (intakeActive) {
                intake.setPower(dirMultiplier);
            } else {
                intake.setPower(0);
            }

            // Toggle Flywheel Control (Gamepad 2 Y) - Direction reversed when gamepad2.back held
            if (gamepad2.y && !lastYPressed) {
                flywheelActive = !flywheelActive;
            }
            lastYPressed = gamepad2.y;

            if (flywheelActive) {
                flywheel.setPower(totalPower * dirMultiplier);
            } else {
                flywheel.setPower(0);
            }

            // Feeder Servos Control (Gamepad 2 A) - Direction reversed when gamepad2.back held
            if (gamepad2.a) {
                feeder.setPower(dirMultiplier);
                feeder2.setPower(dirMultiplier);
            } else {
                feeder.setPower(0);
                feeder2.setPower(0);
            }

            // Corner Servo Control (Gamepad 2 X) - Direction reversed when gamepad2.back held
            if (gamepad2.x) {
                corner_SR.setPower(dirMultiplier);
            } else {
                corner_SR.setPower(0);
            }

            // Telemetry feedback
            telemetry.addData("Drive Mode", isFieldCentric ? "FIELD-CENTRIC [Back to Switch]" : "ROBOT-CENTRIC [Back to Switch]");
            telemetry.addData("Mechanism Direction", gamepad2.back ? "REVERSED (-1x)" : "NORMAL (+1x)");
            if (drive.localizer instanceof PinpointLocalizer) {
                telemetry.addData("Pinpoint Status", ((PinpointLocalizer) drive.localizer).driver.getDeviceStatus());
            }
            telemetry.addData("X Position", "%.2f in", currentPose.position.x);
            telemetry.addData("Y Position", "%.2f in", currentPose.position.y);
            telemetry.addData("Heading (deg)", "%.2f deg", Math.toDegrees(botHeading));
            telemetry.addData("Flywheel Active", flywheelActive);
            telemetry.addData("Base Power", "%.2f", basePower);
            telemetry.addData("Left Stick Modifier", "%+.2f", modifier);
            telemetry.addData("Total Flywheel Power", "%.2f (Dir: %+.0fx)", totalPower, dirMultiplier);

            telemetry.update();
        }
    }
}
