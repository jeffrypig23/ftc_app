package org.firstinspires.ftc.teamcode.Dragons1;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 12/14/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Disabled
@Autonomous(name = "Red Jewel and cube turn", group = "Official")
public class RedJewelCubeTurn extends LinearOpMode {

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
                //<editor-fold desc="Move servo down, and then wait 2 seconds">
                bot.rightServo.setPosition(bot.rightDown);
                time.reset();
                while (time.seconds() < 2) {
                    // Just wait :P
                    idle();
                }
                stageNumber++;
                //</editor-fold>
            }  else if (stageNumber == 1 || stageNumber == 2 || stageNumber == 3) {
                //<editor-fold desc="Get the color of the jewel over 3 iterations">
                if (bot.rightColorSensor.red() > bot.rightColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so don't add the color value
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 4) {
                //<editor-fold desc="Evaluate calculated color, and return a final color">
                if (((int) Math.round(colorValue / 3)) == 1) { //checks color
                    color = "Red";
                    stageNumber++;
                } else {
                    color = "Blue";
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 5) { //spin to knock off
                //<editor-fold desc="If its blue: start a baby turn; if its red: Do nothing">
                if (color.equals("Blue")) {
                    // Baby spin
                    time.reset();
                    while (time.milliseconds() < 200) {
                        bot.right.setPower(1);
                    }
                    bot.right.setPower(0);
                    time.reset();
                    bot.rightServo.setPosition(bot.rightUp);
                    while (time.milliseconds() < 200) {
                        bot.right.setPower(-1);
                    }
                    bot.right.setPower(0);
                    stageNumber++;
                } else {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 6) {
                //<editor-fold desc="Move 4 inches forward and raise the color sensor">
                bot.driveWithGyro( 8,0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 7) {
                //<editor-fold desc="Go forward 30 inches">
                bot.rightServo.setPosition(bot.rightUp);
                // Start cube program
                bot.driveWithGyro(30,0);
                bot.arm.setPower(0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 8) {
                //<editor-fold desc="Turn 90 degrees">
                bot.turn(45);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 9) {
                //<editor-fold desc="Move back slightly, move the arm down, move the box forward, out-take while backing up">
                time.reset();
                while (time.milliseconds() < 200) {
                    bot.left.setPower(0.5d);
                    bot.right.setPower(0.5d);
                }
                bot.left.setPower(0);
                bot.right.setPower(0);
                time.reset();
                bot.arm.setPower(0);
                while (time.milliseconds() < 750) {
                    bot.arm.setPower(-0.5d);
                }
                bot.arm.setPower(0);
                time.reset();
                bot.box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                while (time.milliseconds() < 1200) {
                    bot.box.setPower(-1);
                }
                bot.box.setPower(0);
                time.reset();
                while (time.milliseconds() < 200) {
                    bot.arm.setPower(0);
                    bot.right.setPower(1);
                    bot.left.setPower(1);
                }
                bot.right.setPower(0);
                bot.left.setPower(0);
                time.reset();
                while (time.milliseconds() < 500) {
                    bot.lintake.setPower(-1);
                    bot.rintake.setPower(-1);
                }
                bot.arm.setPower(0);
                stageNumber++;
                //</editor-fold>
            } else if (stageNumber == 10) {
                //<editor-fold desc="Stop out-take and end">
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
                stop();
                //</editor-fold>
            }



            telemetry.addData("Stage number", stageNumber)
                    .addData("Color", color)
                    .addData("Angle", bot.getAngle().firstAngle)
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