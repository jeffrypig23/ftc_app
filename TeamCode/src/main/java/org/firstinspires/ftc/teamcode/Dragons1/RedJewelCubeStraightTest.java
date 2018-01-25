package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 11/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Red Jewel Cube Straight Test", group = "Test")
//@Disabled
public class RedJewelCubeStraightTest extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getConfig(hardwareMap);

        int stageNumber = 0;

        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            // TODO: Re-evaluate jewewl code, and once done, insert here!
            /*} else */if (stageNumber == 8) {
                //<editor-fold desc="Lower arm, out-take, back up, raise arm, and stop">
                while (time.milliseconds() < 2500) {
                    bot.arm.setPower(-0.5d);
                }
                bot.arm.setPower(0);
                time.reset();
                while (time.milliseconds() < 500) {
                    bot.right.setPower(0.5d);
                    bot.left.setPower(0.5d);
                    bot.lintake.setPower(-1);
                    bot.rintake.setPower(-1);
                }
                bot.right.setPower(0);
                bot.left.setPower(0);
                time.reset();
                while (time.seconds() < 2) {
                    bot.arm.setPower(1);
                }
                bot.arm.setPower(0);
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
                stageNumber++;
                //</editor-fold>
            } else if (stageNumber == 9) {
                stop();
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color, (Red value | Blue value)", color+", ("+bot.leftColorSensor.red() + " | " + bot.leftColorSensor.blue()+")")
                    .addData("", "")
                    .addData("Angle (all angles)", bot.getAngle().firstAngle+ "("+ bot.getAngle() + ")")
                    .addData("", "")
                    .addData("right pos", bot.right.getCurrentPosition())
                    .addData("right target (∆)", bot.right.getTargetPosition() + " (" + Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()) + ")");
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }
}
