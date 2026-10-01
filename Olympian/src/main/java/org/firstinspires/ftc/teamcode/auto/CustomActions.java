package org.firstinspires.ftc.teamcode.auto;

import static android.os.SystemClock.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// It's good practice to have a central class for all your custom actions
public class CustomActions {

    public static Action moveMotorToPosition(DcMotor motor, int targetPosition, double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // This is the core logic of a stateful action
                if (!initialized) {
                    // On the first run, set up the motor
                    motor.setTargetPosition(targetPosition);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor.setPower(power);
                    initialized = true;
                }

                // Check if the motor is still busy
                if (motor.isBusy()) {
                    // If it is, report that we are still running
                    packet.put("Motor Ticks", motor.getCurrentPosition());
                    return true;
                } else {
                    // If it's done, stop the motor and report that we are finished
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Or whatever your TeleOp mode is
                    return false;
                }
            }
        };
    }

    public static Action moveMotorTime(DcMotor motor, int time, double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    motor.setPower(-0.75);
                    sleep(time);
                    motor.setPower(0);
                    initialized = true;
                }

                if (motor.isBusy()) {
                    packet.put("Motor Ticks", motor.getCurrentPosition());
                    return true;
                } else {
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    return false;
                }
            }
        };

    }

    }