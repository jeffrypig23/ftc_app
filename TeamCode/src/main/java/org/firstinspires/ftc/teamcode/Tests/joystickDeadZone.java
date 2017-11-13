package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

        float joyStickDeadZone = 0.1f;
        gamepad1.setJoystickDeadzone(joyStickDeadZone);
        gamepad2.setJoystickDeadzone(joyStickDeadZone);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                joyStickDeadZone = joyStickDeadZone + 0.01f;
                gamepad1.setJoystickDeadzone(joyStickDeadZone);
                gamepad2.setJoystickDeadzone(joyStickDeadZone);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                joyStickDeadZone = joyStickDeadZone - 0.01f;
                gamepad1.setJoystickDeadzone(joyStickDeadZone);
                gamepad2.setJoystickDeadzone(joyStickDeadZone);
            }
            telemetry.addData("Dead Zone value", joyStickDeadZone)
                    .addData("Gamepad 1 x:", gamepad1.left_stick_x + " | " + gamepad1.right_stick_x)
                    .addData("Gamepad 1 y:", gamepad1.left_stick_y + " | " + gamepad1.right_stick_y)
                    .addData("Gamepad 2 x:", gamepad2.left_stick_x + " | " + gamepad2.right_stick_x)
                    .addData("Gamepad 2 y:", gamepad2.left_stick_y + " | " + gamepad2.right_stick_y);
            telemetry.update();
        }
    }
}
