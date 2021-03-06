package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

// Teleop for 6128
@TeleOp(name = "TeleOp Test", group = "Test")
//@Disabled
public class TeleopTest extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

        bot.getTeleOpConfig(hardwareMap);

        double powR;
        double powL;
        double throttle;
        double turn;
        double sensitivity=0.4d;
        double slow = 1.0d;
        double turnSpeed=0.7d;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            turn = gamepad1.right_stick_x * gamepad1.right_stick_x;
            throttle = gamepad1.left_stick_y;
            if (gamepad1.right_bumper) {
                slow = 0.35f;
            } else {
                slow = 1;
            }
            if(gamepad1.right_stick_x > 0) {
                powL = (throttle - turnSpeed*turn);
                powR = (throttle + turnSpeed*turn);
            }

            else {
                powL = (throttle + turnSpeed*turn);
                powR = (throttle - turnSpeed*turn);
            }
            if (powR > 1.0) {
                powL -= sensitivity * (powR - 1.0);
                powR = 1.0;
            } else if (powL > 1.0) {
                powR -=  sensitivity * (powL - 1.0);
                powL = 1.0;
            } else if (powR < -1.0) {
                powL +=  sensitivity * (-1.0 - powR);
                powR = -1.0;
            } else if (powL < -1.0) {
                powR +=  sensitivity * (-1.0 - powL);
                powL = -1.0;
            }
            bot.right.setPower(slow * powR);
            bot.left.setPower(slow * powL);

            if (gamepad2.left_bumper) {
                if (gamepad2.left_trigger > 0.1f) {
                    bot.rintake.setPower(-1);
                } else {
                    bot.rintake.setPower(1);
                }
                bot.lintake.setPower(1);

            } else if (gamepad2.right_bumper) {
                if (gamepad2.right_trigger > 0.1f) {
                    bot.rintake.setPower(1);
                } else {
                    bot.rintake.setPower(-1);
                }
                bot.lintake.setPower(-1);

            } else {
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
            }

            bot.arm.setPower(gamepad2.left_stick_y); // Manual control

            bot.servo.setPosition(bot.leftUp);

            telemetry.addData("Left Pow", powL)
                    .addData("RPow", powR)
                    .addData("Right motor", bot.right.getCurrentPosition())
                    .addData("Left motor", bot.left.getCurrentPosition())
                    .addData("Angle", bot.getAngle())
                    .addData("Arm position", bot.arm.getCurrentPosition());
            telemetry.update();
        }
    }

}
