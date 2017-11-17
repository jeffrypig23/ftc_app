package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Stephen Ogden on 11/7/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Blue Jewel", group = "Test")
//@Disabled
public class BlueJewel extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        // Left up is  .53| down is .95
        // Right up is .3 | down is .11


        final double SERVOUPPOS = 0.5d;
        final double SERVODOWNPOS = 0.0d;

        int stageNumber = 0;

        String color = null;

        /*
        ColorSensor leftColorSensor = hardwareMap.colorSensor.get("left color");

        Servo leftServo = hardwareMap.servo.get("left servo");
        leftServo.setPosition(SERVOUPPOS);

        DcMotor left = hardwareMap.dcMotor.get("left");
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        DcMotor right = hardwareMap.dcMotor.get("right");
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            //<editor-fold desc="Move down servo">
            if (stageNumber == 0) {
                bot.leftServo.setPosition(SERVODOWNPOS);
                stageNumber++;
            }
            if (stageNumber == 1) {
                if (bot.leftServo.getPosition() == SERVODOWNPOS) {
                    stageNumber++;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Detect Jewel Color">
            double colorValue = 0.0;
            if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.leftColorSensor.red() > bot.leftColorSensor.blue() && bot.leftColorSensor.green() < bot.leftColorSensor.red()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else if (bot.leftColorSensor.red() < bot.leftColorSensor.blue() && bot.leftColorSensor.green() < bot.leftColorSensor.blue()) {
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

            if (stageNumber == 6) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.leftServo.setPosition(SERVOUPPOS);
                    stageNumber++;
                }

            }
            //</editor-fold>

            //<editor-fold desc="Drive back to position">
            if (stageNumber == 7) {
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
            if (stageNumber == 8) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Telemetry and Stop">
            if (stageNumber >= 0) {
                telemetry.addData("Stage number", stageNumber)
                        .addData("Color", color)
                        .addData("", "")
                        .addData("Red value", bot.leftColorSensor.red())
                        .addData("Blue value", bot.leftColorSensor.blue())
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
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private static boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }

}
