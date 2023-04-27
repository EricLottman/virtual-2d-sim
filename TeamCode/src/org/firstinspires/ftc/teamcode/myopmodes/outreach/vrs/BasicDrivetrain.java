package org.firstinspires.ftc.teamcode.myopmodes.outreach.vrs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
/*
this code is an example of the DcMotorSimple class, it contains all the functions used in the class but getPower which just tells you the last setPower
if you run this in the sim or in real life youll realize the loop isnt perfect, thats because physics exists and we are using no sensors to correct for errors over time
 */
@Autonomous(name = "basic drivetrain using DcMotorSimple (no encoders)", group = "opmodes")
public class BasicDrivetrain extends LinearOpMode {
    DcMotorSimple frontRight,frontLeft,backRight,backLeft;
    @Override
    public void runOpMode() {
        // put initialization blocks here
        frontRight = hardwareMap.get(DcMotorSimple.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorSimple.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorSimple.class, "backRight");
        backLeft = hardwareMap.get(DcMotorSimple.class, "backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        // because we used a while loop it'll loop the circle
        while (opModeIsActive()) {
            // put run code here

            // drive forward
            frontRight.setPower(1);
            frontLeft.setPower(1);
            backRight.setPower(1);
            backLeft.setPower(1);
            sleep(2500);

            // strafe left
            frontRight.setPower(1);
            frontLeft.setPower(-1);
            backRight.setPower(-1);
            backLeft.setPower(1);
            sleep(1100);

            // drive backward
            frontRight.setPower(-1);
            frontLeft.setPower(-1);
            backRight.setPower(-1);
            backLeft.setPower(-1);
            sleep(2500);

            // strafe right
            frontRight.setPower(-1);
            frontLeft.setPower(1);
            backRight.setPower(1);
            backLeft.setPower(-1);
            sleep(1100);
        }
    }
}
