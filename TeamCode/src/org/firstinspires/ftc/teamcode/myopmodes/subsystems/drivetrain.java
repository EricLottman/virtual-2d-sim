package org.firstinspires.ftc.teamcode.myopmodes.subsystems;

import static org.firstinspires.ftc.teamcode.myopmodes.subsystems.ease_commands.*;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.myopmodes.libraries.subsystem;

public class drivetrain extends subsystem {
    ElapsedTime timer = new ElapsedTime();
    public static DcMotorEx frontLeft, frontRight,backLeft,backRight;
    private Orientation lastAngles = new Orientation();
    IMU imu;
    private static constants c = new constants();
    double globalAngle, correction;
    double power = c.power;

    public drivetrain(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotorEx.class, "front_left_motor");
        frontRight = hwMap.get(DcMotorEx.class, "front_right_motor");
        backLeft = hwMap.get(DcMotorEx.class, "back_left_motor");
        backRight = hwMap.get(DcMotorEx.class, "back_right_motor");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class, "imu");

        DistanceSensor frontDistance = hwMap.get(DistanceSensor.class, "front_distance");
        DistanceSensor leftDistance = hwMap.get(DistanceSensor.class, "left_distance");
        DistanceSensor rightDistance = hwMap.get(DistanceSensor.class, "right_distance");
        DistanceSensor backDistance = hwMap.get(DistanceSensor.class, "back_distance");

    }

    public static void SP (String m, double p) {
        switch(m){
            case"fl":frontLeft.setPower(p);break;
            case"fr":frontRight.setPower(p);break;
            case"bl":backLeft.setPower(p);break;
            case"br":backRight.setPower(p);break;
            case"f":frontLeft.setPower(p);frontRight.setPower(p);break;
            case"b":backLeft.setPower(p);backRight.setPower(p);break;
            case"l":frontLeft.setPower(p);backLeft.setPower(p);break;
            case"r":frontRight.setPower(p);backRight.setPower(p);break;
            case"dt":frontLeft.setPower(p);frontRight.setPower(p);backLeft.setPower(p);backRight.setPower(p);break;
        }
    }
    public static void STP (String m, int tp) {
        switch(m){
            case"fl":frontLeft.setTargetPosition(tp);break;
            case"fr":frontRight.setTargetPosition(tp);break;
            case"bl":backLeft.setTargetPosition(tp);break;
            case"br":backRight.setTargetPosition(tp);break;
            case"f":frontLeft.setTargetPosition(tp);frontRight.setTargetPosition(tp);break;
            case"b":backLeft.setTargetPosition(tp);backRight.setTargetPosition(tp);break;
            case"l":frontLeft.setTargetPosition(tp);backLeft.setTargetPosition(tp);break;
            case"r":frontRight.setTargetPosition(tp);backRight.setTargetPosition(tp);break;
            case"dt":frontLeft.setTargetPosition(tp);frontRight.setTargetPosition(tp);backLeft.setTargetPosition(tp);backRight.setTargetPosition(tp);break;
        }
    }
    public static void RTP (String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"fr":frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"bl":backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"br":backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"f":frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"b":backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"l":frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"r":frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
            case"dt":frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);break;
        }
    }
    public static void SAR (String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"fr":frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"bl":backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"br":backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"f":frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"b":backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"l":frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"r":frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);break;
        }
    }
    public static void RWE (String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"fr":frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"bl":backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"br":backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"f":frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"b":backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"l":frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"r":frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);break;
        }
    }
    public static void RUE (String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"fr":frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"bl":backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"br":backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"f":frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"b":backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"l":frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"r":frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);break;
        }
    }
    public static void ST(int i) {
        frontLeft.setTargetPositionTolerance(i);
        backLeft.setTargetPositionTolerance(i);
        backRight.setTargetPositionTolerance(i);
        frontRight.setTargetPositionTolerance(i);
    }

    public void drive (String direction, double inches, double speed){
        SAR("dt");
        RUE("dt");
        switch(direction){
            case"f":
                STP("dt",inTT_dt(inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"b":
                STP("dt",inTT_dt(-inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"l":
                STP("fl",inTT_dt(-inches));
                STP("fr",inTT_dt(inches));
                STP("bl",inTT_dt(inches));
                STP("br",inTT_dt(-inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"r":
                STP("fl",inTT_dt(inches));
                STP("fr",inTT_dt(-inches));
                STP("bl",inTT_dt(-inches));
                STP("br",inTT_dt(inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"fr":
                STP("fl",inTT_dt(inches));
                STP("fr",inTT_dt(0));
                STP("bl",inTT_dt(0));
                STP("br",inTT_dt(inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"bl":
                STP("fl",inTT_dt(-inches));
                STP("fr",inTT_dt(0));
                STP("bl",inTT_dt(0));
                STP("br",inTT_dt(-inches));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"fl":
                STP("fl",inTT_dt(0));
                STP("fr",inTT_dt(inches));
                STP("bl",inTT_dt(inches));
                STP("br",inTT_dt(0));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
            case"br":
                STP("fl",inTT_dt(0));
                STP("fr",inTT_dt(-inches));
                STP("bl",inTT_dt(-inches));
                STP("br",inTT_dt(0));
                SP("dt",speed);
                RTP("dt");
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}
                SP("dt",0);
        }
    }

    public void resetAngle() {
        lastAngles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
    public double getAngle() {
        // imu works in eulear angles so we have to detect when it rolls across the backwards 180 threshold
        Orientation angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double change = angles.firstAngle - lastAngles.firstAngle;

        if (change < -180) {
            change += 360;
        } else if (change > 180) {
            change -= 360;
        }
        globalAngle += change;
        lastAngles = angles;
        return globalAngle;
    }

    private double checkDirection() {
        // the gain value determines how sensitive the correction is to direction changes.
        // you will have to experiment with your robot to get small smooth direction changes to stay on a straight line
        // maybe we can make it proportional
        double correction, angle, gain = .10;
        angle = getAngle();

        if (angle == 0) {
            correction = 0; // no adjustment.
        } else {
            correction = -angle; // reverse sign of angle for correction since thats the angle we want to undo
        }
        correction *= gain;
        return correction;
    }

    public void maintainHeading(String direction) {
        // call resetAngle() prior to calling this function
        // must be called in a loop where it loops until a condition is met
        // power adjustment, + is adjust left - is adjust right
        // add correction for counterclock subtract for clock
        // for diagonals it might be wise to add correction to the 0 power wheels (+ for right - for left)
        // alternatively it might be wise to instead multiply the correction of the wheels in use
        RWE("dt");
        switch (direction){
            case"f":
                correction = checkDirection();
                SP("l", (power - correction));
                SP("r", (power + correction));
                return;
            case"b":
                correction = checkDirection();
                SP("l", (-power - correction));
                SP("r", (-power + correction));
                return;
            case"l":
                correction = checkDirection();
                SP("fl",-power + correction);
                SP("fr",power + correction);
                SP("bl",power - correction);
                SP("br",-power - correction);
                return;
            case"r":
                correction = checkDirection();
                SP("fl",power - correction);
                SP("fr",-power - correction);
                SP("bl",-power + correction);
                SP("br",power + correction);
                return;
            case"fr":
                correction = checkDirection();
                SP("fl",power - correction);
                SP("fr",0);
                SP("bl",0);
                SP("br",power + correction);
                return;
            case"bl":
                correction = checkDirection();
                SP("fl",-power - correction);
                SP("fr",0);
                SP("bl",0);
                SP("br",-power + correction);
                return;
            case"fl":
                correction = checkDirection();
                SP("fl",0);
                SP("fr",power + correction);
                SP("bl",power - correction);
                SP("br",0);
                return;
            case"br":
                correction = checkDirection();
                SP("fl",0);
                SP("fr",-power + correction);
                SP("bl",-power - correction);
                SP("br",0);
                return;
            default: return;
        }
    }

    public void maintainHeading() {
        // call resetAngle() prior to calling this function
        // must be called in a loop where it loops until a condition is met
        RWE("dt");
        correction = checkDirection();
        SP("l", (-correction));
        SP("r", (correction));
    }


    public double maintainHeadingPID(String direction, double previousError){
        // call resetAngle() prior to calling this function
        // this function must be called in a loop where it loops until a condition is met
        // power adjustment, + is adjust left - is adjust right
        // add correction for counterclock subtract for clock
        // for diagonals it might be wise to add correction to the 0 power wheels (+ for right - for left)
        // alternatively it might be wise to instead multiply the correction of the wheels in use
        RWE("dt");
        double angle,currentError,accumulatedError,derivative,P,I,D,p; //p is power of adjustment
        accumulatedError=0;

        P = c.P;
        I = c.I;
        D = c.D;

        // basic rules for tuning
        // p means youre going fast the further you are from your target
        // i means if you hit a rough patch or arent getting to speed we increase power over time to get there
        // d means depending on how big of a spike from the rate of change, we slow down to prevent overshoot
        // the larger the spike the more it dampens, so if youve been slow on a bump and i gets high enough to pass the bump
        // then all of a sudden in one loop youve moved 4x the degrees you normally do, d will spike up to slow you down so you dont overshoot

        // degrees pos and neg may need to be switched
        // we have to return a value since it needs to be a closed loop
        switch (direction) {
            case"f":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("l", power - p);
                SP("r", power + p);
                return previousError;
            case"b":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("l", -power - p);
                SP("r", -power + p);
                return previousError;
            case"l":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",-power + p);
                SP("fr",power + p);
                SP("bl",power - p);
                SP("br",-power - p);
                return previousError;
            case"r":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",power - p);
                SP("fr",-power - p);
                SP("bl",-power + p);
                SP("br",power + p);
                return previousError;
            case"fr":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",power - p);
                SP("fr",0);
                SP("bl",0);
                SP("br",power + p);
                return previousError;
            case"bl":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",-power - p);
                SP("fr",0);
                SP("bl",0);
                SP("br",-power + p);
                return previousError;
            case"fl":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",0);
                SP("fr",power + p);
                SP("bl",power - p);
                SP("br",0);
                return previousError;
            case"br":
                angle = getAngle();
                currentError = 0 - angle;
                accumulatedError += currentError;
                derivative = currentError - previousError;
                previousError = currentError;
                p = P * currentError + I * accumulatedError + D * derivative;
                SP("fl",0);
                SP("fr",-power + p);
                SP("bl",-power - p);
                SP("br",0);
                return previousError;
            default: return previousError;
        }
    }

    public void rotateSample(int degrees, double power) {
        double  lp, rp; // left and right power

        // restart imu movement tracking.
        resetAngle();
        RWE("dt");

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating clockwise (right)
        if (degrees < 0) { // turn right.
            lp = power; rp = -power;
        } else if (degrees > 0) {   // turn left.
            lp = -power; rp = power;
        }
        else return;

        // set power to rotate.
        SP("l",lp);
        SP("r",rp);

        // rotate until turn is completed.
        if (degrees < 0) {
            // on right turn we have to get off zero first.
            while (getAngle() == 0) {}
            while (getAngle() > degrees) {}
        } else {
            while (getAngle() < degrees) {}
        }

        // turn the motors off.
        SP("r",0);
        SP("l",0);

        // wait for rotation to stop.
        // sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
    public void rotatePID(String direction, double degrees) {
        RWE("dt");
        resetAngle();
        double angle,target,currentError,previousError,integralSum,derivative,P,I,D,p; //p is power of adjustment
        previousError=integralSum=0;

        P = c.P;
        I = c.I;
        D = c.D;

        boolean reachedPosition = false;
        // degrees pos and neg may need to be switched
        switch (direction) {
            case"l":
                target = degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target - angle;
                    integralSum += currentError;
                    derivative = currentError - previousError;
                    previousError = currentError;
                    p = P * currentError + I * integralSum + D * derivative;
                    SP("l", -p);
                    SP("r", p);
                    if (target>angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            case"r":
                target = -degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target - angle;
                    integralSum += currentError;
                    derivative = currentError - previousError;
                    previousError = currentError;
                    p = P * currentError + I * integralSum + D * derivative;
                    SP("l", -p);
                    SP("r", p);
                    if (target<angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            default: return;
        }
    }
    public void rotatePIDv2 (String direction, double degrees) {
        double P,I,D,target,angle,currentError,derivative,lastError,integralSum,p;
        integralSum=lastError=0;

        P = c.P;
        I = c.I;
        D = c.D;

        resetAngle();
        timer.reset();
        boolean reachedPosition = false;
        // degrees pos and neg may need to be switched
        switch (direction) {
            case"l":
                target = degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target-angle;
                    integralSum += (currentError*timer.seconds());
                    derivative = (currentError-lastError)/timer.seconds();
                    p = (P*currentError)+(I*integralSum)+(D*derivative);
                    lastError = currentError;
                    timer.reset();
                    SP("l", -p);
                    SP("r", p);
                    if (target>angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            case"r":
                target = -degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target-angle;
                    integralSum += (currentError*timer.seconds());
                    derivative = (currentError-lastError)/timer.seconds();
                    p = (P*currentError)+(I*integralSum)+(D*derivative);
                    lastError = currentError;
                    timer.reset();
                    SP("l", p);
                    SP("r", -p);
                    if (target<angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            default: return;
        }
    }
    public void rotatePIDv3 (String direction, double degrees) {
        double P,I,D,target,angle,currentError,derivative,lastError,integralSum,p,integralSumLimit;
        integralSum=lastError=0;

        P = c.P;
        I = c.I;
        D = c.D;

        resetAngle();
        timer.reset();
        boolean reachedPosition = false;
        // degrees pos and neg may need to be switched
        switch (direction) {
            case"l":
                target = degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target-angle;
                    integralSum += (currentError*timer.seconds());
                    derivative = (currentError-lastError)/timer.seconds();
                    integralSumLimit = c.integralSumLimitEst/I!=0?I:.00000001; // alternately we can use back-calculation and tracking
                    // conditionally integrating, essentially holding the integration factor constant if we suspect windup and oversaturation
                    // if "I!=0?I:.00000001" doesnt work just set it to I, i didnt feel like making a try catch for /0 error
                    if (integralSum > integralSumLimit) {
                        integralSum = integralSumLimit;
                    }
                    if (integralSum < -integralSumLimit) {
                        integralSum = -integralSumLimit;
                    }
                    p = (P*currentError)+(I*integralSum)+(D*derivative);
                    lastError = currentError;
                    timer.reset();
                    SP("l", -p);
                    SP("r", p);
                    if (target>angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            case"r":
                target = -degrees;
                while (!reachedPosition) {
                    angle = getAngle();
                    currentError = target-angle;
                    integralSum += (currentError*timer.seconds());
                    derivative = (currentError-lastError)/timer.seconds();
                    integralSumLimit = c.integralSumLimitEst/I!=0?I:.00000001;
                    if (integralSum > integralSumLimit) {
                        integralSum = integralSumLimit;
                    }
                    if (integralSum < -integralSumLimit) {
                        integralSum = -integralSumLimit;
                    }
                    p = (P*currentError)+(I*integralSum)+(D*derivative);
                    lastError = currentError;
                    timer.reset();
                    SP("l", p);
                    SP("r", -p);
                    if (target<angle) { // may need to switch if it doesnt terminate
                        reachedPosition = true;
                    }
                } SP("dt", 0); return; //SP is probably a bit repetitive here but id rather be safe
            default: return;
        }
    }
    // try to make a low pass filter for derivative
    // try to implement feedfoward to predict how PID will operate
}
