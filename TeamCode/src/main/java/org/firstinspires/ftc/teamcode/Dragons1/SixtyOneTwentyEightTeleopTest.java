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

@TeleOp(name = "6128 TeleOp Test", group = "Test")
//@Disabled


public class SixtyOneTwentyEightTeleopTest extends LinearOpMode {

    private boolean armMoving = false;

    @Override
    public void runOpMode() {

        //<editor-fold desc="Initialization">
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
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        DcMotor box = hardwareMap.dcMotor.get("box");
        box.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        box.setDirection(DcMotorSimple.Direction.FORWARD);

        //Servo leftServo = hardwareMap.servo.get("leftServo");
        //Servo rightServo = hardwareMap.servo.get("rightServo");

        double rightPos = 1;
        double leftPos = 1;
        double powR = 0.0;
        double powL = 0.0;
        double turnSpeed = 0.8;
        double throttle;
        double turn;

        int armPos = -583;
        int boxPos = 0;

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        //</editor-fold>

        waitForStart();

        while (opModeIsActive()) {

            //<editor-fold desc="Drive">
            right.setPower(powR);
            left.setPower(powL);

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            if (turn < 0) { //want to turn left
                powL = (-throttle);
                powR = (-throttle) * (1.0 - turnSpeed * Math.sqrt(Math.abs(turn)));
            } else { //turn right
                powL = (-throttle) * (1.0 - turnSpeed * Math.sqrt(Math.abs(turn)));
                powR = (-throttle);
            }
            if (gamepad1.left_stick_button) {
                powR = -turn;
                powL = turn;
            }
            //</editor-fold>

            Out(lintake, rintake, gamepad2.left_bumper);
            In(lintake, rintake, gamepad2.right_bumper);

            if (gamepad2.dpad_up) {
                armPos = -672;
            }
            if (gamepad2.dpad_down) {
                armPos = -977;
            }

            if (gamepad2.x) {
                boxPos = 0;
            }
            if (gamepad2.y) {
                boxPos = -898;
            }

            arm.setTargetPosition(armPos);
            arm.setPower(0.5d);

            box.setTargetPosition(boxPos);
            box.setPower(0.5d);

            /*
             * Arm: -583 starting, -672 blockhold/default, -977 lowest
             * Box: -420 6in, -844 12 in, -898 max
             */


            telemetry.addData("Left Pow", powL)
                    .addData("RPow", powR)
                    .addData("Right motor", right.getCurrentPosition())
                    .addData("Left motor", left.getCurrentPosition())
                    .addData("Arm position", armPos)
                    .addData("Box position", boxPos);
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
