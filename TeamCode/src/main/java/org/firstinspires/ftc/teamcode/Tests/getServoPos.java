package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SixtyOneTwentyEightConfig;

/**
 * Created by Ethan Hunter on 10/15/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@TeleOp(name = "Servo positions", group = "Test")
//@Disabled
public class getServoPos extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();
    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        //Servo servo = hardwareMap.servo.get("right servo");

        final double SERVOUPPOS = 1;
        final double SERVODOWNPOS = 0;

        double lPos = 0.53;
        double rPos = 0.3;

        boolean left = true;

        bot.getAutoConfig(hardwareMap);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {

            if(gamepad1.a) {
                left=true;
            }
            else if(gamepad1.b) {
                left=false;
            }
            if(left) {
                if(gamepad1.dpad_up) {
                    lPos+=0.001;
                }
                else if(gamepad1.dpad_down)
                    lPos-=0.001;
            }
            else {
                if(gamepad1.dpad_up) {
                    rPos+=0.001;
                }
                else if(gamepad1.dpad_down)
                    rPos-=0.001;
            }
            if (gamepad1.y) {
                rPos = .11;
                lPos = .95;
            }
            if (gamepad1.x) {
                rPos = .3;
                lPos = .53;
            }
            bot.servo.setPosition(lPos);
            bot.spinner.setPosition(rPos);

            telemetry.addData("Servo pos", bot.servo.getPosition())
                    .addData("Spinner pos", bot.spinner.getPosition());
            telemetry.update();
            idle();
        }
    }
}
