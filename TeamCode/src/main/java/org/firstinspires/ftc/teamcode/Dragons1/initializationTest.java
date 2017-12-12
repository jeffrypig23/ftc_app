package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 12/12/17.
 * FTC 6128 | 7935
 * FRC 1595
 */


@Autonomous(name = "Initialization test", group = "Test")
@TeleOp(name = "Initialization test", group = "Test")
@Disabled
public class initializationTest extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        bot.box.setTargetPosition(bot.topBoxPos);
        while (!isThere(bot.box, 40)) {
            if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) >= 40) {
                bot.box.setPower(1.0d);
            } else if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) <= -40) {
                bot.box.setPower(-1.0d);
            }
        }
        bot.box.setPower(0.0d);

        bot.arm.setTargetPosition(-300);
        while (!isThere(bot.arm, 50)) {
            if ((bot.box.getTargetPosition() - bot.arm.getCurrentPosition()) >= 40) {
                bot.arm.setPower(1.0d);
            } else if ((bot.box.getTargetPosition() - bot.arm.getCurrentPosition()) <= -40) {
                bot.arm.setPower(-0.75d);
            }
        }
        bot.arm.setPower(0.0d);

        telemetry.addData("Status", "Done!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            stop();

        }

    }

}
