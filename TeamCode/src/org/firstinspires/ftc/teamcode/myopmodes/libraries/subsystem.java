package org.firstinspires.ftc.teamcode.myopmodes.libraries;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class subsystem {
    HardwareMap hwMap;
    public subsystem(){
        hwMap = null;
    }
    public subsystem(HardwareMap hardwareMap){
        hwMap = hardwareMap;
    }
}
