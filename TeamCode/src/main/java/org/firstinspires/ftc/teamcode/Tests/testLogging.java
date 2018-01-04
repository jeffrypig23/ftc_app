package org.firstinspires.ftc.teamcode.Tests;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Stephen Ogden on 12/16/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Disabled
@Autonomous(name = "Logging test", group = "Test")

public class testLogging extends LinearOpMode {

    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Logger logger = Logger.getLogger("Gary");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:/temp/test/Gary.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Log file successfully created");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        logger.info("Finished initialization\n");
        waitForStart();

        while (opModeIsActive()) {

            logger.info("I have logged successfully ヽ༼ຈل͜ຈ༽ﾉ ");
            stop();

        }

        telemetry.addData("Status", "Done!");
        telemetry.update();
        logger.info("\n\nOpMode ended");

    }

}
