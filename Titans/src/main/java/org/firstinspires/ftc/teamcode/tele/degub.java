package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="deug")
public  class degub extends LinearOpMode {


    public void runOpMode() throws InterruptedException {

        DcMotor Motor_Front_Left = hardwareMap.dcMotor.get( "Motor Front Left"  );
        DcMotor Motor_Front_Right = hardwareMap.dcMotor.get("Motor Front Right" );
        DcMotor Motor_Back_Left = hardwareMap.dcMotor.get(  "Motor Back Left"   );
        DcMotor Motor_Back_Right = hardwareMap.dcMotor.get( "Motor Back Right"  );

        CRServo Cylinder  = hardwareMap.crservo.get("Cylinder");


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {


            if (gamepad1.a)Motor_Back_Left.setPower(1);
            if (gamepad1.x)Motor_Front_Left.setPower(1);
            if (gamepad1.b)Motor_Back_Right.setPower(1);
            if (gamepad1.y)Motor_Front_Right.setPower(1);

            else{
                        Motor_Front_Left.setPower(0);
                        Motor_Front_Right.setPower(0);
                        Motor_Back_Left.setPower(0);
                        Motor_Back_Right.setPower(0) ;
            }


            if (gamepad2.a){
                Cylinder.setDirection(DcMotorSimple.Direction.FORWARD);
                Cylinder.setPower(1);
            }
            if (gamepad2.x){
                Cylinder.setPower(-1);
            }

        }
    }

}
