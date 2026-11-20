package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="Main Field Drive", group="TeleOp")
public class Main_FieldCentricDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotorEx flywheel = (DcMotorEx) hardwareMap.dcMotor.get("flywheel");
        CRServo feederL = hardwareMap.crservo.get("feederL");
        CRServo feederR = hardwareMap.crservo.get("feederR");

        //boolean revflywheelActive = false;
        //boolean lastDDownPressed = false;

        boolean flywheelActive = false;
        boolean lastAPressed = false;

        //boolean revintakeActive = false;
        //boolean lastDLeftPressed = false;

        boolean intakeActive = false;
        boolean lastBPressed = false;

        //boolean revfeederLActive = false;
        //boolean revfeederRActive = false;
        //boolean lastDRightPressed = false;

        boolean feederLActive = false;
        boolean feederRActive = false;
        boolean lastXPressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // CRITICAL: Update localizer pose estimate in every loop
            drive.updatePoseEstimate();

            Pose2d currentPose = drive.localizer.getPose();
            double botHeading = currentPose.heading.toDouble();

            // Reset heading on Options button press
            if (gamepad1.options) {
                drive.localizer.setPose(new Pose2d(currentPose.position, 0));
            }

            // Speed multiplier settings
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

            // Rotate movement vector counter to robot's heading for Field-Centric drive
            double rotationAngle = -botHeading;
            double rotatedX = y * Math.cos(rotationAngle) - x * Math.sin(rotationAngle);
            double rotatedY = y * Math.sin(rotationAngle) + x * Math.cos(rotationAngle);

            Vector2d driveVector = new Vector2d(rotatedX, rotatedY);

            drive.setDrivePowers(
                    new PoseVelocity2d(
                            driveVector,
                            rx
                    )
             );

            double dirMultiplier = gamepad2.dpad_down ? -1.0 : 1.0;

            // Toggle Intake Control (Gamepad 2 B)
            if (gamepad2.b && !lastBPressed) {
                intakeActive = !intakeActive;
            }
            lastBPressed = gamepad2.b;


            if (intakeActive) {
                intake.setPower(dirMultiplier);
            } else {
                intake.setPower(0);
            }

            // Toggle Flywheel Control (Gamepad 2 A)
            if (gamepad2.a && !lastAPressed) {
                flywheelActive = !flywheelActive;
            }
            lastAPressed = gamepad2.a;


            if (flywheelActive) {
                flywheel.setPower(0.55 * dirMultiplier);
            } else {
                flywheel.setPower(0);
            }

            // Toggle Feeder Servos Control (Gamepad 2 X)
            if (gamepad2.x && !lastXPressed) {
                feederLActive = !feederLActive;
                feederRActive = !feederRActive;
            }
            lastXPressed = gamepad2.x;


            if (feederLActive) {
                feederL.setPower(-dirMultiplier);
            } else {
                feederL.setPower(0);
            }

            if (feederRActive) {
                feederR.setPower(dirMultiplier);
            } else {
                feederR.setPower(0);
            }

            // Drivetrain Telemetry Feedback
            telemetry.addData("X Position", "%.2f in", currentPose.position.x);
            telemetry.addData("Y Position", "%.2f in", currentPose.position.y);
            telemetry.addData("Heading (deg)", "%.2f deg", Math.toDegrees(botHeading));

            telemetry.update();
        }
    }
}
