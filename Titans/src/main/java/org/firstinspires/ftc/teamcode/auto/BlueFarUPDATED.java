package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "BlueFar", group = "Autonomous")
public class BlueFarUPDATED extends LinearOpMode {
    public class arm {
        public Action Armdown;
        private DcMotor arm;

        public arm(HardwareMap hardwareMap) {
            arm = hardwareMap.get(DcMotorEx.class, "launch");
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        public class Armdown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    arm.setPower(-0.8);
                    sleep(3000);
                    arm.setPower(0);
                    initialized = true;
                }

                return arm.isBusy();
            }
        }

        public Action Armdown() {
            return new Armdown();
        }
    }



    public void runOpMode() {
        Pose2d initialPose = new Pose2d( 61.3,-14.4, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        arm arm = new arm(hardwareMap);

        // vision here that outputs position
        int visionOutputPosition = 1;

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(27,5),Math.toRadians(232));


        Action tab2 = tab1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(63.4,-34),Math.toRadians(270))

                
                .waitSeconds(3)
                .build();

        waitForStart();

        if (isStopRequested()) return;



        Actions.runBlocking(
                new SequentialAction(
                        tab1.build(),
                        arm.Armdown(),
                        tab2
                )
        );

    }
}