package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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
     *
     * -=OR=-
     *
     * Add initialisation of arm positions to auto
     * You will not be able to know if the box is coming up quite enough because there is no box there, but the program should move the box out of the way of gear
     * then bring the arm down. If you were to bring the arm down first, the box would hit the gear.
     *
     * Place Cubes in box in the positions which only requires straight forward movement
     *
     * Test to make sure the distance is perfectly right for driving there and that the arm lines up with the slot consistently
     * Next add code to bring the arm down
     * Add code to outtake, but skip that stage since there are no intake/outtake motors on the robot at the moment
     * Back the robot up maybe an inch after outtaking, so the cubes are no being supported by the robot and can count as scored
     */

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;

        bot.rightServo.setPosition(bot.rightUp);
        bot.leftServo.setPosition(bot.leftUp);

        bot.box.setTargetPosition(-850);
        bot.box.setPower(-1);
        while(!isThere(bot.box, 10)) {
            // Basically wait until the box is at the right position
            telemetry.addData("Moving box to position", bot.box.getTargetPosition() + " (" + bot.box.getCurrentPosition() + ")");
            telemetry.update();
        }
        bot.box.setPower(0);

        bot.arm.setTargetPosition(-583);
        bot.arm.setPower(-1);
        while (!isThere(bot.arm, 10)) {
            telemetry.addData("Moving arm to position", bot.arm.getTargetPosition() + " (" + bot.arm.getCurrentPosition() + ")");
            telemetry.update();
        }
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.arm.setTargetPosition(-977);
                driveToPosition(bot.right, 30);
                driveToPosition(bot.left, 30);
                stageNumber++;
            } else if (stageNumber == 1) {
                if (isThere(bot.right, 100) || isThere(bot.left, 100)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 2) {
                bot.lintake.setPower(-1);
                bot.rintake.setPower(-1);
                time.reset();
                while (time.seconds() < 1) {
                    telemetry.addData("Status","Gaining points...");
                    telemetry.update();
                }
                bot.rintake.setPower(0);
                bot.lintake.setPower(0);
                stageNumber++;
            } else if (stageNumber == 3) {
                driveToPosition(bot.right, -3);
                driveToPosition(bot.left, -3);
                stageNumber++;
            } else if (stageNumber == 4) {
                if (isThere(bot.right, 100) || isThere(bot.left, 100)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 5) {
                stop();
            }

            if (stageNumber == 1 || stageNumber == 4) {
                if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 100) {
                    bot.right.setPower(1);
                } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -100) {
                    bot.right.setPower(-1);
                } else {
                    bot.right.setPower(0);
                }
                if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) >= 100) {
                    bot.left.setPower(1);
                } else if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) <= -100) {
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
