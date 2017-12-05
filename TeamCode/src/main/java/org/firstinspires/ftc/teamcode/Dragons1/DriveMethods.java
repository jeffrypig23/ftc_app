package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 10/30/17.
 * FTC 6128 | 7935
 * FRC 1595
 */


@TeleOp(name = "Drive Methods", group = "Test")
@Disabled

public class DriveMethods extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        double powR = 0.0;
        double powL = 0.0;
        double turnSpeed = 0.81;
        double throttle;
        double sensitivity = 0.7;
        double turn;
        double slow = 1.0f;

        String driveMethod = "ORIGINAL";

        boolean oneStick = false;
        boolean bPressed = false;

        int quickTurn = 0;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                slow = 0.35f;
            } else {
                slow = 1.0f;
            }
            if (gamepad1.b && !bPressed) {
                bPressed = true;
                oneStick = !oneStick;
            }
            else {
                bPressed = false;
            }



            if (gamepad1.dpad_up)
                {driveMethod = "ORIGINAL";}
            else if(gamepad1.dpad_down)
                {driveMethod = "BASIC WC";}
            else if(gamepad1.dpad_right)
                {driveMethod = "CHEESY WC";}
            else if(gamepad1.dpad_left)
                {driveMethod = "SQRT WC";}
            throttle = -gamepad1.left_stick_y;
            if (oneStick)
            {turn = gamepad1.left_stick_x;}
            else
            {turn = gamepad1.right_stick_x;}

            switch (driveMethod) {
            case ("ORIGINAL"):
                if (turn < 0) { //want to turn left
                    powL = throttle;
                    powR = throttle * (1.0 - turnSpeed * Math.sqrt(Math.abs(turn)));
                } else { //turn right
                    powL = throttle * (1.0 - turnSpeed * Math.sqrt(Math.abs(turn)));
                    powR = throttle;
                }
                if (gamepad1.left_stick_button) {
                    powR = -turn;
                    powL = turn;
                }
            case ("BASIC WC"):
                quickTurn = 0;
                powL = (int)(throttle - turn);
                powR = (int)(throttle + turn);
                if (powR > 1.0) {
                    powL -= quickTurn * sensitivity*(powR - 1.0);
                    powR = 1.0;
                } else if (powL > 1.0) {
                    powR -=  quickTurn * sensitivity*(powL - 1.0);
                    powL = 1.0;
                } else if (powR < -1.0) {
                    powL +=  quickTurn * sensitivity*(-1.0 - powR);
                    powR = -1.0;
                } else if (powL < -1.0) {
                    powR +=  quickTurn * sensitivity*(-1.0 - powL);
                    powL = -1.0;
                }
            case ("CHEESY WC"):
                quickTurn = 1;
                powL = (int)(throttle - turn);
                powR = (int)(throttle + turn);
                if (powR > 1.0) {
                    powL -= quickTurn * (powR - 1.0);
                    powR = 1.0;
                } else if (powL > 1.0) {
                    powR -=  quickTurn * (powL - 1.0);
                    powL = 1.0;
                } else if (powR < -1.0) {
                    powL +=  quickTurn * (-1.0 - powR);
                    powR = -1.0;
                } else if (powL < -1.0) {
                    powR +=  quickTurn * (-1.0 - powL);
                    powL = -1.0;
                }
            }

            bot.right.setPower(slow*powR);
            bot.left.setPower(slow*powL);

            telemetry.addData("One Stick?", oneStick);
            telemetry.addData("Mode:", driveMethod);
            telemetry.addData("LPow", powL);
            telemetry.addData("RPow", powR);
            telemetry.addData("RPos", bot.right.getCurrentPosition());
            telemetry.addData("LPos", bot.left.getCurrentPosition());
            telemetry.update();
        }
    }
}
