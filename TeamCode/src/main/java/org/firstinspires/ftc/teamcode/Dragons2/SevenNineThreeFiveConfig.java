package org.firstinspires.ftc.teamcode.Dragons2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Stephen Ogden on 11/15/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

// This is the config file for FTC Team 7935: Dragons 2.0

public class SevenNineThreeFiveConfig {

    DcMotor left;
    DcMotor right;
    DcMotor lintake;
    DcMotor rintake;
    DcMotor lelevator;
    DcMotor relevator;
    DcMotor out;

    public void getConfig(HardwareMap config) {

        left = config.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setDirection(DcMotorSimple.Direction.FORWARD);

        right = config.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        lintake = config.dcMotor.get("lintake");
        lintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rintake = config.dcMotor.get("rintake");
        rintake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lelevator = config.dcMotor.get("lelevator");
        lelevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lelevator.setDirection(DcMotorSimple.Direction.FORWARD);
        lelevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lelevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        relevator = config.dcMotor.get("relevator");
        relevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        relevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        out = config.dcMotor.get("out");
        out.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        out.setDirection(DcMotorSimple.Direction.FORWARD);

    }
}
