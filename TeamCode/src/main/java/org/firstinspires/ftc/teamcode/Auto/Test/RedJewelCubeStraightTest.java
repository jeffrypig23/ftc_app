package org.firstinspires.ftc.teamcode.Auto.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 12/29/17.
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

        bot.getAutoConfig(hardwareMap);
        bot.getVision(hardwareMap);

        int stageNumber = 5;

        double colorValue = 0.0;

        String color = "";
        RelicRecoveryVuMark pos = RelicRecoveryVuMark.UNKNOWN;

        bot.arm.setPower(0);
        bot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.resetEncoder();

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        bot.vision.activate();
        while (opModeIsActive()) {

            // TODO: Re-evaluate jewel code, and once done, insert here!
            if (stageNumber == 5) {
                bot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.arm.setTargetPosition(bot.armDown);
                bot.arm.setPower(50);
                if (!bot.arm.isBusy()) {
                    bot.arm.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 6) {
                pos = bot.getVuMark();
                if (!pos.equals(RelicRecoveryVuMark.UNKNOWN)) {
                    stageNumber++;
                }
            } else if (stageNumber == 7) {
                bot.driveWithPID(-24);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 8) {
                bot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                bot.right.setTargetPosition(1280);
                bot.left.setTargetPosition(-1640);

                bot.right.setPower(0.45);
                bot.left.setPower(0.45);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 9) {
                if (pos == RelicRecoveryVuMark.RIGHT) {
                    bot.driveWithPID(3);
                } else if (pos == RelicRecoveryVuMark.CENTER) {
                    bot.driveWithPID(10);
                } else if (pos == RelicRecoveryVuMark.LEFT) {
                    bot.driveWithPID(18);
                }
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 10) {
                bot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                bot.right.setTargetPosition(-1433);
                bot.left.setTargetPosition(1536);

                bot.right.setPower(0.45);
                bot.left.setPower(0.45);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 11) {
                bot.driveWithPID(-4);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 12) {
                bot.arm.setTargetPosition(bot.armUp);
                bot.arm.setPower(75);
                if (!bot.arm.isBusy()) {
                    stageNumber++;
                }
            } else if (stageNumber == 13) {
                bot.driveWithPID(3);
                bot.arm.setTargetPosition(bot.armDown);
                bot.arm.setPower(50);
                if (!bot.right.isBusy() && !bot.left.isBusy() && !bot.arm.isBusy()) {
                    bot.arm.setPower(0);
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 14) {
                idle();
                // TODO: Idle -> Stop
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color", "%s", color)
                    .addData("Special column", pos)
                    .addData("", "")
                    .addData("Angle", "%s", bot.getAngle().firstAngle)
                    .addData("", "")
                    .addData("Arm power", bot.arm.getPower())
                    .addData("Power (R|L)", "%s,%s", bot.right.getPower(), bot.left.getPower());
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }
}
