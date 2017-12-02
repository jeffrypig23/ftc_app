package org.firstinspires.ftc.teamcode.Dragons2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Stephen Ogden on 10/31/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "7935 TeleOp", group = "Official")
@Disabled

public class SevenNineThreeFiveTeleop extends LinearOpMode {

    private boolean outRun = false;

    private SevenNineThreeFiveConfig bot = new SevenNineThreeFiveConfig();

    public void runOpMode() {

        //<editor-fold desc="Initialization">
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        /*
        DcMotor left = hardwareMap.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor right = hardwareMap.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor lintake = hardwareMap.dcMotor.get("lintake");
        lintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //lintake.setDirection(DcMotorSimple.Direction.FORWARD);
        lintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor rintake = hardwareMap.dcMotor.get("rintake");
        rintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor lelevator = hardwareMap.dcMotor.get("lelevator");
        lelevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lelevator.setDirection(DcMotorSimple.Direction.FORWARD);
        lelevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lelevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lelevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        DcMotor relevator = hardwareMap.dcMotor.get("relevator");
        relevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //relevator.setDirection(DcMotorSimple.Direction.FORWARD);
        relevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        relevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        relevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        DcMotor out = hardwareMap.dcMotor.get("out");
        out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        out.setDirection(DcMotorSimple.Direction.FORWARD);
        */

        bot.getConfig(hardwareMap);

        int elevatorPos = 251;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        //</editor-fold>
        while (opModeIsActive()) {

            bot.right.setPower(gamepad2.left_stick_y - (gamepad2.right_stick_x * 2));
            bot.left.setPower(gamepad2.left_stick_y + (gamepad2.right_stick_x * 2));

            if (gamepad1.dpad_down) {
                bot.lintake.setPower(1);
                bot.rintake.setPower(-1);
                bot.out.setPower(-1);
            } else if (gamepad1.dpad_up) {
                bot.lintake.setPower(-1);
                bot.rintake.setPower(1);
                bot.out.setPower(1);
            } else {
                //In(lintake, gamepad1.left_bumper);
                //In(rintake, gamepad1.right_bumper);
                bot.lintake.setPower(gamepad1.right_trigger * -1);
                bot.rintake.setPower(gamepad1.right_trigger);
                bot.out.setPower(gamepad1.right_trigger);
            }

            bot.out.setPower(gamepad1.left_trigger * -1);

            /*
            if (gamepad1.right_trigger >= 0.2f) {
                Out(out);
            }
            */


            if (gamepad1.right_stick_y != 0 || elevatorPos < 250) {
                bot.relevator.setPower(1);
                bot.lelevator.setPower(-1);
            } else {
                bot.relevator.setPower(0);
                bot.lelevator.setPower(0);
            }

            //relevator.setPower(gamepad1.right_stick_y);
            //lelevator.setPower(gamepad1.right_stick_y);

            if (elevatorPos > 250) {
                elevatorPos = (int)gamepad1.right_stick_y + elevatorPos;
                //lelevator.setPower(gamepad1.right_stick_y);
            } else {
                elevatorPos = 251;
            }
            bot.relevator.setTargetPosition(elevatorPos);
            bot.lelevator.setTargetPosition(elevatorPos);

            /*
            if (gamepad1.a) {
                elevatorPos(relevator,lelevator, 24);
            }
            if (gamepad1.b) {
                elevatorPos(relevator,lelevator, 18);
            }
            if (gamepad1.y) {
                elevatorPos(relevator,lelevator, 12);
            }
            if (gamepad1.x) {
                elevatorPos(relevator, lelevator, 6);
            }

            if (isThere(relevator, 30)) {
                relevator.setPower(0);
            }
            if (isThere(lelevator, 30)) {
                lelevator.setPower(0);
            }
            */

            telemetry.addData("RPower", gamepad2.left_stick_y - (gamepad2.right_stick_x * 2));
            telemetry.addData("LPower", gamepad2.left_stick_y + (gamepad2.right_stick_x * 2));
            telemetry.addData("TargetPos", elevatorPos);
            telemetry.addData("Left elevator", bot.lelevator.getCurrentPosition());
            telemetry.addData("Right elevator", bot.relevator.getCurrentPosition());

            telemetry.update();
        }

    }

    private void In (DcMotor motor, boolean run) {
        if (run) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
            //motor0.setDirection(DcMotorSimple.Direction.FORWARD);
            motor.setPower(1);
            //motor0.setPower(1);
        } else {
            motor.setPower(0);
        }

    }
    private void Out (DcMotor motor) {
        if (outRun) {
            int curentPos = motor.getCurrentPosition();
            int targetPos = motor.getTargetPosition();
            if (Math.abs((targetPos - curentPos)) <= 30) {
                outRun = false;
            }
        } else {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(299040);
            motor.setPower(1);
            outRun = true;
        }
    }
    private void elevatorPos (DcMotor motor0, DcMotor motor1, int position) {
        switch (position) {
            case 6: {
                motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor0.setTargetPosition(49840 * 6);
                motor0.setPower(100);
                motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor1.setTargetPosition(49840 * 6);
                motor1.setPower(100);
            }
            case 12: {
                motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor0.setTargetPosition(49840 * 12);
                motor0.setPower(100);
                motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor1.setTargetPosition(49840 * 12);
                motor1.setPower(100);
            }
            case 18: {
                motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor0.setTargetPosition(49840 * 18);
                motor0.setPower(100);
                motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor1.setTargetPosition(49840 * 18);
                motor1.setPower(100);
            }
            case 24: {
                motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor0.setTargetPosition(49840 * 24);
                motor0.setPower(100);
                motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor1.setTargetPosition(49840 * 24);
                motor1.setPower(100);
            }
        }
    }
    private static boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }
}
