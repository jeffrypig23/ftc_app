package org.firstinspires.ftc.teamcode.Dragons1;

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

// Teleop for 6128
@TeleOp(name = "Drive Methods", group = "Test")
//@Disabled

/*
Function which will move the box to one of 4 different positions
    Based on encoder
    Pass a value - Value corresponds to position

Another funciton to lift
    Controlled by Continuous SERVO which is connected to encoder
    Gear ratio is 12:1
        560 tics per rotation on neverest
        290 on core hex
        12 full rotations around the small to go one full around the gear
    Goes from starting posotion up by 90°

Intake
    2 Motors

Outtake
    2 Motors

    // TODO: All motors (Except drive) are Core Hex motors!!!


 */

public class DriveMethods extends LinearOpMode {

    private boolean armMoving = false;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        DcMotor left = hardwareMap.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor right = hardwareMap.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor lintake = hardwareMap.dcMotor.get("lintake");
        lintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor rintake = hardwareMap.dcMotor.get("rintake");
        rintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor arm = hardwareMap.dcMotor.get("arm");
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //CRServo arm = hardwareMap.crservo.get("arm");

        DcMotor box = hardwareMap.dcMotor.get("box");
        box.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        box.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        box.setDirection(DcMotorSimple.Direction.FORWARD);

        //Servo leftServo = hardwareMap.servo.get("leftServo");
        //Servo rightServo = hardwareMap.servo.get("rightServo");

        double rightPos = 1;
        double leftPos = 1;
        double powR = 0.0;
        double powL = 0.0;
        double turnSpeed = 0.81;
        double throttle;
        double turn;
        String driveMethod = "ORIGINAL";
        boolean oneStick = false;
        boolean bPressed = false;
        int quickTurn = 0;
        double sensitivity = 0.7;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        float slow = 1.0f;
        ElapsedTime time = new ElapsedTime();
        int lastTime = 0;

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

            right.setPower(slow*powR);
            left.setPower(slow*powL);

            telemetry.addData("One Stick?", oneStick);
            telemetry.addData("Mode:", driveMethod);
            telemetry.addData("LPow", powL);
            telemetry.addData("RPow", powR);
            telemetry.addData("RPos", right.getCurrentPosition());
            telemetry.addData("LPos", left.getCurrentPosition());
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

    // TODO: Redo box postion (Based on inches)

    private void manualMoveUp(DcMotor motor, boolean run) {
        if (run) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
            motor.setPower(1);
        } else {
            motor.setPower(0);
        }
    }

    private void manualMoveDown(DcMotor motor, boolean run){
        if (run) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
            motor.setPower(1);
        } else {
            motor.setPower(0);
        }
    }


    private void moveBoxTo(DcMotor motor, int position, double power) {
        motor.setTargetPosition((int)(290/(3.14*1.2) * position)); //290 tick/rotation * (3.14 * 1.2) inches/rotation --> 290*1/(3.14*1.2) ticks/inch
        motor.setPower(power);
    }


    /*
    private void boxPos (DcMotor motor, int position) {

        switch (position){
            case 0: {
                // Something
            }
            case 1: {
                // Something
            }
            case 2: {
                // Something
            }
            case 3: {
                // Something
            }
        }
    }
    */

    private void moveArm (DcMotor motor, int degrees, double power) {
        // 290*12*(96/360) = 729.6
        //float conversion = 3480.0f * (degrees/360.0f);
        int position = Math.round(((290*12)/360) * (degrees));

        motor.setTargetPosition(position);
        motor.setPower(power);

    }

    private static boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }
    /*
    private void armUp (DcMotor motor) {
        if (armMoving) {
            if (Math.abs(motor.getTargetPosition() - motor.getCurrentPosition()) <= 50) {
                motor.setPower(0);
                armMoving = false;
            }
        } else {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(730);
            motor.setPower(1);
            armMoving = true;
        }
    }

    private void armDown (DcMotor motor) {
        if (armMoving) {
            if (Math.abs(motor.getTargetPosition() - motor.getCurrentPosition()) <= 50) {
                motor.setPower(0);
                armMoving = false;
            }
        } else {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(-(730));
            motor.setPower(1);
            armMoving = true;
        }
    }
    */
    /*
    private void armUp (CRServo servo) {
        if (armMoving) {
            if (Math.abs(servo.))
        }
    }
    */
}
