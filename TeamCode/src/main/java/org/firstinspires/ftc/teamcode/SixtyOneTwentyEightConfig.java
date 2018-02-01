package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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

    public DcMotor left;
    public DcMotor right;
    public DcMotor lintake;
    public DcMotor rintake;
    public DcMotor arm;

    @Deprecated
    public DcMotor box;

    public ColorSensor leftColorSensor;
    public ColorSensor rightColorSensor;

    @Deprecated
    public Servo rightServo;

    @Deprecated
    public Servo leftServo;

    public VuforiaTrackables vision;
    public VuforiaLocalizer vuforia;

    public BNO055IMU gyro;

    public VuforiaTrackable relicTemplate;

    // Left upMost =  .5 | Offset = .6 | Down = .95
    // Right upMost =  .36 | Offset = .29 | Down = .12

    // TODO: Many devices are going to be deprecated soon!

    @Deprecated
    public final double leftUp = 0.5d;
    @Deprecated
    public final double leftOffset = 0.6d;
    @Deprecated
    public final double leftDown = 1.0d;

    @Deprecated
    public final double rightUp = 0.36d;
    @Deprecated
    public final double rightOffset = 0.29d;
    @Deprecated
    public final double rightDown = 0.0d;

    @Deprecated
    public final int topBoxPos = -950;
    @Deprecated
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

    public void getAutoConfig(HardwareMap config) {
        left = config.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        right = config.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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

    @SuppressWarnings("SameParameterValue")
    public boolean isThere(DcMotor motor, int discrepancy) {
        int currentPosition = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - currentPosition)) <= discrepancy;
    }

    public String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public RelicRecoveryVuMark getVuMark() {
        return RelicRecoveryVuMark.from(this.relicTemplate);
    }

    public Orientation getAngle() {
        return this.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public void turn(int degree) {
        double turn = (double) this.getAngle().firstAngle;
        degree = degree * -1;
        if ((int) Math.round(turn) > (degree + 5)) {
            this.left.setPower(-0.4d);
            this.right.setPower(0.4d);
        } else if ((int) Math.round(turn) < (degree - 5)) {
            this.left.setPower(0.4d);
            this.right.setPower(-0.4d);
        } else {
            this.left.setPower(0);
            this.right.setPower(0);
        }
    }

    // TODO: Lookup new PID methods!
    @SuppressWarnings("PointlessArithmeticExpression")
    public void driveWithPID(double position, int degree) {
        // 28 (ticks)/(rot motor) * 49 (rot motor/rot wheel) * 1/(3.14*4) (rot wheel/in) = 109 ticks/in
        final double equation = (28 * 49) * 1/(3.14*4);
        this.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.right.setTargetPosition((int) (equation * position) * -1); // Need to make it negative, as forward is negative...
        this.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.left.setTargetPosition((int) (equation * position * -1));

        double turn = (double) this.getAngle().firstAngle;
        degree = degree * -1; // Aaaalll the things get inverted

        double motorWithEncoderPower = 0.45d;
        double motorWithoutEncoderPower = 0.45;

        this.right.setPower(motorWithEncoderPower);
        this.left.setPower(motorWithoutEncoderPower);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public void driveWithGyro(double position_in_inches, int degree) {
        this.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // 28 (ticks)/(rot motor) * 49 (rot motor/rot wheel) * 1/(3.14*4) (rot wheel/in) = 109 ticks/in
        final double equation = (28 * 49) * 1/(3.14*4);

        this.right.setTargetPosition((int) (equation * position_in_inches) * -1); // Need to make it negative, as forward is negative...

        double turn = (double) this.getAngle().firstAngle;
        degree = degree * -1; // Aaaalll the things get inverted

        double motorWithEncoderPower = 0;
        double motorWithoutEncoderPower = 0;

        //<editor-fold desc="Manage position">
        if ((this.right.getTargetPosition() - this.right.getCurrentPosition()) >= 25) {
            motorWithEncoderPower = 0.5d;
            motorWithoutEncoderPower = 0.5d;
        } else if ((this.right.getTargetPosition() - this.right.getCurrentPosition()) <= -25) {
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

        this.right.setPower(motorWithEncoderPower);
        this.left.setPower(motorWithoutEncoderPower);
    }

    public void resetEncoder() {
        this.right.setPower(0);
        this.left.setPower(0);
        this.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
