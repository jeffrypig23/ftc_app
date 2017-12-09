package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Stephen Ogden on 12/8/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "Gyro test", group = "Test")
//@Disabled

public class gyroTest extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        BNO055IMU gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro.initialize(parameters);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Angle", gyro.getAngularOrientation());
            telemetry.update();
        }
    }
}
