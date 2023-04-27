package org.firstinspires.ftc.teamcode.myopmodes.outreach.reddit;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// i used DcMotorEx class rather than DcMotor because setting a tolerance allows our code to be less jumpy
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
// idk the Gyroscope class you were using but it just doesnt exist in ftc, at least not where i looked
// maybe you meant GyroSensor???

/*
in this file i assume you want to have a dynamic ability to change height of the arm, as thats how the code originally appeared
assuming you only wanted a few (2 or more) predetermined positions this would be a whole lot easier, no tuning increment or tolerances,
and way easier to read code for you to understand
 */

@TeleOp
public class ArmTest2 extends LinearOpMode {
    // i like to put anything i need to tune up here so some variables here may seem repetitive but its just neater
    private DcMotorEx arm,leftmotor,rightmotor;
    private Servo claw;
    private IMU imu;
    private DigitalChannel touch;
    Orientation angles;
    private double heading;
    // max at 45
    // min at 0 degrees
    // below we can set out angles for max and min
    int minAngle = 0;
    int maxAngle = 45;
    int tolerance = 5; // change how close we are to the target position before we ajust out target position
    // based off of COUNTS_PER_MOTOR_REV var being low i kept the tolerance low,
    // increase this if the code gets jumpy when holding dpad
    int increment = 5; // how much we change each time the code is looped, this is different between motors,
    // based off of COUNTS_PER_MOTOR_REV var being low i kept the increment low,
    // increase it for more drastic changes decrease it for smaller changes when holding dpad

    // just so you know in case you didnt realize you have to fill your own info in here, if you dont know ill rewrite it my way so its easier for you to understand, i already have it premade i just have to find it
    // assuming you dont know your motors count per rotation its 28 * motor internal gear ratio assuming you use a hall effect motor
    static final double COUNTS_PER_MOTOR_REV = 288;
    static final double GEAR_REDUCTION = 2.7778;
    static final double COUNTS_PER_GEAR_REV = COUNTS_PER_MOTOR_REV * GEAR_REDUCTION;
    static final double COUNTS_PER_DEGREE = COUNTS_PER_GEAR_REV/360;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        imu = hardwareMap.get(IMU.class, "imu");
        leftmotor = hardwareMap.get(DcMotorEx.class, "leftmotor");
        rightmotor = hardwareMap.get(DcMotorEx.class, "rightmotor");
        touch = hardwareMap.get(DigitalChannel.class, "touch");

        // set our current position to 0
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        // tell it we are going to use encoders
        arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        // this just means if we set power to 0 the motor will short itself and attempt to stay in place with 0 power
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        // this value deter
        arm.setTargetPositionTolerance(tolerance);
        // tell the motor where to go
        arm.setTargetPosition(0);
        // tell the motor to go
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // here i convert the angle you gave above into a number that is representative of that angle on your motor
        minAngle = (int)(COUNTS_PER_DEGREE*minAngle);
        maxAngle = (int)(COUNTS_PER_DEGREE*maxAngle);

        waitForStart();

        //run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // how to get heading assuming hub is upside up, if not change firstAngle to second or third
            angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = angles.firstAngle;

            arm.setPower(1); // you can set this to whatever needed, as long as its enough power to lift whatever you need youre good

            // if our current position is close enough to our target position and a button is pressed we change our target
            if (gamepad1.dpad_up  && !arm.isBusy()) {
                arm.setTargetPosition(arm.getCurrentPosition()+increment);
            }
            if (gamepad1.dpad_down && !arm.isBusy()) {
                arm.setTargetPosition(arm.getCurrentPosition()-increment);
            }

            // if we are shooting for a target position outside our determined limits, set it back to the limit
            if (arm.getTargetPosition() > maxAngle) {
                arm.setTargetPosition(maxAngle);
            } else if (arm.getTargetPosition() < minAngle) {
                arm.setTargetPosition(minAngle);
            }

            // because this code loops in a while(opModeIsActive()) loop,
            // i only need to tell it to go to its position at the end of the code
            arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            // idk what you were using the touch button for
            telemetry.addData("Arm Test", arm.getCurrentPosition());
            telemetry.update();
        }
    }
}

