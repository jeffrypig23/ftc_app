package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

// Teleop for 6128
@TeleOp(name = "6128 TeleOp", group = "Official")
//@Disabled

public class SixtyOneTwentyEightTeleop extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

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

        int armPos = -583;
        int boxPos = 0;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            turn = Math.abs(Math.pow(gamepad1.right_stick_x, (double)2));
            //throttle = Math.abs(Math.pow(gamepad1.left_stick_y, (double)2));
            throttle = gamepad1.left_stick_y;
            if(gamepad1.left_stick_y < 0) {
             //   throttle = -throttle;
            }

            if(gamepad1.right_stick_x > 0) {
                powL = (throttle - turn);
                powR = (throttle + turn);
            }
            else {
                powL = (throttle + turn);
                powR = (throttle - turn);
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

            Out(bot.lintake, bot.rintake, gamepad2.left_bumper);
            In(bot.lintake, bot.rintake, gamepad2.right_bumper);

            bot.arm.setPower(gamepad2.left_stick_y); // Manual control
            bot.box.setPower(gamepad2.right_stick_y); // Manuual control

            telemetry.addData("Left Pow", powL)
                    .addData("RPow", powR)
                    .addData("Right motor", bot.right.getCurrentPosition())
                    .addData("Left motor", bot.left.getCurrentPosition())
                    .addData("Arm position", armPos + " (" + bot.arm.getCurrentPosition() + ")")
                    .addData("Box position", boxPos +  " (" + bot.box.getCurrentPosition() + ")");
            telemetry.update();
        }
    }

    private void Out (DcMotor motor0, DcMotor motor1, boolean run) {
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
    private void In (DcMotor motor0, DcMotor motor1, boolean run) {
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
