package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "6128 TeleOp Test", group = "Test")
//@Disabled


public class SixtyOneTwentyEightTeleopTest extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

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
        double sensitivity=0.6f;

        int selectedArmPos = 2;
        int armPos = -583;
        int boxPos = -850; // Was set to -844

        boolean isPressed = false;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //<editor-fold desc="Drive">
            if(gamepad1.left_stick_y > 0) {
                throttle = -(gamepad1.left_stick_y*gamepad1.left_stick_y);
            }
            else {
                throttle = gamepad1.left_stick_y*gamepad1.left_stick_y;
            }

            turn = gamepad1.right_stick_x;
            if(turn > 0) {
                powL = (int) (throttle - turn * turn);
                powR = (int) (throttle + turn * turn);
            }
            else {
                powL = (int) (throttle + turn * turn);
                powR = (int) (throttle - turn * turn);
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
            bot.right.setPower(powR);
            bot.left.setPower(powL);
            //</editor-fold>

            Out(bot.lintake, bot.rintake, gamepad2.left_bumper);
            In(bot.lintake, bot.rintake, gamepad2.right_bumper);

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

            if (gamepad2.x) {
                boxPos = 0;
            }
            if (gamepad2.y) {
                boxPos = -898;
            }

            bot.arm.setTargetPosition(armPos);

            bot.box.setTargetPosition(boxPos);

            if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) >= 10) {
                bot.box.setPower(0.5d);
            } else if ((bot.box.getTargetPosition() - bot.box.getCurrentPosition()) <= -10) {
                bot.box.setPower(-0.5d);
            } else {
                bot.box.setPower(0);
            }
            if (isThere(bot.box, 10)) {
                //if (bot.arm.getTargetPosition() == -5 && bot.arm.getCurrentPosition() >= -105) {
                    //bot.arm.setPower(-0.1d);
                //} else {
                if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) >= 5) {
                    bot.arm.setPower(1);
                } else if ((bot.arm.getTargetPosition() - bot.arm.getCurrentPosition()) <= -5) {
                    bot.arm.setPower(-1);
                } else {
                    bot.arm.setPower(0);
                }
                //}
            } else {
                bot.arm.setPower(0);
            }


            /*
             * Arm: -583 starting, -672 blockhold/default, -977 lowest
             * Box: -420 6in, -844 12 in, -898 max
             */


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

    private void Out(DcMotor motor0, DcMotor motor1, boolean run) {
        if (run) {
            motor0.setDirection(DcMotorSimple.Direction.REVERSE);
            motor1.setDirection(DcMotorSimple.Direction.REVERSE);
            motor0.setPower(1);
            motor1.setPower(1);
        } else {
            motor0.setPower(0);
            motor1.setPower(0);
        }

    }

    private void In(DcMotor motor0, DcMotor motor1, boolean run) {
        if (run) {
            motor0.setDirection(DcMotorSimple.Direction.FORWARD);
            motor1.setDirection(DcMotorSimple.Direction.FORWARD);
            motor0.setPower(1);
            motor1.setPower(1);
        } else {
            motor0.setPower(0);
            motor1.setPower(0);
        }

    }

}
