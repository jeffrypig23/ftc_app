package org.firstinspires.ftc.teamcode.Dragons1;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 11/7/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "6128: Blue Jewel", group = "Official")
//@Disabled
public class BlueJewel extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;
        int armPos = -583;
        int boxPos = -850;

        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        bot.arm.setTargetPosition(armPos);
        bot.box.setTargetPosition(boxPos);

        while (!isThere(bot.box, 10)) {
            if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) >= 10) {
                bot.box.setPower(1.0d);
            } else if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) <= -10) {
                bot.box.setPower(-1.0d);
            } else {
                bot.box.setPower(0);
            }
        }
        bot.box.setPower(0);
        while (!isThere(bot.arm, 7)) {
            if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) >= 10) {
                bot.arm.setPower(1);
            } else if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) <= -10) {
                bot.arm.setPower(-1);
            } else {
                bot.arm.setPower(0);
            }
        }
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.leftServo.setPosition(bot.leftDown);
                time.reset();
                stageNumber++;
            } else if (stageNumber == 1) {
                if (time.seconds() < 2) {
                    //idle();
                } else {
                    stageNumber = 2;
                }
            } else if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.leftColorSensor.red() > bot.leftColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so dont add the color value
                    stageNumber++;
                }
            } else if (stageNumber == 5) {
                if (((int) Math.round(colorValue / 3)) == 1) {
                    color = "Red";
                    bot.app.post(new Runnable() {
                        public void run() {
                            bot.app.setBackgroundColor(Color.argb(bot.leftColorSensor.alpha(), bot.leftColorSensor.red(), bot.leftColorSensor.green(), bot.leftColorSensor.blue()));
                        }
                    });
                    stageNumber = 6;
                } else {
                    color = "Blue";
                    bot.app.post(new Runnable() {
                        public void run() {
                            bot.app.setBackgroundColor(Color.argb(bot.leftColorSensor.alpha(), bot.leftColorSensor.red(), bot.leftColorSensor.green(), bot.leftColorSensor.blue()));
                        }
                    });
                    stageNumber = 6;
                }
            } else if (stageNumber == 6) {
                if (color.equals("Red")) {
                    //driveToPosition(bot.left, 2);
                    bot.left.setPower(0);
                    driveToPosition(bot.right, -4);
                    stageNumber++;
                } else {
                    //driveToPosition(bot.left, -2);
                    bot.left.setPower(-1);
                    bot.right.setTargetPosition(-300);
                    //driveToPosition(bot.right, 0.4d);
                    stageNumber++;
                }
            } else if (stageNumber == 7) {
                if (isThere(bot.right, 100)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.leftServo.setPosition(bot.leftUp);
                    stageNumber++;
                }

            } else if (stageNumber == 8) {
                if (color.equals("Red")) {
                    //driveToPosition(bot.left, 1);
                    bot.left.setPower(0);
                    driveToPosition(bot.right, 4);
                    stageNumber++;
                } else {
                    //driveToPosition(bot.left, -1);
                    bot.left.setPower(-1);
                    bot.right.setTargetPosition(-2700);
                    //driveToPosition(bot.right, -0.4d);
                    stageNumber++;
                }
            } else if (stageNumber == 9) {
                if (isThere(bot.right, 100)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 10) {
                //stop();
                if (color.equals("Red")) {
                    bot.left.setPower(-1);
                    bot.right.setTargetPosition(-3000);
                    stageNumber++;
                } else {
                    // Done!
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 11) {
                if (isThere(bot.right, 100)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.leftServo.setPosition(bot.leftUp);
                    stageNumber++;
                }
            } else if (stageNumber == 12) {
                // Done!
                bot.left.setPower(0);
                bot.right.setPower(0);
                stop();
            }

            /*
            if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) >= 10) {
                bot.left.setPower(1);
            } else if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) <= -10) {
                bot.left.setPower(-1);
            } else {
                bot.left.setPower(0);
            }
            */

            if (stageNumber != 12) {
                if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                    bot.right.setPower(1);
                } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                    bot.right.setPower(-1);
                } else {
                    bot.right.setPower(0);
                }
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Color", color)
                    .addData("Time", time.seconds())
                    .addData("Red value", bot.leftColorSensor.red())
                    .addData("Blue value", bot.leftColorSensor.blue())
                    .addData("", "").addData("lefFront pos", bot.left.getCurrentPosition())
                    .addData("left target", bot.left.getTargetPosition())
                    .addData("left ∆", Math.abs(bot.left.getTargetPosition() - bot.left.getCurrentPosition()))
                    .addData("", "").addData("right pos", bot.right.getCurrentPosition())
                    .addData("right target", bot.right.getTargetPosition())
                    .addData("right ∆", Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()));
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }
}
