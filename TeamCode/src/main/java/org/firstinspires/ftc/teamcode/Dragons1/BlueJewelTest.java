package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 12/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Blue Jewel Test", group = "Test")
@Disabled
public class BlueJewelTest extends LinearOpMode {
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
                //<editor-fold desc="Move servo down, and then wait 1 second">
                bot.leftServo.setPosition(bot.leftDown);
                time.reset();
                while (time.seconds() < 1) {
                    idle();
                }
                stageNumber++;
                //</editor-fold>
            }  else if (stageNumber == 1 || stageNumber == 2 || stageNumber == 3) {
                //<editor-fold desc="Get the color of the jewel over 3 iterations">
                if (bot.leftColorSensor.red() > bot.leftColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so don't add the color value
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 4) {
                //<editor-fold desc="Evaluate calculated color, and return a final color">
                if (((int) Math.round(colorValue / 3)) == 1) { //checks color
                    color = "Red";
                    stageNumber++;
                } else {
                    color = "Blue";
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 5) {
                //<editor-fold desc="If its red: start a baby turn; if its blue: Do nothing">
                if (color.equals("Red")) {
                    // Baby spin
                    time.reset();
                    while (time.milliseconds() < 200) {
                        bot.left.setPower(1);
                    }
                    bot.left.setPower(0);
                    time.reset();
                    bot.leftServo.setPosition(bot.leftUp);
                    while (time.milliseconds() < 200) {
                        bot.left.setPower(-1);
                    }
                    bot.left.setPower(0);
                    stageNumber++;
                } else {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 6) {
                //<editor-fold desc="Move 8 inches forward and raise the color sensor">
                bot.driveWithGyro(8, 0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 7) {
                //<editor-fold desc="Go forward 26 inches">
                bot.leftServo.setPosition(bot.leftUp);
                bot.driveWithGyro(26, 0);
                bot.arm.setPower(0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                    stop();
                }
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color, (Red value | Blue value)", color+", ("+bot.leftColorSensor.red() + " | " + bot.leftColorSensor.blue()+")")
                    .addData("", "")
                    .addData("Angle (all angles)", bot.getAngle().firstAngle+ "("+ bot.getAngle() + ")")
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
