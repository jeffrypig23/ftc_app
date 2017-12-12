package org.firstinspires.ftc.teamcode.Dragons1;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 12/8/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Drive forward 3 feet", group = "Test")
//@Disabled
public class driveForwardXInches extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        bot.getConfig(hardwareMap);

        bot.rightServo.setPosition(bot.rightUp);
        bot.leftServo.setPosition(bot.leftUp);

        telemetry.addData("Status", "Done");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            driveToPosition(bot.left, -36);
            driveToPosition(bot.right, -36);
            while (!isThere(bot.left, 10) && !isThere(bot.right, 10)) {
                if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                    bot.right.setPower(1);
                    bot.left.setPower(1);
                } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                    bot.right.setPower(-1);
                    bot.left.setPower(-1);
                } else {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                }
            }
            bot.right.setPower(0);
            bot.left.setPower(0);
            stop();
        }
    }
}
