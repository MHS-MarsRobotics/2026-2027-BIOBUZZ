package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="forward")
public class Forward extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {



        DcMotor Motor_Front_Left = hardwareMap.dcMotor.get("Motor Front Left");
        DcMotor Motor_Front_Right = hardwareMap.dcMotor.get("Motor Front Right");
        DcMotor Motor_Back_Left = hardwareMap.dcMotor.get("Motor Back Left");
        DcMotor Motor_Back_Right = hardwareMap.dcMotor.get("Motor Back Right");

        Motor_Back_Left.setDirection(DcMotor.Direction.REVERSE);
        Motor_Front_Left.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        ElapsedTime runtime = new ElapsedTime();

        if (isStopRequested()) return;
        while (opModeIsActive()){



            if (runtime.seconds()  > 0 && runtime.seconds() <0.25){
                Motor_Back_Left.setPower(1);
                Motor_Front_Left.setPower(1);
                Motor_Back_Right.setPower(1);
                Motor_Front_Right.setPower(1);
            }
            else {
                Motor_Back_Left.setPower(0);
                Motor_Front_Left.setPower(0);
                Motor_Back_Right.setPower(0);
                Motor_Front_Right.setPower(0);
            }
        }
    }
}
