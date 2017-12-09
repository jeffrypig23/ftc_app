package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "6128 TeleOp Test", group = "Test")
//@Disabled

public class SixtyOneTwentyEightTeleopTest extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
    ElapsedTime armTime = new ElapsedTime();

    @Override
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
        double sensitivity=0.4f;
        double turnSpeed=0.7f;
        double slow = 1;

        int selectedArmPos = 2;
        int armPos = -583;
        int boxPos = 0; // Was set to -844

        boolean isPressed = false;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            turn = Math.abs(Math.pow(gamepad1.right_stick_x, 0.5));
            throttle = gamepad1.left_stick_y;//(gamepad1.left_stick_y/Math.abs(gamepad1.left_stick_y)) * Math.abs(Math.pow(gamepad1.left_stick_y, (double)2))  ;
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
            /*
            if (!isPressed) {
                if (gamepad2.dpad_up) {
                    //armPos = -672;
                    selectedArmPos++;
                    isPressed = true;
                } else if (gamepad2.dpad_down) {
                    //armPos = -977;
                    selectedArmPos--;
                    isPressed = true;
                }
            } else if (!(gamepad2.dpad_up && gamepad2.dpad_down)) {
                isPressed = false;
            }

            if (selectedArmPos < 0) {
                selectedArmPos = 0;
            } else if (selectedArmPos > 3) {
                selectedArmPos = 3;
            }

            if (selectedArmPos == 0) {
                armPos = -977;
            } else if (selectedArmPos == 1) {
                armPos = -672;
            } else if (selectedArmPos == 2) {
                armPos = -583;
            } else if (selectedArmPos == 3) {
                armPos = -7;
            }

            bot.arm.setTargetPosition(armPos);

            if (isThere(bot.box, 10)) {
                if (selectedArmPos == 3) {
                    if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) >= 5) {
                        bot.arm.setPower(0.40d);
                    } else if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) <= -5) {
                        bot.arm.setPower(-0.40d);
                    } else {
                        bot.arm.setPower(0);
                    }
                } else {
                    if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) >= 5) {
                        bot.arm.setPower(1);
                    } else if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) <= -5) {
                        bot.arm.setPower(-1);
                    } else {
                        bot.arm.setPower(0);
                    }
                }
            } else {
                bot.arm.setPower(0);
            }
            */

            //bot.box.setPower(gamepad2.right_stick_y); // Manuual control

            if (gamepad2.right_stick_y != 0) {
                boxPos = bot.box.getCurrentPosition() + (int) gamepad2.right_stick_y;
            }

            if (gamepad2.x) {
                boxPos = bot.bottomBoxPos;
            }
            if (gamepad2.y) {
                boxPos = bot.topBoxPos;
            }
            bot.box.setTargetPosition(boxPos);

            if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) >= 40) {
                bot.box.setPower(1.0d);
            } else if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) <= -40) {
                bot.box.setPower(-1.0d);
            } else if (isThere(bot.box, 9)) {
                bot.box.setPower(0);
            }

            if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) >= 10 && (bot.box.getTargetPosition() - bot.box.getCurrentPosition()) < 40) {
                bot.box.setPower(-0.75d);
            } else if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) <= -10 && (bot.box.getTargetPosition() - bot.box.getCurrentPosition()) > -40) {
                bot.box.setPower(0.75d);
            } else if (isThere(bot.box, 9)) {
                bot.box.setPower(0);
            }

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

            telemetry.addData("isPressed", isPressed)
                    .addData("ArmSelectPosition", selectedArmPos)
                    .addData("Left Pow", powL)
                    .addData("RPow", powR)
                    .addData("Right motor", bot.right.getCurrentPosition())
                    .addData("Left motor", bot.left.getCurrentPosition())
                    .addData("Arm position", armPos + " (" + bot.arm.getCurrentPosition() + ")")
                    .addData("Box position", boxPos +  " (" + bot.box.getCurrentPosition() + ")");
            telemetry.update();
        }
        telemetry.addData("Status", "Done");
        telemetry.update();
    }

}
