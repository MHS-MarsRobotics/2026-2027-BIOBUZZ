package com.example.meepmeeptesting2;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.core.entity.Entity;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class OlympianMeepMeep {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity REDFAR = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();

        RoadRunnerBotEntity REDGOAL = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();

        RoadRunnerBotEntity BLUEFAR = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .setColorScheme(new ColorSchemeBlueDark())
                .build();

        RoadRunnerBotEntity BLUEGOAL = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .setColorScheme(new ColorSchemeBlueDark())
                .build();

        REDFAR.runAction(REDFAR.getDrive().actionBuilder(new Pose2d(61.3, 14.4, Math.toRadians(180)))
                .splineTo(new Vector2d(-47, 49), Math.toRadians(185))
                .strafeToLinearHeading(new Vector2d(-52,8),Math.toRadians(90))
                .build());



        REDGOAL.runAction(REDGOAL.getDrive().actionBuilder(new Pose2d( -50, 50,Math.toRadians(135)))
                .strafeTo(new Vector2d(-47,49))
                .strafeToLinearHeading(new Vector2d(-52,8),Math.toRadians(90))
                .build());



        BLUEFAR.runAction(BLUEFAR.getDrive().actionBuilder(new Pose2d(61.3, -14.4, Math.toRadians(180)))
                .splineTo(new Vector2d(-47, -49), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(-52,-8),Math.toRadians(270))
                .build());



        BLUEGOAL.runAction(BLUEGOAL.getDrive().actionBuilder(new Pose2d( -50, -50,Math.toRadians(225)))
                .strafeTo(new Vector2d(-47,-49))
                .strafeToLinearHeading(new Vector2d(-52,-8),Math.toRadians(270))
                .build());



        Image bgImage = null;
        try {
            File f = new File("src/main/resources/BIOBUZZ.png");
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
            meepMeep.setBackground(MeepMeep.Background.GRID_BLUE);
        }

        meepMeep.setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(REDFAR)
                .addEntity(REDGOAL)
                .addEntity(BLUEFAR)
                .addEntity(BLUEGOAL)
                .start();


}};
