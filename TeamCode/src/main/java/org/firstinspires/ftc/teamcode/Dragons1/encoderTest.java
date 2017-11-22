package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by Stephen Ogden on 11/6/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "6128 encoder test", group = "Test")
//@Disabled
public class encoderTest extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        int selectedMotor = 0;

        String motorName = "Left drive, " + bot.left.getDeviceName();

        boolean isPressed = false;
        boolean simpleMode = true;

        telemetry.addData("Status", "Done! Press play to start" );
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Simple mode enabled", simpleMode);

            /* This is the simple mode for the program, meaning you have to move the stuff manually, no controller required */
            if (simpleMode) {
                //<editor-fold desc="Simple mode">
                telemetry.addData("Left drive", bot.left.getCurrentPosition())
                        .addData("Right drive", bot.right.getCurrentPosition())
                        .addData("Arm", bot.arm.getCurrentPosition())
                        .addData("Box", bot.box.getCurrentPosition())
                        .addData("Left servo", bot.leftServo.getPosition())
                        .addData("Right servo", bot.rightServo.getPosition());
                //</editor-fold>
            } else {
                /* This is the advanced mode for the program, meaning you can move stuff via either controller */
                /* Not only does this give the name like it does in the simple one, but also the deviceName, powers, and more */
                //<editor-fold desc="Advanced mode">
                telemetry.addData("Simple mode enabled", simpleMode)
                        .addData("isPressed", isPressed)
                        .addData("Selected motor", motorName);
                switch (selectedMotor) {
                    case 0: {
                        // Left drive
                        motorName = "Left drive, " + bot.left.getDeviceName();
                        bot.left.setPower(.5);
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.left.setTargetPosition(bot.left.getTargetPosition() + 1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.left.setTargetPosition(bot.left.getTargetPosition() - 1);
                        }
                        telemetry.addData("Position", bot.left.getCurrentPosition())
                                .addData("Power", bot.left.getPower());

                    }
                    case 1: {
                        // Right drive
                        motorName = "Right drive, " + bot.right.getDeviceName();
                        bot.right.setPower(.5);
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.right.setTargetPosition(bot.right.getTargetPosition() + 1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.right.setTargetPosition(bot.right.getTargetPosition() - 1);
                        }
                        telemetry.addData("Position", bot.right.getCurrentPosition())
                                .addData("Power", bot.right.getPower());
                    }
                    case 2: {
                        // lintake (runs without encoder)
                        motorName = "Left intake, " + bot.lintake.getDeviceName();
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.lintake.setPower(1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.lintake.setPower(-1);
                        } else {
                            bot.lintake.setPower(0);
                        }
                        telemetry.addData("Power", bot.lintake.getPower());
                    }
                    case 3: {
                        // rintake (runs without encoder)
                        motorName = "Right intake, " + bot.rintake.getDeviceName();
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.rintake.setPower(1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.rintake.setPower(-1);
                        } else {
                            bot.rintake.setPower(0);
                        }
                        telemetry.addData("Power", bot.rintake.getPower());
                    }
                    case 4: {
                        // arm
                        motorName = "Arm, " + bot.arm.getDeviceName();
                        bot.arm.setPower(.5);
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.arm.setTargetPosition(bot.arm.getTargetPosition() + 1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.arm.setTargetPosition(bot.arm.getTargetPosition() - 1);
                        }
                        telemetry.addData("Position", bot.arm.getCurrentPosition())
                                .addData("Power", bot.arm.getPower());
                    }
                    case 5: {
                        // box
                        motorName = "Box, " + bot.box.getDeviceName();
                        bot.box.setPower(.5);
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.box.setTargetPosition(bot.box.getTargetPosition() + 1);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.box.setTargetPosition(bot.box.getTargetPosition() - 1);
                        }
                        telemetry.addData("Position", bot.box.getCurrentPosition())
                                .addData("Power", bot.box.getPower());
                    }
                    case 6: {
                        // rightServo
                        motorName = "Right servo " + bot.rightServo.getDeviceName();
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.rightServo.setPosition(bot.rightServo.getPosition() + 0.001);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.rightServo.setPosition(bot.rightServo.getPosition() - 0.001);
                        }
                        telemetry.addData("Position", bot.rightServo.getPosition());
                    }
                    case 7: {
                        // leftServo
                        motorName = "Left servo, " + bot.leftServo.getDeviceName();
                        if (gamepad1.dpad_up || gamepad2.dpad_up) {
                            bot.leftServo.setPosition(bot.leftServo.getPosition() + 0.001);
                        } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                            bot.leftServo.setPosition(bot.leftServo.getPosition() - 0.001);
                        }
                        telemetry.addData("Position", bot.leftServo.getPosition());
                    }
                }

                if (!isPressed) {
                    if ((gamepad1.dpad_left || gamepad2.dpad_left)) {
                        selectedMotor--;
                        isPressed = true;
                    } else if ((gamepad1.dpad_right || gamepad2.dpad_right)) {
                        selectedMotor++;
                        isPressed = true;
                    }
                } else if (!(gamepad1.dpad_left && gamepad2.dpad_left && gamepad1.dpad_right && gamepad2.dpad_right)) {
                    isPressed = false;
                }

                if (selectedMotor > 7) {
                    selectedMotor = 0;
                } else if (selectedMotor < 0) {
                    selectedMotor = 7;
                }
                //</editor-fold>
            }
            telemetry.addData("Right color sensor (RGB)", bot.rightColorSensor.red() + ", " + bot.rightColorSensor.green() + ", " + bot.rightColorSensor.blue())
                    .addData("Left color sensor (RGB)", bot.leftColorSensor.red() + ", " + bot.leftColorSensor.green() + ", " + bot.leftColorSensor.blue());
            telemetry.update();

            if (gamepad1.start || gamepad2.start) {
                simpleMode = !simpleMode; // Switch it
            }

        }
    }
}
