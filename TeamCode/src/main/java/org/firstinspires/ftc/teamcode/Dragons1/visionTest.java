package org.firstinspires.ftc.teamcode.Dragons1;

import android.hardware.camera2.CameraDevice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.format;

/**
 * Created by Stephen Ogden on 12/6/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
@TeleOp(name = "Vision test", group = "test")
//@Disabled
public class visionTest extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getVision(hardwareMap);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();

        waitForStart();
        bot.vision.activate();
        while (opModeIsActive()) {
            if (bot.vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("Status", "%s visible", bot.vuMark);
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)bot.relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
            } else {
                telemetry.addData("Status", "Target not visible!");
            }
            telemetry.update();
        }
        com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
        bot.vision.deactivate();
    }
}
