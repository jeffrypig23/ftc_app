package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "TeleOp Test", group = "Test")
//@Disabled

public class TeleopTest extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
    ElapsedTime armTime = new ElapsedTime();

    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        double powR;
        double powL;
        double throttle;
        double turn;
        double sensitivity=0.4d;
        double turnSpeed=0.7d;
        double slow = 1;

        int armPos = -583;

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

            if (gamepad2.right_trigger > 0.1f) {
                bot.rintake.setPower(1);
                bot.lintake.setPower(-1);
            } else if (gamepad2.left_trigger > 0.1f) {
                bot.rintake.setPower(-1);
                bot.lintake.setPower(1);
            } else if (gamepad2.left_bumper) {
                bot.lintake.setPower(1);
                bot.rintake.setPower(1);
            } else if (gamepad2.right_bumper) {
                bot.lintake.setPower(-1);
                bot.rintake.setPower(-1);
            } else {
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
            }

            bot.arm.setPower(gamepad2.left_stick_y); // Manual control

            if (bot.arm.isBusy() || (bot.arm.getPower() != 0.0d)) {
                armTime.reset();
                bot.leftServo.setPosition(bot.leftOffset);
                bot.rightServo.setPosition(bot.rightOffset);
            } else {
                if (armTime.seconds() > 1) {
                    bot.leftServo.setPosition(bot.leftUp);
                    bot.rightServo.setPosition(bot.rightUp);
                }
            }

            telemetry.addData("Right motor", bot.right.getCurrentPosition())
                    .addData("Left motor", bot.left.getCurrentPosition())
                    .addData("Arm position", armPos + " (" + bot.arm.getCurrentPosition() + ")");
            telemetry.update();
        }
        telemetry.addData("Status", "Done");
        telemetry.update();
    }

}
