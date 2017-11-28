package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Stephen Ogden on 11/27/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

public interface custom {

    public void driveToPosition(DcMotor motor, int position);

    public boolean isThere(DcMotor motor, int discrepancy);

}
