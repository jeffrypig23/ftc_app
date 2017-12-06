package org.firstinspires.ftc.teamcode.Dragons1;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * Created by Stephen Ogden on 11/14/17.
 * FTC 6128 | 7935
 * FRC 1595
 */


/**
 * This is NOT an OpMode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is the bot for FTC Team 6128: Dragons 1.0.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel: Left drive motor: "left"
 * Motor channel: Right drive motor:  "right"
 * Motor channel: Left intake motor: "lintake"
 * Motor channel: Right intake motor: "rintake"
 * Motor channel: Motor to raise/lower arm: "arm"
 * Motor channel: Motor to raise/lower box: "box"
 *
 * I2C Channel: Color sensor for left side: "left color"
 * I2C Channel: Color sensor for right side: "right color"
 *
 * Servo channel: Servo to raise/lower right arm: "right servo"
 * Servo channel: Servo to raise/lower left arm: "left servo"
 *
 */
public class SixtyOneTwentyEightConfig {

    DcMotor left;
    DcMotor right;
    DcMotor lintake;
    DcMotor rintake;
    DcMotor arm;
    DcMotor box;

    ColorSensor leftColorSensor;
    ColorSensor rightColorSensor;

    Servo rightServo;
    Servo leftServo;

    View app;

    // Left upMost =  .5 | Offset = .6 | Down = .95
    // Right upMost =  .36 | Offset = .29 | Down = .12

    public final double leftUp = 0.5d;
    public final double leftOffset = 0.6d;
    public final double leftDown = 0.95d;

    public final double rightUp = 0.36d;
    public final double rightOffset = 0.29d;
    public final double rightDown = 0.0d;

    public final int topBoxPos = -890; // TODO: Find new top box position
    public final int bottomBoxPos = -30;

    public void getConfig(HardwareMap config) {

        left = config.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Encoder machine 🅱️roke
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        right = config.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        lintake = config.dcMotor.get("lintake");
        lintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lintake.setDirection(DcMotorSimple.Direction.REVERSE);

        rintake = config.dcMotor.get("rintake");
        rintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rintake.setDirection(DcMotorSimple.Direction.REVERSE);

        arm = config.dcMotor.get("arm");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        box = config.dcMotor.get("box");
        box.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        box.setDirection(DcMotorSimple.Direction.FORWARD);

        leftColorSensor = config.colorSensor.get("left color");
        rightColorSensor = config.colorSensor.get("right color");

        rightServo = config.servo.get("right servo");
        leftServo = config.servo.get("left servo");

        app = ((Activity) config.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

    }

    public static void driveToPosition(DcMotor motor, double position_in_inches) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motor.setTargetPosition(position * 25810);
        // 28 (ticks)/(rot motor) * 49 (rot motor/rot wheel) * 1/(3.14*4) (rot wheel/in) = 109 ticks/in
        final double equation = (28 * 49) * 1/(3.14*4);
        motor.setTargetPosition((int) (equation * position_in_inches));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static boolean isThere(DcMotor motor, int discrepancy) {
        int currentPosition = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - currentPosition)) <= discrepancy;
    }
}
