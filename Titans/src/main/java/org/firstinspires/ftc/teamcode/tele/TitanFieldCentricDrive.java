package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="Titan Field Drive")
public class  TitanFieldCentricDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor Motor_Front_Left = hardwareMap.dcMotor.get( "Motor Front Left"  );
        DcMotor Motor_Front_Right = hardwareMap.dcMotor.get("Motor Front Right" );
        DcMotor Motor_Back_Left = hardwareMap.dcMotor.get(  "Motor Back Left"   );
        DcMotor Motor_Back_Right = hardwareMap.dcMotor.get( "Motor Back Right"  );
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor flywheel = hardwareMap.dcMotor.get("flywheel");
        CRServo feederL = hardwareMap.crservo.get("feederL");
        CRServo feederR = hardwareMap.crservo.get("feederR");





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
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        // Change names accordingly
        boolean flywheelActive = false;
        boolean lastAPressed = false;

        boolean intakeActive = false;
        boolean lastBPressed = false;

        boolean feederLActive = false;
        boolean feederRActive = false;
        boolean lastXPressed = false;


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = -rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            Motor_Front_Left.setPower(frontLeftPower);
            Motor_Front_Right.setPower(frontRightPower);
            Motor_Back_Left.setPower(backLeftPower);
            Motor_Back_Right.setPower(backRightPower);



            if (gamepad2.b && !lastBPressed) {
                intakeActive = !intakeActive;
            }
            lastBPressed = gamepad2.b;

            if (intakeActive) intake.setPower(-1);
            else intake.setPower(0);



            if (gamepad2.a && !lastAPressed) {
                flywheelActive = !flywheelActive;
            }
            lastAPressed = gamepad2.a;

            if (flywheelActive) flywheel.setPower(.5);
            else flywheel.setPower(0);



            if (gamepad2.x && !lastXPressed) {
                feederLActive = !feederLActive;
                feederRActive = !feederRActive;
            }
            lastXPressed = gamepad2.x;

            if (feederLActive) feederL.setPower(1);
            else feederL.setPower(0);

            if (feederRActive) feederR.setPower(-1);
            else feederR.setPower(0);

        }
    }

}
