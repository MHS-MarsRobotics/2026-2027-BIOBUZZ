package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name="Robot Drive")
public class RobotCentricDrive extends LinearOpMode {
    private static final double TICKS_PER_REV = 28.0;
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor Motor_Front_Left = hardwareMap.dcMotor.get( "Motor Front Left"  );
        DcMotor Motor_Front_Right = hardwareMap.dcMotor.get("Motor Front Right" );
        DcMotor Motor_Back_Left = hardwareMap.dcMotor.get(  "Motor Back Left"   );
        DcMotor Motor_Back_Right = hardwareMap.dcMotor.get( "Motor Back Right"  );

        DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");
        DcMotorEx fly = hardwareMap.get(DcMotorEx.class, "fly");

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fly.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);







        Motor_Back_Left.setDirection(DcMotor.Direction.REVERSE);
        Motor_Front_Left.setDirection(DcMotor.Direction.REVERSE);

        Motor_Front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motor_Back_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motor_Front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motor_Back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            Motor_Front_Left.setPower(frontLeftPower);
            Motor_Front_Right.setPower(frontRightPower);
            Motor_Back_Left.setPower(backLeftPower);
            Motor_Back_Right.setPower(backRightPower);

            if (gamepad2.y) {
                fly.setPower(-0.55);
            } else if (gamepad2.x) {
                fly.setPower(0);
            }

            // Button Controls for Intake
            if (gamepad2.a) {
                intake.setPower(-1.0);
            } else if (gamepad2.b) {
                intake.setPower(0);
            }

            double flyTicksPerSec = fly.getVelocity();
            double flyRpm = (flyTicksPerSec / TICKS_PER_REV) * 60.0;
            double flyCurrentAmps = fly.getCurrent(CurrentUnit.AMPS);

            telemetry.addLine("=== FLYWHEEL TELEMETRY ===");
            telemetry.addData("Fly Power", "%.2f", fly.getPower());
            telemetry.addData("Fly Position (Ticks)", fly.getCurrentPosition());
            telemetry.addData("Fly Speed (Ticks/Sec)", "%.1f", flyTicksPerSec);
            telemetry.addData("Fly Speed (RPM)", "%.1f RPM", flyRpm);
            telemetry.addData("Fly Current Draw", "%.2f Amps", flyCurrentAmps);

            // Intake Telemetry
            double intakeCurrentAmps = intake.getCurrent(CurrentUnit.AMPS);

            telemetry.addLine("\n=== INTAKE TELEMETRY ===");
            telemetry.addData("Intake Power", "%.2f", intake.getPower());
            telemetry.addData("Intake Position (Ticks)", intake.getCurrentPosition());
            telemetry.addData("Intake Current Draw", "%.2f Amps", intakeCurrentAmps);

            // Jam Detection Alert (If current > 4 Amps)
            if (intakeCurrentAmps > 4.0) {
                telemetry.addData("INTAKE ALERT", "JAM DETECTED (> 4A)!");
            }

            telemetry.update();

        }
    }

}
