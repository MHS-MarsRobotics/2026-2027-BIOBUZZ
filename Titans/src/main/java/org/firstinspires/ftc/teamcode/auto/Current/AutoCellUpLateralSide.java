package org.firstinspires.ftc.teamcode.auto.Current;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;


@Autonomous(name = "Cell Up Lat Side", group = "Autonomous")
public class AutoCellUpLateralSide extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(11.6, -63, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(35,-35))
                .strafeTo(new Vector2d(35,-12))
                .turnTo(Math.toRadians(180))
                .waitSeconds(1)
                .turnTo(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-30.5,-63,Math.toRadians(90)),Math.toRadians(270));

        /*Action tab2 = tab1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-90,-8),Math.toRadians(270))
                .build();*/

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(
                tab1.build()));


    }
}
