package org.firstinspires.ftc.teamcode.myopmodes.opmodes;

import static java.lang.Math.*;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "this is a good educational experience", group = "opmode")
public class ConvinceJess extends LinearOpMode {
    // global initializations here
    ElapsedTime timer = new ElapsedTime();
    static DcMotorEx frontLeft, frontRight,backLeft,backRight;
    Orientation lastAngles = new Orientation();
    double globalAngle;
    IMU imu;
    // drivetrain info
    static final double diameter_dt=2; // wheel diameter
    static final int motor_ratio_dt=40; // ratio on motor
    static final int gear_ratio_up_dt=1; // gears on driveshaft, direct drive = 1
    static final int gear_ratio_down_dt=1; // gears on driveshaft, direct drive = 1
    public void runOpMode(){
        // initializations here
        frontLeft = hardwareMap.get(DcMotorEx.class, "front_left_motor");
        frontRight = hardwareMap.get(DcMotorEx.class, "front_right_motor");
        backLeft = hardwareMap.get(DcMotorEx.class, "back_left_motor");
        backRight = hardwareMap.get(DcMotorEx.class, "back_right_motor");
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        imu = hardwareMap.get(IMU.class, "imu");
        DistanceSensor frontDistance = hardwareMap.get(DistanceSensor.class, "front_distance");
        DistanceSensor leftDistance = hardwareMap.get(DistanceSensor.class, "left_distance");
        DistanceSensor rightDistance = hardwareMap.get(DistanceSensor.class, "right_distance");
        DistanceSensor backDistance = hardwareMap.get(DistanceSensor.class, "back_distance");

        String stage = "initalized";
        telemetry.addData("status", stage);
        telemetry.update();
        waitForStart();

        while (opModeIsActive()){
            stage = "running";
            telemetry.addData("status", stage);
            telemetry.update();

            STP("dt",inTT_dt(13*2));
            RTP("dt");
            SP("dt",1);
            while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()){}
            SP("dt",0);

            SAR("dt");
            STP("fl",inTT_dt(13/2));
            STP("fr",inTT_dt(-13/2));
            STP("bl",inTT_dt(-13/2));
            STP("br",inTT_dt(13/2));
            RTP("dt");
            SP("dt",1);
            while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()){}
            SP("dt",0);

            SAR("dt");
            STP("fl",inTT_dt(-13*1.5));
            STP("fr",inTT_dt(13*1.5));
            STP("bl",inTT_dt(13*1.5));
            STP("br",inTT_dt(-13*1.5));
            RTP("dt");
            SP("dt",1);
            while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()){}
            SP("dt",0);

            SAR("dt");
            STP("dt",inTT_dt(-13*2));
            RTP("dt");
            SP("dt",1);
            while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()){}
            SP("dt",0);

            stage = "eof";
            while (stage.equals("eof")) {
                SP("dt",0);
                telemetry.addData("status", stage);
                telemetry.update();
                sleep(1);
            }
        }
    }
    public static void SP (@NonNull String m, double p) {
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
    public static void STP (@NonNull String m, int tp) {
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
    public static void RTP (@NonNull String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"fr":frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"bl":backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"br":backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"f":frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"b":backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"l":frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"r":frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
            case"dt":frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);break;
        }
    }
    public static void SAR (@NonNull String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"fr":frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"bl":backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"br":backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"f":frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"b":backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"l":frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"r":frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);break;
        }
    }
    public static void RWE (@NonNull String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"fr":frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"bl":backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"br":backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"f":frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"b":backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"l":frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"r":frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);break;
        }
    }
    public static void RUE (@NonNull String m) {
        switch(m){
            case"fl":frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"fr":frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"bl":backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"br":backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"f":frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"b":backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"l":frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"r":frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
            case"dt":frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);break;
        }
    }
    public static void ST(int i) {
        frontLeft.setTargetPositionTolerance(i);
        backLeft.setTargetPositionTolerance(i);
        backRight.setTargetPositionTolerance(i);
        frontRight.setTargetPositionTolerance(i);
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
    public void drive (@NonNull String direction, double inches, double speed){
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
    public static double getConversionFactorDT() {
        try {
            return ((motor_ratio_dt*gear_ratio_up_dt*28)/gear_ratio_down_dt)/(diameter_dt*PI);
        }
        catch (ArithmeticException e) {
            return (motor_ratio_dt*28)/(diameter_dt*PI);
        }
    }
    public static int inTT_dt(double inches){
        return (int)round(getConversionFactorDT()*inches);
    }
}
