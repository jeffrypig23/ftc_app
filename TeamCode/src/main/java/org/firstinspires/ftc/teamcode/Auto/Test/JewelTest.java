package org.firstinspires.ftc.teamcode.Auto.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 12/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Jewel Test", group = "Test")
//@Disabled
public class JewelTest extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getAutoConfig(hardwareMap);

        int stageNumber = 0;

        String color = "";

        bot.servo.setPosition(bot.leftUp);
        bot.spinner.setPosition(bot.leftIn);
        //bot.rightSpinner.setPosition(bot.rightIn);
        //bot.rightServo.setPosition(bot.rightIn)

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();
        time.reset();
        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.spinner.setPosition(bot.leftMid);
                if (time.milliseconds() > 500) {
                    bot.servo.setPosition(bot.leftDown);
                    time.reset();
                    stageNumber++;
                }
            } else if (stageNumber == 1) {
                if (time.seconds() > 1) {
                    if (bot.colorSensor.blue() > bot.colorSensor.red()) {
                        color = "BLUE";
                        stageNumber++;
                        time.reset();
                    } else {
                        color = "RED";
                        stageNumber++;
                        time.reset();
                    }
                }
            } else if (stageNumber == 2) {
                if (color == "RED") {
                    bot.spinner.setPosition(bot.leftOut);
                } else {
                    bot.spinner.setPosition(bot.leftIn);
                }
                if (time.milliseconds() > 500) {
                    stageNumber++;
                }
            } else if (stageNumber == 3) {
                bot.servo.setPosition(bot.leftUp);
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color", "%s", color)
                    .addData("", "")
                    .addData("Power (R|L)", "%s,%s", bot.right.getPower(), bot.left.getPower());
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }
}
