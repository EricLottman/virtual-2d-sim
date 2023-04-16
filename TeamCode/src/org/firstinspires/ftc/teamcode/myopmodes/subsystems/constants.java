package org.firstinspires.ftc.teamcode.myopmodes.subsystems;

import static org.firstinspires.ftc.teamcode.myopmodes.subsystems.ease_commands.*;
import static java.lang.Math.*;

public class constants {

    // drivetrain info
    final double diameter_dt=4; // wheel diameter
    final int motor_ratio_dt=40; // ratio on motor
    final int gear_ratio_up_dt=1; // gears on driveshaft, direct drive = 1
    final int gear_ratio_down_dt=1; // gears on driveshaft, direct drive = 1

    // diameter assuming spool is direct circle
    // if not it's the amount of string displaced per rotation saved
    // the one not in use must be set to 0
    // if using displaced string you can change the units being used
    final double spool_diameter=4;
    final double spool_displacement=0;
    final String displacement_units="in";
    final int motor_ratio_linear_slide=40;
    final int gear_ratio_up_linear_slide=1; // gears on shaft, direct drive = 1
    final int gear_ratio_down_linear_slide=1; // gears on shaft, direct drive = 1

    // default power for closed loop methods
    // its kinda like a "scanning" speed
    final double power = .30;

    // PID algorithm values
    final double P = 1; // gain
    final double I = 0; // reset
    final double D = 0; // react
    // basic rules for tuning
    // p means youre going fast the further you are from your target
    // i means if you hit a rough patch or arent getting to speed we increase power over time to get there
    // d means depending on how big of a spike from the rate of change, we slow down to prevent overshoot
    // the larger the spike the more it dampens, so if youve been slow on a bump and i gets high enough to pass the bump
    // then all of a sudden in one loop youve moved 4x the degrees you normally do, d will spike up to slow you down so you dont overshoot

    // manual tuning
    // start with P, I, and D at 0
    // increase P until steady-state error is very low (nearly hits position)
    // increase I until steady-state error is removed entirely (hits position)
    // increase D until oscillations are removed (doesn't overshoot)

    // if its not moving as fast as you want increase p so it ramps up faster (if you do this often you may have to increase d)
    // if its unstable and overshoots turn up d


    // int sum lim est controls how far from steady state we have to be before we set integral to constant
    final double integralSumLimitEst = .25;


    // to calculate distances
    // assuming its a hall effect encoder then *28 accounts for the rises and falls for the channels
    final double conversion_factor_dt = getConversionFactorDT();
    final double conversion_factor_linear_slide = getConversionFactorLinearSlide();

    public double getConversionFactorDT() {
        try {
            return ((motor_ratio_dt*gear_ratio_up_dt*28)/gear_ratio_down_dt)/(diameter_dt*PI);
        }
        catch (ArithmeticException e) {
            return (motor_ratio_dt*28)/(diameter_dt*PI);
        }
    }
    public double getConversionFactorLinearSlide() {
        double stringperrotation=spool_diameter!=0?spool_diameter:toIN(spool_displacement, displacement_units);
        try {
            return ((motor_ratio_linear_slide*gear_ratio_up_linear_slide*28)/gear_ratio_down_linear_slide)/(stringperrotation*PI);
        }
        catch (ArithmeticException e) {
            return (motor_ratio_linear_slide*28)/(stringperrotation*PI);
        }
    }
}
