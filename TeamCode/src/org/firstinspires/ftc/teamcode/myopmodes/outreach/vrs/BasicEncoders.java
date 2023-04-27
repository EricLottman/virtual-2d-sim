package org.firstinspires.ftc.teamcode.myopmodes.outreach.vrs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
this is a basic use of encoders, there is more you can do with encoders, to see more check out the DcMotorEx class
when using encoders we should always set power to 1 if we want the motor to move,
as setting power to -1 will just confuse the motor, we are telling it where to go and giving it power, itll pick directions itself.
ticks is a measurement of rotation, ccp is something often described on specs when buying a motor, it means counts per rotation.
with hall effect motors (most anything youll use in ftc) you can get the ccp by doing 28 times the motor ratio
 */

@Autonomous(name = "basic encoder implementation", group = "opmodes")
public class BasicEncoders extends LinearOpMode {

    DcMotor frontRight,frontLeft,backRight,backLeft;
    String stage;

    @Override
    public void runOpMode() {
        // put initialization blocks here
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // setting mode to stop and reset makes our current tick value 0
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // we are telling the code we want to use encoders not just power
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // the following code although seemingly pointless is to prevent the hub from being confused about whether or not we are functioning just as encoders
        // there are occasions where despite saying its running with encoders the hub may thing setPower is being used without encoders, so this is just to be safe
        // tell motor to set its goal to 0 (starting position)
        frontLeft.setTargetPosition(0);
        frontRight.setTargetPosition(0);
        backLeft.setTargetPosition(0);
        backRight.setTargetPosition(0);
        // tell the motor to go
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


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

            stage = "go forward 3000 ticks";
            frontLeft.setTargetPosition(3000);
            frontRight.setTargetPosition(3000);
            backLeft.setTargetPosition(3000);
            backRight.setTargetPosition(3000);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);
            // isBusy() checks to see if our current position is at our target position
            // in this case we are using this just so the code doesnt continue and is stuck in a loop until we get to our position
            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addData("status", stage);
                // getTargetPosition tells us where we last told the code to setTargetPosition
                // getCurrentPosition tells us where we are right now
                telemetry.addData("remaining ticks", Math.abs(frontLeft.getTargetPosition()-frontLeft.getCurrentPosition()));
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            stage = "go back 2000 ticks";
            // because we havent reset our encoders back 2000 means we are going to 1000 ticks (3000-2000=1000)
            frontLeft.setTargetPosition(1000);
            frontRight.setTargetPosition(1000);
            backLeft.setTargetPosition(1000);
            backRight.setTargetPosition(1000);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);
            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addData("status", stage);
                // getTargetPosition tells us where we last told the code to setTargetPosition
                // getCurrentPosition tells us where we are right now
                telemetry.addData("remaining ticks", Math.abs(frontLeft.getTargetPosition()-frontLeft.getCurrentPosition()));
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // reset encoders
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            stage = "forward 2000";
            // because we reset encoders we are currently at 0, which makes 2000 ahead of us 2000
            frontLeft.setTargetPosition(2000);
            frontRight.setTargetPosition(2000);
            backLeft.setTargetPosition(2000);
            backRight.setTargetPosition(2000);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);
            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addData("status", stage);
                // getTargetPosition tells us where we last told the code to setTargetPosition
                // getCurrentPosition tells us where we are right now
                telemetry.addData("remaining ticks", Math.abs(frontLeft.getTargetPosition()-frontLeft.getCurrentPosition()));
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            stage = "strafe right 4000";
            // because we didnt reset it and dont feel like retyping the last target position or if we dont want to reset many times
            // we can just add the new target as out goal plus where we currently are
            frontLeft.setTargetPosition(4000+frontLeft.getCurrentPosition());
            frontRight.setTargetPosition(-4000+frontRight.getCurrentPosition());
            backLeft.setTargetPosition(-4000+backLeft.getCurrentPosition());
            backRight.setTargetPosition(4000+backRight.getCurrentPosition());
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);
            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addData("status", stage);
                // getTargetPosition tells us where we last told the code to setTargetPosition
                // getCurrentPosition tells us where we are right now
                telemetry.addData("remaining ticks", Math.abs(frontLeft.getTargetPosition()-frontLeft.getCurrentPosition()));
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // reset encoders
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            stage = "pivot right 2000 ticks";
            frontLeft.setTargetPosition(2000);
            frontRight.setTargetPosition(-2000);
            backLeft.setTargetPosition(2000);
            backRight.setTargetPosition(-2000);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            backRight.setPower(.5);
            while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addData("status", stage);
                // getTargetPosition tells us where we last told the code to setTargetPosition
                // getCurrentPosition tells us where we are right now
                telemetry.addData("remaining ticks", Math.abs(frontLeft.getTargetPosition()-frontLeft.getCurrentPosition()));
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // put end code here
            // note this is only here because the opModeIsActive loop is while not if, i just like how it looks but at the end of the day feel free to do you
            stage = "eof"; // end of file
            while (stage.equals("eof")) {
                telemetry.addData("status", stage);
                telemetry.update();
                sleep(1); // we dont want to loop entire opmode in most autonomous situations so we sleep for eternity at the end of our file
            }
        }
    }
}