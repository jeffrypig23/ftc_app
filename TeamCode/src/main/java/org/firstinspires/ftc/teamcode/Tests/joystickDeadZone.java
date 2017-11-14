package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 11/13/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "Deadzone test", group = "Test")
//@Disabled
public class joystickDeadZone extends LinearOpMode {
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        float joyStickDeadZone = 0.00000001f;
        gamepad1.setJoystickDeadzone(joyStickDeadZone);
        gamepad2.setJoystickDeadzone(joyStickDeadZone);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        boolean lastPressedUp=true;
        boolean lastPressedDown=true;

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !lastPressedUp) {
                joyStickDeadZone = joyStickDeadZone + 0.0025f;
            } else if (gamepad1.dpad_down && !lastPressedDown) {
                joyStickDeadZone = joyStickDeadZone - 0.0025f;
            }
            lastPressedUp=gamepad1.dpad_up;
            lastPressedDown=gamepad1.dpad_down;
            gamepad1.setJoystickDeadzone(joyStickDeadZone);
            gamepad2.setJoystickDeadzone(joyStickDeadZone);
            telemetry.addData("Dead Zone value", joyStickDeadZone)
                    .addData("Gamepad 1 x:", gamepad1.left_stick_x + " | " + gamepad1.right_stick_x)
                    .addData("Gamepad 1 y:", gamepad1.left_stick_y + " | " + gamepad1.right_stick_y)
                    .addData("Gamepad 2 x:", gamepad2.left_stick_x + " | " + gamepad2.right_stick_x)
                    .addData("Gamepad 2 y:", gamepad2.left_stick_y + " | " + gamepad2.right_stick_y);
            telemetry.update();
        }
    }
}
