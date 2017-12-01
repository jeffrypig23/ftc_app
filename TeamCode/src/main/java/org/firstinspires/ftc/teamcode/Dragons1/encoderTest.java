package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by Stephen Ogden on 11/6/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "Encoder and color test", group = "Test")
//@Disabled
public class encoderTest extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        telemetry.addData("Status", "Done! Press play to start" );
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
                telemetry.addData("Left drive", bot.left.getCurrentPosition())
                        .addData("Right drive", bot.right.getCurrentPosition())
                        .addData("Arm", bot.arm.getCurrentPosition())
                        .addData("Box", bot.box.getCurrentPosition())
                        .addData("Left servo", bot.leftServo.getPosition())
                        .addData("Right servo", bot.rightServo.getPosition())
                        .addData("Right color sensor (RGB)", bot.rightColorSensor.red() + ", " + bot.rightColorSensor.green() + ", " + bot.rightColorSensor.blue())
                        .addData("Left color sensor (RGB)", bot.leftColorSensor.red() + ", " + bot.leftColorSensor.green() + ", " + bot.leftColorSensor.blue());
            telemetry.update();
        }
    }
}
