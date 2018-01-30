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

@Autonomous(name = "Red Jewel", group = "Official")
public class RedJewel extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getConfig(hardwareMap);

        int stageNumber = 0;
        int armPos = -583;

        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.rightServo.setPosition(bot.rightDown);
                time.reset();
                while (time.seconds() < 1) {
                    idle();
                }
                stageNumber++;
            } else if (stageNumber == 1 || stageNumber == 2 || stageNumber == 3) {
                if (bot.rightColorSensor.red() > bot.rightColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so dont add the color value
                    stageNumber++;
                }
            } else if (stageNumber == 4) {
                if (((int) Math.round(colorValue / 3)) == 1) {
                    color = "Red";
                    stageNumber++;
                } else {
                    color = "Blue";
                    stageNumber++;
                }
            } else if (stageNumber == 5) { //spin to knock off
                //<editor-fold desc="If its blue: start a baby turn; if its red: Do nothing">
                if (color.equals("Blue")) {
                    // Baby spin
                    time.reset();
                    while (time.milliseconds() < 200) {
                        bot.right.setPower(1);
                    }
                    bot.right.setPower(0);
                    time.reset();
                    bot.rightServo.setPosition(bot.rightUp);
                    while (time.milliseconds() < 200) {
                        bot.right.setPower(-1);
                    }
                    bot.right.setPower(0);
                    stageNumber++;
                } else {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 6) {
                //<editor-fold desc="Move 4 inches forward and raise the color sensor">
                bot.driveWithGyro(8, 0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 7) {
                //<editor-fold desc="Go forward 30 inches">
                bot.rightServo.setPosition(bot.rightUp);
                bot.driveWithGyro(30, 0);
                bot.arm.setPower(0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 8) {
                stop();
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color, (Red value | Blue value)", color + ", (" + bot.leftColorSensor.red() + " | " + bot.leftColorSensor.blue() + ")")
                    .addData("", "")
                    .addData("Angle (all angles)", bot.getAngle().firstAngle + "(" + bot.getAngle() + ")")
                    .addData("", "")
                    .addData("right pos", bot.right.getCurrentPosition())
                    .addData("right target (∆)", bot.right.getTargetPosition() + " (" + Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()) + ")");
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }
}