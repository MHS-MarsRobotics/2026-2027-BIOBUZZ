package org.firstinspires.ftc.teamcode.auto.Current;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;


@Autonomous(name = "Cell Down Side", group = "Autonomous")
public class AutoCellDownSide extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d( 63,63, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(50, 63));

        /*Action tab2 = tab1.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-90,-8),Math.toRadians(270))
                .build();*/

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(
                tab1.build()));


    }

}
