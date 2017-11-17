package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Ethan Hunter on 10/15/17.
 * FTC 6128 | 7935
 * FRC 1595
 */
// Left up is  .53| down is .95
// Right up is .3 | down is .11
@TeleOp(name = "6128 Servo up and down", group = "Test")
//@Disabled
public class getServoPos extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        //Servo servo = hardwareMap.servo.get("right servo");

        final double SERVOUPPOS = 1;
        final double SERVODOWNPOS = 0;
        double lPos = 0.53;
        double rPos = 0.3;
        boolean left = true;

        bot.getConfig(hardwareMap);

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
            bot.leftServo.setPosition(lPos);
            bot.rightServo.setPosition(rPos);

            telemetry.addData("Right servo pos", bot.rightServo.getPosition())
                    .addData("Left servo pos", bot.leftServo.getPosition());
            telemetry.update();

            idle();
        }
    }
}
