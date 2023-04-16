package org.firstinspires.ftc.teamcode.myopmodes.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.myopmodes.subsystems.drivetrain;

/**
 * Example OpMode. Demonstrates use of gyro, color sensor, encoders, and telemetry.
 *
 */
@Autonomous(name = "gyro test", group = "opmode")
public class gyro extends LinearOpMode {

    public void runOpMode(){
        drivetrain d = new drivetrain(hardwareMap);
        telemetry.addData("Press Start When Ready","");
        telemetry.update();
        d.resetAngle();

        waitForStart();
        while (opModeIsActive()){
            String stage = "rotating";
            telemetry.addData("angle ", d.getAngle());
            telemetry.update();
            d.SP("l",1);
            d.SP("r",-1);
//          d.rotatePID("l",90);

//            stage = "eof";
//            while (stage.equals("eof")) {
//                telemetry.addData("status", "end of file");
//                telemetry.update();
//                sleep(1);
//            }

//            telemetry.addData("angle ", d.getAngle());
//            telemetry.update();

        }
    }
}
