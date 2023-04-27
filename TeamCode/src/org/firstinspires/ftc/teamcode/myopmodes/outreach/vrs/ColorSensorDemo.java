package org.firstinspires.ftc.teamcode.myopmodes.outreach.vrs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "basic color sensor code", group = "opmodes")
public class ColorSensorDemo extends LinearOpMode {


    private DcMotor frontRight,frontLeft, backRight, backLeft, motor5, motor6;
    ColorSensor frontColorSensor;
    int r,g,b;
    String color;

    public void runOpMode() {
        // put initialization blocks here
        frontColorSensor = hardwareMap.get(ColorSensor.class,"frontColorSensor");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // in normal ftc code this would be illegal, you cant move before start is pressed unless it is to initialize parts of your robot
        // however in this sample we need to not be on the wall at the start and we have no choice where the robot starts
        frontLeft.setPower(1);
        frontRight.setPower(1);
        backLeft.setPower(1);
        backRight.setPower(1);
        sleep(300);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        waitForStart();
        // because we used a while loop it'll loop the code
        while (opModeIsActive()) {
            // get rgb values
            r = frontColorSensor.red();
            g = frontColorSensor.green();
            b = frontColorSensor.blue();

            // scan right
            frontLeft.setPower(-.3);
            frontRight.setPower(.3);
            backLeft.setPower(-.3);
            backRight.setPower(.3);

            telemetry.addData("r", r);
            telemetry.addData("g", g);
            telemetry.addData("b", b);
            telemetry.addData("runtime", getRuntime());
            telemetry.update();

//            color=r<25?"green":(r>130&& b>180?"purple":"yellow");
            // if we see a spike check while loop and pause the scanning movement
            if (r>0 || g>0 || b>0) {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                // while it hasnt been 5 seconds yet show the RGB values in telemetry
                telemetry.addData("r", r);
                telemetry.addData("g", g);
                telemetry.addData("b", b);
//                telemetry.addData("color", color);
                telemetry.update();
                sleep(5000);
                // go back to scanning so we get off the color before rechecking if
                frontLeft.setPower(-.3);
                frontRight.setPower(.3);
                backLeft.setPower(-.3);
                backRight.setPower(.3);
                sleep(300);
            }
        }
    }
}
