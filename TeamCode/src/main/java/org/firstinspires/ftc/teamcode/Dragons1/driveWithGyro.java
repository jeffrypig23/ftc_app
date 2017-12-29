package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Stephen Ogden on 12/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

public class driveWithGyro {
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
        if ((int) Math.round(turn) > (degree + 5)) {
            motorWithoutEncoderPower = motorWithoutEncoderPower + -0.25d;
            motorWithEncoderPower = motorWithEncoderPower + 0.25d;
        } else if ((int) Math.round(turn) < (degree - 5)) {
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
}
