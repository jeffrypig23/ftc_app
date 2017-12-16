package org.firstinspires.ftc.teamcode.Tests;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Stephen Ogden on 12/16/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

//@Disabled
@Autonomous(name = "Logging test", group = "Test")

public class testLogging extends LinearOpMode {

    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        //String path = Environment.getExternalStorageDirectory().toString();
        File file = new File("Gary.txt");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        writer.println("Finished initialization\n");
        waitForStart();

        while (opModeIsActive()) {

            writer.println("I have logged successfully ヽ༼ຈل͜ຈ༽ﾉ ");
            stop();

        }

        telemetry.addData("Status", "Done!");
        telemetry.update();
        writer.println("\n\nOpMode ended");
        writer.close();

    }

}
