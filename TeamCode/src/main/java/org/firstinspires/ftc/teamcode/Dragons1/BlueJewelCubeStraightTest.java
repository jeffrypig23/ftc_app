package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.Dragons1.driveWithGyro.driveWithGyro;

/**
 * Created by Stephen Ogden on 12/29/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Blue Jewel Cube Straight Test", group = "Test")
@Disabled
public class BlueJewelCubeStraightTest extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getConfig(hardwareMap);

        int stageNumber = 0;

        double colorValue = 0.0;

        String color = "";

        Orientation angles = null;

        telemetry.addData("Status", "Done");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {

            angles = bot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            if (stageNumber == 0) {
                bot.leftServo.setPosition(bot.leftDown);
                time.reset();
                while (time.seconds() < 1) {
                    idle();
                }
                stageNumber++;
            } else if (stageNumber == 1 || stageNumber == 2 || stageNumber == 3) {
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
               driveWithGyro(bot.right, bot.left, 4, 0, bot.gyro);
               if (bot.right.getPower() == 0) {
                   bot.leftServo.setPosition(bot.leftUp);
                   stageNumber++;
               }
            } else if (stageNumber == 7) {
                driveWithGyro(bot.right,bot.left,28,0,bot.gyro);
                if (bot.right.getPower() == 0) {
                    time.reset();
                    while (time.milliseconds() < 2500) {
                        bot.arm.setPower(0.75d);
                    }
                    bot.arm.setPower(0);
                    time.reset();
                    bot.box.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    while (time.milliseconds() < 1500) {
                        bot.box.setPower(-1);
                    }
                    bot.box.setPower(0);
                    time.reset();
                    while (time.milliseconds() < 500) {
                        bot.right.setPower(0.5d);
                        bot.left.setPower(0.5d);
                        bot.lintake.setPower(-1);
                        bot.rintake.setPower(-1);
                    }
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    time.reset();
                    while (time.seconds() < 2) {
                        bot.arm.setPower(-1);
                    }
                    bot.arm.setPower(0);
                    bot.lintake.setPower(0);
                    bot.rintake.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 8) {
                stop();
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color, (Red value | Blue value)", color+", ("+bot.leftColorSensor.red() + " | " + bot.leftColorSensor.blue()+")")
                    .addData("", "")
                    .addData("Angle (all angles)", angles.firstAngle+ "("+ angles + ")")
                    .addData("", "")
                    .addData("right pos", bot.right.getCurrentPosition())
                    .addData("right target (∆)", bot.right.getTargetPosition() + " (" + Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()) + ")");
            telemetry.update();

        }
        telemetry.addData("Status", "Done");
        telemetry.update();
    }

}
