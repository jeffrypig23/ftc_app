package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;

/**
 * Created by Stephen Ogden on 11/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Disabled
@Autonomous(name = "Blue Club (straight)", group = "Test")

public class BlueCubeStraight extends LinearOpMode {
    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
    /*
     * Move arm
     * Drive forward for 3 inches
     * Spit out cube
     * Backup Y inches
     */

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        int stageNumber = 0;

        bot.rightServo.setPosition(bot.rightUp);
        bot.leftServo.setPosition(bot.leftUp);

        telemetry.addData("Status", "Done!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (stageNumber == 0) {
                bot.arm.setTargetPosition(-977);
                stageNumber++;
            } else if (stageNumber == 1) {
                if (isThere(bot.arm, 10)) {
                    bot.arm.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 2) {
                driveToPosition(bot.right, 30);
                driveToPosition(bot.left, 30);
                stageNumber++;
            } else if (stageNumber == 3) {
                if (isThere(bot.right, 100) || isThere(bot.left, 100)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            }

            if (stageNumber !=3) {
                if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                    bot.right.setPower(1);
                } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                    bot.right.setPower(-1);
                } else {
                    bot.right.setPower(0);
                }
                if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) >= 10) {
                    bot.left.setPower(1);
                } else if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) <= -10) {
                    bot.left.setPower(-1);
                } else {
                    bot.left.setPower(0);
                }
            }
            if (stageNumber == 1) {
                if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) >= 10) {
                    bot.right.setPower(1);
                } else if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) <= -10) {
                    bot.arm.setPower(-1);
                } else {
                    bot.arm.setPower(0);
                }
            }


            telemetry.addData("Stage number", stageNumber)
                    .addData("","")
                    .addData("Right motor position", bot.right.getCurrentPosition())
                    .addData("Right motor target", bot.right.getTargetPosition())
                    .addData("Right motor ∆", bot.right.getTargetPosition()-bot.right.getCurrentPosition())
                    .addData("","")
                    .addData("Left motor position", bot.left.getCurrentPosition())
                    .addData("Left motor target", bot.left.getTargetPosition())
                    .addData("Left motor ∆", bot.left.getTargetPosition()-bot.left.getCurrentPosition())
                    .addData("","")
                    .addData("Arm position", bot.arm.getCurrentPosition())
                    .addData("Arm target", bot.arm.getTargetPosition())
                    .addData("Arm ∆", bot.arm.getCurrentPosition() - bot.arm.getTargetPosition());
            telemetry.update();

        }
    }
}
