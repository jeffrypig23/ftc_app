package org.firstinspires.ftc.teamcode.Dragons1;

import android.graphics.Color;

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
 * Created by Stephen Ogden on 11/9/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "6128: Red Jewel", group = "Official")
//@Disabled

public class RedJewel extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;
        int armPos = -583;
        //int boxPos = -850;

        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        bot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.rightServo.setPosition(bot.rightDown);
                time.reset();
                stageNumber++;
            } else if (stageNumber == 1) {
                if (time.seconds() < 2) {
                    //idle();
                } else {
                    stageNumber = 2;
                }
            } else if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.rightColorSensor.red() > bot.rightColorSensor.blue()) {
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
            } else if (stageNumber == 6) {
                if (color.equals("Blue")) {
                    //driveToPosition(bot.left, 2);
                    bot.left.setPower(0);
                    driveToPosition(bot.right, 4);
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
                    bot.rightServo.setPosition(bot.rightUp);
                    stageNumber++;
                }

            } else if (stageNumber == 8) {
                if (color.equals("Blue")) {
                    //driveToPosition(bot.left, 1);
                    bot.left.setPower(0);
                    driveToPosition(bot.right, -4);
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
                if (color.equals("Blue")) {
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
                    bot.rightServo.setPosition(bot.rightUp);
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
