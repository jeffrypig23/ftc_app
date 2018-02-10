package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;


/**
 * Created by Stephen Ogden on 11/6/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Deprecated
@TeleOp(name = "Encoder and color test", group = "Test")
@Disabled
public class encoderTest extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getTeleOpConfig(hardwareMap);

        telemetry.addData("Status", "Done! Press play to start" );
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
                telemetry.addData("Left drive", bot.left.getCurrentPosition())
                        .addData("Right drive", bot.right.getCurrentPosition())
                        .addData("Arm", bot.arm.getCurrentPosition())
                        .addData("Servo", bot.servo.getPosition())
                        .addData("Left color sensor (RGB)", bot.colorSensor.red() + ", " + bot.colorSensor.green() + ", " + bot.colorSensor.blue());
            telemetry.update();
        }
    }
}
