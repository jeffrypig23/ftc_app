package org.firstinspires.ftc.teamcode.Dragons1;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.turn;

/**
 * Created by Stephen Ogden on 12/14/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
@Disabled
@Autonomous(name = "Red Jewel and cube turn", group = "Test")
public class NewRedJewelCubeTurn extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;

        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.rightServo.setPosition(bot.rightDown);
                time.reset();
                while (time.seconds() < 2) {
                    // Just wait :P
                    idle();
                }
                stageNumber = 2;
            }  else if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.rightColorSensor.red() > bot.rightColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so dont add the color value
                    stageNumber++;
                }
            } else if (stageNumber == 5) {
                if (((int) Math.round(colorValue / 3)) == 1) { //checks color
                    color = "Red";
                    bot.app.post(new Runnable() {
                        public void run() {
                            bot.app.setBackgroundColor(Color.argb(bot.rightColorSensor.alpha(), bot.rightColorSensor.red(), bot.rightColorSensor.green(), bot.rightColorSensor.blue()));
                        }
                    });
                    stageNumber = 6;
                } else {
                    color = "Blue";
                    bot.app.post(new Runnable() {
                        public void run() {
                            bot.app.setBackgroundColor(Color.argb(bot.rightColorSensor.alpha(), bot.rightColorSensor.red(), bot.rightColorSensor.green(), bot.rightColorSensor.blue()));
                        }
                    });
                    stageNumber = 6;
                }
            } else if (stageNumber == 6) { //spin to knock off
                if (color.equals("Blue")) {
                    // Baby spin
                    bot.left.setPower(0);
                    driveToPosition(bot.right, 4);
                    stageNumber++;
                } else {
                    stageNumber = 10;
                }
            } else if (stageNumber == 7) { //lift arm
                if (isThere(bot.right, 100)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.rightServo.setPosition(bot.rightUp);
                    stageNumber++;
                }
            } else if (stageNumber == 8) {
                bot.left.setPower(0);
                driveToPosition(bot.right, -5);
                stageNumber++;
            } else if (stageNumber == 9) {
                if (isThere(bot.right, 100)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 10) {
                driveToPosition(bot.right, -2);
                bot.left.setPower(-1);
                stageNumber++;
            } else if (stageNumber == 11) {
                if (isThere(bot.right, 50)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 12) { //cube begins
                bot.rightServo.setPosition(bot.rightUp);
                // Start cube program
                driveToPosition(bot.right, -30);
                bot.arm.setPower(0);
                bot.left.setPower(-0.8d);
                stageNumber++;
            } else if (stageNumber == 13) {
                if (isThere(bot.right, 50)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 14) {
                bot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                turn(90, bot.left, bot.right, bot.gyro);
                bot.left.setPower(bot.right.getPower() * -1);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                    bot.left.setPower(0);
                }
            } else if (stageNumber == 15) {
                time.reset();
                while (time.milliseconds() < 200) {
                    bot.left.setPower(0.5d);
                    bot.right.setPower(0.5d);
                }
                bot.left.setPower(0);
                bot.right.setPower(0);
                time.reset();
                bot.arm.setPower(0);
                while (time.milliseconds() < 500) {
                    bot.arm.setPower(-0.5d);
                }
                bot.arm.setPower(0);
                time.reset();
                bot.box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                while (time.milliseconds() < 1000) {
                    bot.box.setPower(-1);
                }
                bot.box.setPower(0);
                time.reset();
                while (time.milliseconds() < 500) {
                    bot.lintake.setPower(-1);
                    bot.rintake.setPower(-1);
                }
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
                bot.arm.setPower(0);
                stageNumber++;

            } else if (stageNumber == 16) {
                driveToPosition(bot.right, 6);
                bot.arm.setPower(0);
                bot.left.setPower(1);
                stageNumber++;
            } else if (stageNumber == 17) {
                if (isThere(bot.right, 100)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 18) {
                bot.right.setPower(0);
                bot.left.setPower(0);
                stop();
            }

            if (stageNumber == 7 || stageNumber == 9 || stageNumber == 11 || stageNumber == 13 || stageNumber == 17) {
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