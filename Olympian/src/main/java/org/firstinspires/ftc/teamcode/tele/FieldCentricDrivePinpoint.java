package org.firstinspires.ftc.teamcode.tele;

// Imports for Road Runner
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

// Imports for FTC SDK
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// Import your own MecanumDrive class
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

@TeleOp(name = "new odo comp Field Drive ")
public class FieldCentricDrivePinpoint extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the MecanumDrive class, which handles hardware setup and PinpointLocalizer
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // CRITICAL: Update the localizer's position estimate from the Pinpoint computer in every loop
            drive.updatePoseEstimate();

            Pose2d currentPose = drive.localizer.getPose();
            double botHeading = currentPose.heading.toDouble();

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
            double y = -gamepad1.left_stick_y * speedMultiplier;
            double x = -gamepad1.left_stick_x * speedMultiplier;
            double rx = -gamepad1.right_stick_x * speedMultiplier;

            // Rotate the movement vector counter to the robot's heading for Field-Centric drive
            double rotationAngle = -botHeading;
            double rotatedX = y * Math.cos(rotationAngle) - x * Math.sin(rotationAngle);
            double rotatedY = y * Math.sin(rotationAngle) + x * Math.cos(rotationAngle);

            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(rotatedX, rotatedY),
                    rx
            ));

            // Telemetry feedback from Pinpoint Computer
            if (drive.localizer instanceof PinpointLocalizer) {
                telemetry.addData("Pinpoint Status", ((PinpointLocalizer) drive.localizer).driver.getDeviceStatus());
            }
            telemetry.addData("X Position", "%.2f in", currentPose.position.x);
            telemetry.addData("Y Position", "%.2f in", currentPose.position.y);
            telemetry.addData("Heading (deg)", "%.2f deg", Math.toDegrees(botHeading));

            telemetry.update();
        }
    }
}
