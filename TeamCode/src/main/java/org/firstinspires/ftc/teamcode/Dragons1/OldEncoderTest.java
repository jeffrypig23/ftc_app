package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Stephen Ogden on 11/13/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
@TeleOp(name = "Ethan's encoder test", group = "Test")
@Disabled
@Deprecated
public class OldEncoderTest extends LinearOpMode {
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        DcMotor left = hardwareMap.dcMotor.get("left");
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor right = hardwareMap.dcMotor.get("right");
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor lintake = hardwareMap.dcMotor.get("lintake");
        lintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor rintake = hardwareMap.dcMotor.get("rintake");
        rintake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor arm = hardwareMap.dcMotor.get("arm");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        DcMotor box = hardwareMap.dcMotor.get("box");
        box.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        box.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            /*
             * Arm: -583 starting, -672 blockhold/default, -977 lowest
             * Box: -420 6in, -844 12 in, -898 max
             */
            telemetry.addData("Left motor", left.getCurrentPosition())
                    .addData("Right motor", right.getCurrentPosition())
                    .addData("Arm", arm.getCurrentPosition())
                    .addData("Box", box.getCurrentPosition());
            telemetry.update();
        }
    }
}
