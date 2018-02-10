package org.firstinspires.ftc.teamcode.Auto.Official;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 12/14/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Red Jewel and cube turn", group = "Official")
public class RedJewelCubeTurn extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getAutoConfig(hardwareMap);
        bot.getVision(hardwareMap);

        int stageNumber = 0;

        String color = "";
        RelicRecoveryVuMark pos = RelicRecoveryVuMark.UNKNOWN;

        bot.leftServo.setPosition(bot.leftUp);
        bot.leftSpinner.setPosition(bot.leftIn);

        bot.arm.setPower(0);
        bot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.resetEncoder();

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        time.reset();
        bot.vision.activate();
        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.leftSpinner.setPosition(bot.leftMid);
                if (time.milliseconds() > 500) {
                    bot.leftServo.setPosition(bot.leftDown);
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
                    bot.leftSpinner.setPosition(bot.leftIn);
                } else {
                    bot.leftSpinner.setPosition(bot.leftOut);
                }
                if (time.milliseconds() > 500) {
                    stageNumber++;
                    time.reset();
                }
            } else if (stageNumber == 3) {
                bot.leftServo.setPosition(bot.leftUp);
                if (time.milliseconds() > 500) {
                    stageNumber++;
                }
            } else if (stageNumber == 4) {
                bot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.arm.setTargetPosition(bot.armDown);
                bot.arm.setPower(50);
                if (!bot.arm.isBusy()) {
                    bot.arm.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 5) {
                pos = bot.getVuMark();
                if (!pos.equals(RelicRecoveryVuMark.UNKNOWN)) {
                    stageNumber++;
                }
            } else if (stageNumber == 6) {
                if (pos.equals(RelicRecoveryVuMark.LEFT)) {
                    bot.driveWithPID(-38);
                } else if (pos.equals(RelicRecoveryVuMark.CENTER)) {
                    bot.driveWithPID(-28);
                } else if (pos.equals(RelicRecoveryVuMark.RIGHT)) {
                    bot.driveWithPID(-22);
                } else {
                    stageNumber--;
                    bot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }

            } else if (stageNumber == 7) {
                bot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                bot.left.setTargetPosition(-1277);
                bot.right.setTargetPosition(1639);

                bot.right.setPower(0.45);
                bot.left.setPower(0.45);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 8) {
                bot.driveWithPID(-12);
                if (!bot.right.isBusy() && !bot.left.isBusy()) {
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 9) {
                bot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bot.arm.setTargetPosition(bot.armUp);
                bot.arm.setPower(75);
                if (!bot.arm.isBusy()) {
                    stageNumber++;
                }
            } else if (stageNumber == 10) {
                bot.driveWithPID(4);
                bot.arm.setTargetPosition(bot.armDown);
                bot.arm.setPower(50);
                if (!bot.right.isBusy() && !bot.left.isBusy() && !bot.arm.isBusy()) {
                    bot.arm.setPower(0);
                    bot.resetEncoder();
                    stageNumber++;
                }
            } else if (stageNumber == 11) {
                stop();
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