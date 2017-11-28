package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Stephen Ogden on 11/27/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

public class driveToPosition implements custom {

    @Override
    public void driveToPosition(DcMotor motor, int position) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(position * 25810);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }
}
