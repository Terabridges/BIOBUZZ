package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive implements Subsystem{

    // declaration
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    //variables
    double leftFrontPow = 0.0;
    double rightFrontPow = 0.0;
    double leftBackPow = 0.0;
    double rightBackPow = 0.0;

//Consturcotur
    public Drive(HardwareMap map){
        backLeft = map.get(DcMotor.class, "BackLeft");
        backRight = map.get(DcMotor.class, "BackRight");
        frontLeft = map.get(DcMotor.class, "FrontLeft");
        frontRight = map.get(DcMotor.class, "FrontRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    //Functions
    public void drive(double forward, double right, double rotate) {
        // This calculates the power needed for each wheel based on the amount of forward,
        // strafe right, and rotate
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        double maxPower = 1.0;

        // This is needed to make sure we don't pass > 1.0 to any wheel
        // It allows us to keep all of the motors in proportion to what they should
        // be and not get clipped
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));

        // We multiply by maxSpeed so that it can be set lower for outreaches
        // When a young child is driving the robot, we may not want to allow full
        // speed.
        frontLeftPower /= maxPower;
        frontRightPower /= maxPower;
        backLeftPower /= maxPower;
        backRightPower /= maxPower;

        setDrivePowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

    public void setDrivePowers(double lf, double rf, double lb, double rb){
        leftFrontPow = lf;
        rightFrontPow = rf;
        leftBackPow = lb;
        rightBackPow = rb;
    }

    @Override
    public void toInit(){

    }

    @Override
    public void update(){

        frontLeft.setPower(leftFrontPow);
        frontRight.setPower(rightFrontPow);
        backLeft.setPower(leftBackPow);
        backRight.setPower(rightBackPow);
    }
}
