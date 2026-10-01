package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class TitanMeepMeep {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        //Olympian rubber band launch bot

        //Initialize Bot to blue goal
        RoadRunnerBotEntity myBotToBlueGoal = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(14,15)
                .build();
        //Initialize Bot to red goal
        RoadRunnerBotEntity myBotToRedGoal = new DefaultBotBuilder(meepMeep)

                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(14,15)
                .build();

       // Drive to Blue Goal
        myBotToBlueGoal.runAction(myBotToBlueGoal.getDrive().actionBuilder(new Pose2d(63, -12, Math.toRadians(0)))
                        .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(47,-0),Math.toRadians(210))
                        .waitSeconds(1)
                        .strafeToLinearHeading(new Vector2d(63.4,-34),Math.toRadians(270))
                .build());

        // Drive to Red Goal
        myBotToRedGoal.runAction(myBotToRedGoal.getDrive().actionBuilder(new Pose2d(63, 12, Math.toRadians(0)))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(47,0),Math.toRadians(150))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(63.4,34),Math.toRadians(90))
                .build());



     //Titan Far launch bot

        //Initialize Bot to blue goal
        RoadRunnerBotEntity TitanToBlueGoal = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();

        //Initialize Bot to red goal
        RoadRunnerBotEntity TitanToRedGoal = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();

        // Drive to Blue Goal
        TitanToBlueGoal.runAction(TitanToBlueGoal.getDrive().actionBuilder(new Pose2d(-50, -50, Math.toRadians(229)))
                .waitSeconds(2)
                .strafeTo(new Vector2d(-20,-20))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-63.4,-8),Math.toRadians(270))
                .build());

        TitanToRedGoal.runAction(TitanToRedGoal.getDrive().actionBuilder(new Pose2d(-50, 50, Math.toRadians(129)))
                .waitSeconds(2)
                .strafeTo(new Vector2d(-34,15))
                .waitSeconds(1)
                .turnTo(Math.toRadians(225))
                .waitSeconds(1)
                .splineTo(new Vector2d(38,-62),Math.toRadians(0))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-34,-62),Math.toRadians(90))
                //.strafeToLinearHeading(new Vector2d(-63.4,8),Math.toRadians(90))
                .build());

        Image bgImage = null;
        try {
            File f = new File("src/main/resources/BIOBUZZ.png");
            if (!f.exists()) f = new File("TitanMeepMeep/src/main/resources/BIOBUZZ.png");
            if (!f.exists()) f = new File("OlympianMeepMeep/src/main/resources/BIOBUZZ.png");
            if (!f.exists()) f = new File("BIOBUZZ.png");
            if (f.exists()) {
                bgImage = ImageIO.read(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bgImage != null) {
            meepMeep.setBackground(bgImage);
        } else {
            System.err.println("\n\n=======================================================");
            System.err.println("⚠️ ERROR: COULD NOT LOAD BIOBUZZ.png");
            System.err.println("If you just renamed 'BIOBUZZ_FIELD.webp' to 'BIOBUZZ.png',");
            System.err.println("the file is still a WebP image internally, which Java cannot read.");
            System.err.println("Please CONVERT the image to a TRUE .png or .jpg using a tool");
            System.err.println("like Paint, Photoshop, or an online image converter.");
            System.err.println("=======================================================\n\n");
            meepMeep.setBackground(MeepMeep.Background.GRID_BLUE);
        }

        meepMeep.setDarkMode(true)
                .setBackgroundAlpha(0.95f)
              //  .addEntity(TitanToBlueGoal)
                .addEntity(TitanToRedGoal)
              //.addEntity(myBotToBlueGoal)
              //.addEntity(myBotToRedGoal)
                .start();


    }
}