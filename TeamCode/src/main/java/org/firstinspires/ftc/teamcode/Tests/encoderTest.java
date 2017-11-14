package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by Stephen Ogden on 11/6/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "encoderTest", group = "Test")
@Disabled
public class encoderTest extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        DcMotor motor = hardwareMap.dcMotor.get("motor");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int motorPos = 500;

        telemetry.addData("Status", "Done! Press play to start" );
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            motorPos = (int)gamepad1.left_stick_y + motorPos;
            motorPos = (int)gamepad1.right_stick_y + motorPos;

            motor.setTargetPosition(motorPos);

            if (motor.getCurrentPosition() != motor.getTargetPosition()) {
                motor.setPower(1);
            } else {
                motor.setPower(0);
            }

            telemetry.addData("Motor pos", motor.getCurrentPosition())
                    .addData("Target pos", motor.getTargetPosition())
                    .addData("motor power", motor.getPower())
                    .addData("motorPos", motorPos);
            telemetry.update();

        }
    }
}
