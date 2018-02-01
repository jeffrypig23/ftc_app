package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 12/8/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
@TeleOp(name="Turn", group = "Test")
//@Disabled
public class turn90 extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        bot.getConfig(hardwareMap);

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            bot.turn(90);

            telemetry.addData("Angles", bot.getAngle() + " (" + (int)Math.round((double) bot.getAngle().firstAngle) + ")");
            telemetry.update();

        }
    }
}
