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

        RoadRunnerBotEntity UPSIDE = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();

        RoadRunnerBotEntity DOWNSIDE = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(14,15)
                .build();


        UPSIDE.runAction(UPSIDE.getDrive().actionBuilder(new Pose2d(-63, 11.6, Math.toRadians(0)))
                .strafeTo(new Vector2d(-59,11.6))
                .turnTo(Math.toRadians(90))
                .splineTo(new Vector2d(0, 40), Math.toRadians(0))
                .splineTo(new Vector2d(36, 63), Math.toRadians(90))
                .build());

        DOWNSIDE.runAction(DOWNSIDE.getDrive().actionBuilder(new Pose2d(63, 63, Math.toRadians(0)))
                .strafeTo(new Vector2d(50,63))
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
                .addEntity(UPSIDE)
                .addEntity(DOWNSIDE)
                .start();


}};
