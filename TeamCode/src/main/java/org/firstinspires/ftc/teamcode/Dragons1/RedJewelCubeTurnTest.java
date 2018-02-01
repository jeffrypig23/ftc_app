package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Stephen Ogden on 12/14/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Red Jewel Cube turn Test", group = "Test")
//@Disabled
public class RedJewelCubeTurnTest extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getAutoConfig(hardwareMap);
        bot.getVision(hardwareMap);
        bot.resetEncoder();

        int stageNumber = 6;

        double colorValue = 0.0;

        String color = "";
        RelicRecoveryVuMark pos = RelicRecoveryVuMark.UNKNOWN;

        bot.arm.setPower(0);

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        bot.vision.activate();
        while (opModeIsActive()) {

            // TODO: Re-evaluate jewel code, and once done, insert here!
            if (stageNumber == 6) {
                pos = bot.getVuMark();
                if (!pos.equals(RelicRecoveryVuMark.UNKNOWN)) {
                    stageNumber++;
                }
            } else if (stageNumber == 7) {
                //<editor-fold desc="Go forward based on distance from image">
                // Do one for center, left, and right
                if (pos.equals(RelicRecoveryVuMark.LEFT)) {
                    // First one
                    bot.driveWithGyroPID(-19, 2);
                } else if (pos.equals(RelicRecoveryVuMark.CENTER)) {
                    // Second one
                    bot.driveWithGyroPID(-36, 2);
                } else if (pos.equals(RelicRecoveryVuMark.RIGHT)) {
                    // Last one
                    bot.driveWithGyroPID(-40, 2);
                } else {
                    stageNumber--;
                }
                if (bot.right.getPower() == 0) {
                    //stageNumber++;
                    // TODO: Change me back when ready!
                    stageNumber = 12;
                }

            } else if (stageNumber == 8) {
                //<editor-fold desc="Turn 90 degrees">
                bot.turn(-90);
                if (bot.right.getPower() == 0) {
                    bot.resetEncoder();
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 9) {
                //<editor-fold desc="Backup slightly">
                bot.driveWithGyroPID(-4,-90);
                if (bot.right.getPower() == 0 || bot.isThere(bot.right, 20)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                    time.reset();
                }
                //</editor-fold>
            } else if (stageNumber == 10) {
                //<editor-fold desc="Raise your dongers! ヽ༼ຈل͜ຈ༽ﾉ">
                while (time.milliseconds() < 500) {
                    // bot.arm.setPower(1);
                }
                bot.arm.setPower(0);
                stageNumber++;
                //</editor-fold>
            } else if (stageNumber == 11) {
                //<editor-fold desc="Drive forward a tiny bit to get away from the box">
                bot.driveWithGyroPID(4,-90);
                if (bot.right.getPower() == 0 || bot.isThere(bot.right, 20)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 12) {
                return;
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Determined color, (Red value | Blue value)", color+", ("+bot.leftColorSensor.red() + " | " + bot.leftColorSensor.blue()+")")
                    .addData("Special column", bot.getVuMark())
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