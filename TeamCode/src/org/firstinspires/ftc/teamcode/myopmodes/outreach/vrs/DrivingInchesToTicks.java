package org.firstinspires.ftc.teamcode.myopmodes.outreach.vrs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "drive amount in inches", group = "opmodes")
public class DrivingInchesToTicks extends LinearOpMode {

    static final double diameter_dt=2; // wheel diameter
    static final int ticksPerRevolution=1440; // for any hall effect motors (most dc motors) just do 28 * motor ratio
    private String stage; // to better document the code

    @Override
    public void runOpMode() {
        // put initialization blocks here
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stage = "initialized";
        telemetry.addData("status", stage);
        telemetry.addLine("press play to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // put run code here
            stage = "running";
            telemetry.addData("status", stage);
            telemetry.update();

            double inches = 24; // how many inches you want to drive

            stage = "driving";
            double circumference = diameter_dt * Math.PI; // find out how many inches the robot travels per wheel rotation
            double ticksPerRotation = ticksPerRevolution/circumference; // find out how many inches the robot travels per tick
            int ticksToTravel = (int) (ticksPerRotation * inches); // find out how many ticks the robot needs to travel, and cast it to an int because encoder ticks work as an int

            // reset the encoders so we start from scratch
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // tell the robot to move specified amount of ticks, to strafe instead of drive introduce negative values where needed
            // in real life assuming youre using mecanum wheels, there will be an amount of reasonable error due to the physics of rollers on mecanum wheels, particularly when strafing
            // it is suggested you use odemetry to correct for this error, or you can attempt by trial and error to find an ajustment to multiply by to correct for this error
            // lowering the power will also help with this error as the robot wont run in place as much
            frontLeft.setTargetPosition(ticksToTravel);
            frontRight.setTargetPosition(ticksToTravel);
            backLeft.setTargetPosition(ticksToTravel);
            backRight.setTargetPosition(ticksToTravel);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // set the power
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);

            // wait so it can finish moving
            sleep(6000);

            // brake
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // put end code here
            stage = "eof"; // end of file
            while (stage.equals("eof")) {
                telemetry.addData("status", stage);
                telemetry.update();
                sleep(1); // we dont want to loop entire opmode in most autonomous situations so we sleep for etermity at the end of our file
            }
        }
    }
}

