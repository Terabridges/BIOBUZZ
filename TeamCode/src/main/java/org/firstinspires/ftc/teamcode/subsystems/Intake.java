package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake implements Subsystem {

    private DcMotor intakeMotor;

    double intakePow = 0.0;

    public Intake(HardwareMap map){
        intakeMotor = map.get(DcMotor.class, "intake");
    }

    public void intakeSpin(){
        intakePow = 1;

    }

    public void intakeStop(){
        intakePow = 0;
    }









    @Override
    public void toInit() {

    }

    @Override
    public void update() {
        intakeMotor.setPower(intakePow);
    }
}
