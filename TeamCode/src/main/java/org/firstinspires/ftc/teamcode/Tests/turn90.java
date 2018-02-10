package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 12/8/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
@Deprecated
@TeleOp(name="Turn", group = "Test")
@Disabled
public class turn90 extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        bot.getAutoConfig(hardwareMap);

        bot.servo.setPosition(bot.leftUp);

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Angles", bot.getAngle() + " (" + (int)Math.round((double) bot.getAngle().firstAngle) + ")");
            telemetry.update();

        }
    }
}
