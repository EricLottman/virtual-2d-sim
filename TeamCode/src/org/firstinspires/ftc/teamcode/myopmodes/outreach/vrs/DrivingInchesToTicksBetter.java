package org.firstinspires.ftc.teamcode.myopmodes.outreach.vrs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "drive amount in inches", group = "opmodes")
public class DrivingInchesToTicksBetter extends LinearOpMode {

    private static DcMotorEx frontRight,frontLeft,backRight,backLeft;
    static final double diameter_dt=2; // wheel diameter
    static final int ticksPerRevolution=1440; // for any hall effect motors (most dc motors) just do 28 * motor ratio
    private static String stage; // to better document the code

    @Override
    public void runOpMode() {
        // put initialization blocks here
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // from the DcMotorEx class, it allows us to define how close to current position we must be before motor.isBusy() returns false
        frontLeft.setTargetPositionTolerance(500);
        backLeft.setTargetPositionTolerance(500);
        backRight.setTargetPositionTolerance(500);
        frontRight.setTargetPositionTolerance(500);

        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

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

            driveInches(24, 0.5); // drive foward 24 inches at 50% power
            driveInches(-12, 0.5); // drive backwards 12 inches at 50% power

            // put end code here
            stage = "eof"; // end of file
            while (stage.equals("eof")) {
                telemetry.addData("status", stage);
                telemetry.update();
                sleep(1); // we dont want to loop entire opmode in most autonomous situations so we sleep for etermity at the end of our file
            }
        }
    }
    // drive a certain amount of inches
    public void driveInches(double inches, double power) {
        stage = "driving";
        double circumference = diameter_dt * Math.PI; // find out how many inches the robot travels per wheel rotation
        double ticksPerRotation = ticksPerRevolution/circumference; // find out how many inches the robot travels per tick
        int ticksToTravel = (int) (ticksPerRotation * inches); // find out how many ticks the robot needs to travel, and cast it to an int because encoder ticks work as an int

        // reset the encoders so we start from scratch
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // tell the robot to move specified amount of ticks, to strafe instead of drive introduce negative values where needed
        // in real life assuming youre using mecanum wheels, there will be an amount of reasonable error due to the physics of rollers on mecanum wheels, particularly when strafing
        // it is suggested you use odemetry to correct for this error, or you can attempt by trial and error to find an ajustment to multiply by to correct for this error
        // lowering the power will also help with this error as the robot wont run in place as much
        frontLeft.setTargetPosition(ticksToTravel);
        frontRight.setTargetPosition(ticksToTravel);
        backLeft.setTargetPosition(ticksToTravel);
        backRight.setTargetPosition(ticksToTravel);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // set the power
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        // dont continue until the motors are done within the tolerance
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("position", frontLeft.getCurrentPosition());
            telemetry.addData("status", stage);
            telemetry.addData("remaining inches", inches-((frontLeft.getCurrentPosition()*inches)/frontLeft.getTargetPosition())); // derived using proportion x/currentposition = inches/targetticks(tickstotravel)
            telemetry.update();
        }

        // brake
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
