package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name="Annay", group="TeleOp")
public class MainTeleOp extends OpMode {

    public Drive drive;
    Gamepad currentGamepad1;
    Gamepad previousGamepad1;

    private Intake intake;


    @Override
    public void init() {
        drive = new Drive(hardwareMap);
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        this.intake = new Intake(hardwareMap);


    }

    @Override
    public void loop() {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);

        if ( currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
            intake.intakeSpin();
        }

        if ( !currentGamepad1.right_bumper && previousGamepad1.right_bumper){
            intake.intakeStop();
        }


        drive.drive(-currentGamepad1.left_stick_y, currentGamepad1.left_stick_x, currentGamepad1.right_stick_x);

        drive.update();
        intake.update();
    }

}
