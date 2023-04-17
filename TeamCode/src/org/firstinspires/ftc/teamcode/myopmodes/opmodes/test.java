package org.firstinspires.ftc.teamcode.myopmodes.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.myopmodes.subsystems.drivetrain;

/**
 * Example OpMode. Demonstrates use of gyro, color sensor, encoders, and telemetry.
 *
 */
@Autonomous(name = "gyro test", group = "opmode")
public class test extends LinearOpMode {

    public void runOpMode(){
        ElapsedTime t = new ElapsedTime();
        drivetrain d = new drivetrain(hardwareMap);
        telemetry.addData("Press Start When Ready","");
        telemetry.update();
        d.resetAngle();

        waitForStart();
        while (opModeIsActive()){
            t.reset();
            String stage = "showing off gyro as encoder";
            while (t.seconds() < 5 && stage.equals("showing off gyro as encoder")) {
                telemetry.addData("status", stage);
                telemetry.addData("remaining time", 5-t.seconds());
                telemetry.addData("angle", d.getAngle());
                telemetry.update();
                d.SP("l", 1);
                d.SP("r", -1);
            }
            d.SP("dt",0);

            t.reset();
            d.resetAngle();
            stage = "showing off maintainHeading";
            while (t.seconds() < 2 && stage.equals("showing off maintainHeading")) {
                d.maintainHeading("f");
                telemetry.addData("status", stage);
                telemetry.addData("remaining time", 2-t.seconds());
                telemetry.addData("angle", d.getAngle());
                telemetry.update();
            }
            d.SP("dt",0);

            stage = "eof";
            while (stage.equals("eof")) {
                d.SP("dt",0);
                telemetry.addData("status", "end of file");
                telemetry.addData("angle", d.getAngle());
                telemetry.update();
                sleep(1);
            }
        }
    }
}
