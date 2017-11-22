package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Stephen Ogden on 11/9/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Red Jewel", group = "Test")
//@Disabled
public class RedJewel extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;

        String color = "";

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {

            //<editor-fold desc="Move down servo">
            if (stageNumber == 0) {
                bot.rightServo.setPosition(bot.rightDown);
                stageNumber++;
            }
            if (stageNumber == 1) {
                if (time.seconds() < 2) {
                    // Do nothing :P
                } else {
                    stageNumber = 2;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Detect Jewel Color">
            double colorValue = 0.0;
            if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.rightColorSensor.red() > bot.rightColorSensor.blue() && bot.rightColorSensor.green() < bot.rightColorSensor.red()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else if (bot.rightColorSensor.red() < bot.rightColorSensor.blue() && bot.rightColorSensor.green() < bot.rightColorSensor.blue()) {
                    stageNumber++;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Drive based on Jewel Color">
            if (stageNumber == 5) {

                if (((int) Math.round(colorValue / 3)) == 1) {
                    color = "Red";
                } else {
                    color = "Blue";
                }
                switch (color) {
                    case "Red": {
                        driveToPostion(bot.left, 1, .3);
                        driveToPostion(bot.right, -1, .3);
                        stageNumber++;
                    }
                    case "Blue": {
                        driveToPostion(bot.left, -1, .3);
                        driveToPostion(bot.right, 1, .3);
                        stageNumber++;
                    }
                }
            }

            if (stageNumber == 6) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.rightServo.setPosition(bot.rightUp);
                    stageNumber++;
                }

            }
            //</editor-fold>

            //<editor-fold desc="Drive back to position">
            if (stageNumber == 7) {
                switch (color) {
                    case "Red": {
                        driveToPostion(bot.left, -1, .3);
                        driveToPostion(bot.right, 1, .3);
                        stageNumber++;
                    }
                    case "Blue": {
                        driveToPostion(bot.left, 1, .3);
                        driveToPostion(bot.right, -1, .3);
                        stageNumber++;
                    }
                }
            }
            if (stageNumber == 8) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            }
            //</editor-fold>

            if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) >= 10) {
                bot.left.setPower(1);
            } else if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) <= -10) {
                bot.left.setPower(-1);
            } else {
                bot.left.setPower(0);
            }

            if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                bot.right.setPower(1);
            } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                bot.right.setPower(-1);
            } else {
                bot.right.setPower(0);
            }

            //<editor-fold desc="Telemetry and Stop">
            if (stageNumber >= 0) {
                telemetry.addData("Stage number", stageNumber)
                        .addData("Color", color)
                        .addData("", "")
                        .addData("Red value", bot.rightColorSensor.red())
                        .addData("Blue value", bot.rightColorSensor.blue())
                        .addData("", "").addData("lefFront pos", bot.left.getCurrentPosition())
                        .addData("left target", bot.left.getTargetPosition())
                        .addData("left ∆", Math.abs(bot.left.getTargetPosition() - bot.left.getCurrentPosition()))
                        .addData("", "").addData("right pos", bot.right.getCurrentPosition())
                        .addData("right target", bot.right.getTargetPosition())
                        .addData("right ∆", Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()));
                telemetry.update();

            } else {
                stop();
            }
            //</editor-fold>

            idle();
        }
        telemetry.addData("Status", "Done!");
        telemetry.update();
    }

    private static void driveToPostion(DcMotor motor, int position, double power) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(position * 25810);
        //motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private static boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }
}
