package org.firstinspires.ftc.teamcode.tele;

// Imports for Road Runner
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

// Imports for FTC SDK
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// Import your own MecanumDrive class
import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name = "new odo comp Field Drive ")
public class FieldCentricDrivePinpoint extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the MecanumDrive class, which handles all hardware setup
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        DcMotor arm = hardwareMap.dcMotor.get("arm");
        Servo intake_0 = hardwareMap.servo.get("intake_0");
        Servo intake_1 = hardwareMap.servo.get("intake_1");


        // Wait for the start button to be pressed


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {


            double speedMultiplier = 1.0;

            if (gamepad1.right_trigger >0) {
                speedMultiplier = 0.5;
            }
            if (gamepad1.right_bumper) {
                speedMultiplier = 0.25;
            }

            drive.updatePoseEstimate();

            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * speedMultiplier,
                            -gamepad1.left_stick_x * speedMultiplier
                    ),
                    -gamepad1.right_stick_x * speedMultiplier
            ));

            if (gamepad2.a){
                arm.setPower(-0.75);
            }
            else if (gamepad2.x){
                arm.setPower(0.55);
            }
            else {
                arm.setPower(0);
            }

            if (gamepad2.dpad_up){
                intake_0.setPosition(0.7);
                intake_1.setPosition(0.3);
            }
             if (gamepad2.dpad_right){
                intake_0.setPosition(0.6);
                intake_1.setPosition(0.4);
            }
             if (gamepad2.dpad_down){
                intake_0.setPosition(0.3);
                intake_1.setPosition(0.7);
            }





            // CRITICAL: Update the localizer's position estimate in every loop


            Pose2d currentPose = drive.localizer.getPose();

            // Optional: Telemetry
            telemetry.addData("x", currentPose.position.x);
            telemetry.addData("y", currentPose.position.y);

            // Convert radians to degrees for easier reading
            telemetry.addData("heading (deg)", Math.toDegrees(currentPose.heading.toDouble()));

            telemetry.update();
        }
    }
}