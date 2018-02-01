package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 12/15/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Blue Jewel Cube turn Test", group = "Test")
@Disabled
public class BlueJewelCubeTurnTest extends LinearOpMode {
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
        ElapsedTime time = new ElapsedTime();

        bot.getConfig(hardwareMap);

        int stageNumber = 7;

        double colorValue = 0.0;

        String color = "";

        bot.arm.setPower(0);

        telemetry.addData("Status", "Done");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            // TODO: Re-evaluate jewewl code, and once done, insert here!
            // Todo: Implement vision
            if (stageNumber == 7) {
                //<editor-fold desc="Go forward 30 inches">
                bot.driveWithGyro(-38, -2);
                bot.arm.setPower(0);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
            } else if (stageNumber == 8) {
                //<editor-fold desc="Turn 90 degrees">
                bot.turn(90);
                if (bot.right.getPower() == 0) {
                    bot.resetEncoder();
                    stageNumber++;
                }
                //</editor-fold>
            } else if (stageNumber == 9) {
                //<editor-fold desc="Backup slightly">
                bot.driveWithGyro(-4,90);
                if (bot.right.getPower() == 0 || bot.isThere(bot.right, 20)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber=100;
                    time.reset();
                }
                //</editor-fold>
            } else if (stageNumber == 10) {
                //<editor-fold desc="Raise your dongers! ヽ༼ຈل͜ຈ༽ﾉ">
                while (time.seconds() < 1) {
                    //bot.arm.setPower(1);
                }
                bot.arm.setPower(0);
                stageNumber++;
                //</editor-fold>
            } else if (stageNumber == 11) {
                //<editor-fold desc="Drive forward a tiny bit to get away from the box">
                bot.driveWithGyro(4,90);
                if (bot.right.getPower() == 0) {
                    stageNumber++;
                }
            } else if (stageNumber == 12) {
                return;
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