package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;

/**
 * Created by Stephen Ogden on 10/23/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

// 560 ticks per rotation
// 4 in diameter
// 89 ticks per inches

// This is for 6128

@Autonomous(name = "Blue Auto", group = "Test")
@Disabled

@Deprecated
public class BlueAuto extends LinearOpMode {



    public static final String TAG = "Vuforia VuMark Sample";
    private final double SERVOUPPOS = .5;
    private final double SERVODOWNPOS = 0;
    private float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    private String color = null;
    private String imageLocation = null;
    // StageNumber > -1 means running
    private int stageNumber = 0;

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {

        //<editor-fold desc="Initialization">
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        /*
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUgZTU3/////AAAAGaQ5yTo6EkZqvsH9Iel0EktQjXWAZUz3q3FPq22sUTrmsYCccs/mjYiflQBH2u7lofbTxe4BxTca9o2EOnNwA8dLGa/yL3cUgDGjeRfXuwZUCpIG6OEKhiPU5ntOpT2Nr5uVkT3vs2uRr7J6G7YoaGHLw2i1wGncRaw37rZyO03QRh0ZatdKIiK1ItuvJkP3qfUJwQwcpROwa+ZdDNQDbpU6WTL+kPZpnkgR8oLcu+Na1lWrbJ2ZTYG8eUjoIGowbVVGJgORHJazy6/7MbYH268h9ZC4vZ12ItyDK/GlPRTeQWdcZRlWfzAAFwNrjmdjWv9hMuOMoWxo2Y2Rw1Fwii4ohLyRmcQa/wAWY+AOEL14";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        RelicRecoveryVuMark vuMark;
        */

        bot.getConfig(hardwareMap);
        bot.getVision(hardwareMap);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        bot.vision.activate();
        //</editor-fold>


        while (opModeIsActive()) {

            //<editor-fold desc="Move down servo">
            if (stageNumber == 0) {
                //bot.servo.setPosition(SERVODOWNPOS);
                //colorSensor.enableLed(false);
                CameraDevice.getInstance().setFlashTorchMode(false);
                stageNumber++;
            }
            if (stageNumber == 1) {
                //if (servo.getPosition() == SERVODOWNPOS) {
                    stageNumber++;
                //}
            }
            //</editor-fold>

            //<editor-fold desc="Detect Jewel Color">
            if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                //colorSensor.enableLed(true);
                //if (colorSensor.red() > colorSensor.blue() && colorSensor.green() < colorSensor.red()) {
                    color = "Red";
                    stageNumber++;
                //} else if (colorSensor.red() < colorSensor.blue() && colorSensor.green() < colorSensor.blue()) {
                    color = "Blue";
                    stageNumber++;
                //} else {
                    color = "Unknown :(";
                //}
            }
            //</editor-fold>

            //<editor-fold desc="Drive based on Jewel Color">
            if (stageNumber == 5) {
                switch (color) {
                    case "Red": {
                        driveToPosition(bot.left, -1);
                        driveToPosition(bot.right, 1);
                        stageNumber++;
                    }
                    case "Blue": {
                        driveToPosition(bot.left, 1);
                        driveToPosition(bot.right, -1);
                        stageNumber++;
                    }
                }
            }

            if (stageNumber == 6) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    //servo.setPosition(SERVOUPPOS);
                    stageNumber++;
                }

            }
            //</editor-fold>

            //<editor-fold desc="Drive back to position">
            if (stageNumber == 7) {
                switch (color) {
                    case "Red": {
                        driveToPosition(bot.left, 1);
                        driveToPosition(bot.right, -1);
                        stageNumber++;
                    }
                    case "Blue": {
                        driveToPosition(bot.left, -1);
                        driveToPosition(bot.right, 1);
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

            // Scan image, if all else fails, go center
            if (stageNumber == 9) {
                switch (bot.vuMark) {
                    case LEFT: {
                        imageLocation = "Left";
                        stageNumber++;
                    }
                    case RIGHT: {
                        imageLocation = "Right";
                        stageNumber++;
                    }
                    case CENTER: {
                        imageLocation = "Center";
                        stageNumber++;
                    }
                    case UNKNOWN: {
                        imageLocation = "Center"; // Go to center if unknown
                        stageNumber++;
                    }
                }
            }

            if (stageNumber == 10) {
                // Drive 36 In to get before key thing
                driveToPosition(bot.left, -36);
                driveToPosition(bot.right, 36);
                stageNumber++;
            }
            if (stageNumber == 11) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            }

            if (stageNumber == 12) {
                // Turn 90 degrees clockwise
                driveToPosition(bot.left, -4);
                driveToPosition(bot.right, 4);
                stageNumber++;
            }
            if (stageNumber == 13) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            }

            // Drive depending on image

            // Mark as done
            if (stageNumber == 14) {
                bot.left.setPower(0);
                bot.right.setPower(0);
                stageNumber = -1;
            }

            //<editor-fold desc="Telemetry and Stop">
            if (stageNumber >= 0) {
                telemetry.addData("Stage number", stageNumber)
                        .addData("Color", color)
                        .addData("Image Location", imageLocation)
                        .addData("", "")
                        //.addData("Red value", colorSensor.red())
                        //.addData("Blue value", colorSensor.blue())
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
}
