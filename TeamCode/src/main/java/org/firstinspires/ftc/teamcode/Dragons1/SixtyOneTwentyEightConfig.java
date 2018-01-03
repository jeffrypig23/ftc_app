package org.firstinspires.ftc.teamcode.Dragons1;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Stephen Ogden on 11/14/17.
 * FTC 6128 | 7935
 * FRC 1595
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

    VuforiaTrackables vision;
    VuforiaLocalizer vuforia;

    BNO055IMU gyro;
    
    static VuforiaTrackable relicTemplate;
    static RelicRecoveryVuMark vuMark;

    // Left upMost =  .5 | Offset = .6 | Down = .95
    // Right upMost =  .36 | Offset = .29 | Down = .12

    public final double leftUp = 0.5d;
    public final double leftOffset = 0.6d;
    public final double leftDown = 1.0d;

    public final double rightUp = 0.36d;
    public final double rightOffset = 0.29d;
    public final double rightDown = 0.0d;

    public final int topBoxPos = -950;
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

        gyro = config.get(BNO055IMU.class, "gyro");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = config.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);

    }

    public void getVision(HardwareMap config) {
        int cameraMonitorViewId = config.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", config.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUgZTU3/////AAAAGaQ5yTo6EkZqvsH9Iel0EktQjXWAZUz3q3FPq22sUTrmsYCccs/mjYiflQBH2u7lofbTxe4BxTca9o2EOnNwA8dLGa/yL3cUgDGjeRfXuwZUCpIG6OEKhiPU5ntOpT2Nr5uVkT3vs2uRr7J6G7YoaGHLw2i1wGncRaw37rZyO03QRh0ZatdKIiK1ItuvJkP3qfUJwQwcpROwa+ZdDNQDbpU6WTL+kPZpnkgR8oLcu+Na1lWrbJ2ZTYG8eUjoIGowbVVGJgORHJazy6/7MbYH268h9ZC4vZ12ItyDK/GlPRTeQWdcZRlWfzAAFwNrjmdjWv9hMuOMoWxo2Y2Rw1Fwii4ohLyRmcQa/wAWY+AOEL14";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        vision = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = vision.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
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

    public static String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public static void getVuMark() {
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
    }

    public static void turn(int degree, DcMotor leftMotor, DcMotor rightMotor, BNO055IMU gyro) {

        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double turn = (double) angles.firstAngle;
        degree = degree * -1;
        if ((int) Math.round(turn) > (degree + 5)) {
            leftMotor.setPower(-0.5d);
            rightMotor.setPower(0.5d);
        } else if ((int) Math.round(turn) < (degree - 5)) {
            leftMotor.setPower(0.5d);
            rightMotor.setPower(-0.5d);
        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
    }

    public static void drive(DcMotor motorWithEncoder, DcMotor motorWithoutEncoder, double position_in_inches) {
        motorWithEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorWithoutEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor.setTargetPosition(position * 25810);
        // 28 (ticks)/(rot motor) * 49 (rot motor/rot wheel) * 1/(3.14*4) (rot wheel/in) = 109 ticks/in
        final double equation = (28 * 49) * 1/(3.14*4);
        motorWithEncoder.setTargetPosition((int) (equation * position_in_inches) * -1); // Need to make it negative, as forward is negative...

        if ((motorWithEncoder.getTargetPosition() - motorWithEncoder.getCurrentPosition()) >= 25) {
            motorWithEncoder.setPower(0.5d);
            motorWithoutEncoder.setPower(0.5d);
        } else if ((motorWithEncoder.getTargetPosition() - motorWithEncoder.getCurrentPosition()) <= -25) {
            motorWithEncoder.setPower(-0.5d);
            motorWithoutEncoder.setPower(-0.5d);
        } else {
            motorWithEncoder.setPower(0);
            motorWithoutEncoder.setPower(0);
        }

    }

    public static void driveWithGyro(DcMotor motorWithEncoder, DcMotor motorWithoutEncoder, double position_in_inches, int degree, BNO055IMU gyro) {

        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        motorWithEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorWithoutEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // 28 (ticks)/(rot motor) * 49 (rot motor/rot wheel) * 1/(3.14*4) (rot wheel/in) = 109 ticks/in
        final double equation = (28 * 49) * 1/(3.14*4);
        motorWithEncoder.setTargetPosition((int) (equation * position_in_inches) * -1); // Need to make it negative, as forward is negative...

        double turn = (double) angles.firstAngle;
        degree = degree * -1; // Aaaalll the things get inverted

        double motorWithEncoderPower = 0;
        double motorWithoutEncoderPower = 0;

        //<editor-fold desc="Manage position">
        if ((motorWithEncoder.getTargetPosition() - motorWithEncoder.getCurrentPosition()) >= 25) {
            motorWithEncoderPower = 0.5d;
            motorWithoutEncoderPower = 0.5d;
        } else if ((motorWithEncoder.getTargetPosition() - motorWithEncoder.getCurrentPosition()) <= -25) {
            motorWithEncoderPower = -0.5d;
            motorWithoutEncoderPower = -0.5d;
        } else {
            motorWithEncoderPower = 0;
            motorWithoutEncoderPower = 0;
        }
        //</editor-fold>

        //<editor-fold desc="Manage degree">
        if ((int) Math.round(turn) > (degree + 2)) {
            motorWithoutEncoderPower = motorWithoutEncoderPower + -0.25d;
            motorWithEncoderPower = motorWithEncoderPower + 0.25d;
        } else if ((int) Math.round(turn) < (degree - 2)) {
            motorWithoutEncoderPower = motorWithoutEncoderPower + 0.25d;
            motorWithEncoderPower = motorWithEncoderPower + -0.25d;
        } else {
            motorWithoutEncoderPower = motorWithoutEncoderPower + 0;
            motorWithEncoderPower = motorWithEncoderPower + 0;
        }
        //</editor-fold>

        motorWithEncoder.setPower(motorWithEncoderPower);
        motorWithoutEncoder.setPower(motorWithoutEncoderPower);
    }

    public static void resetEncoder(DcMotor motorWithEncoder) {
        motorWithEncoder.setPower(0);
        motorWithEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorWithEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
