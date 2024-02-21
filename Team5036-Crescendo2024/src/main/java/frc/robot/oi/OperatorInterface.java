package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface implements IOperatorInterface {
    private Joystick drivetrainController;
    private Joystick operatorController;
    private Joystick tuningJoystick;

    public OperatorInterface() {
        drivetrainController = new Joystick(0);
        operatorController = new Joystick(1);
        tuningJoystick = new Joystick(2);
    }

    @Override
    public double getDriveTrainForward() {
        return -drivetrainController.getRawAxis(1);

    }

    @Override
    public double getDriveTrainRotate() {
        return drivetrainController.getRawAxis(4);
    }

    // @Override
    // public double getOpenLoopShooter() {
    //     return -operatorController.getRawAxis(1);
    // }

    @Override
    public boolean spinUp() {
        return operatorController.getRawButton(6);
    }

    @Override
    public boolean getShotSpeedButton() {
        return operatorController.getRawAxis(3) > 0.15;
    }

    @Override
    public boolean getHpIntakeSpeedButton() {
       return operatorController.getRawAxis(2) > 0.15;
    }

    @Override
    public boolean getAmpSpeedSpeedButton() {
        return operatorController.getRawButton(2);
    }

    @Override
    public boolean getIntakeOpenLoopButton(){
        return operatorController.getRawButton(4);
    }

    @Override
    public boolean getOuttakeOpenLoopButton(){
        return operatorController.getRawButton(1);
    }

    @Override
    public boolean getArticulatedIntakeOpenLoopButton(){
        return operatorController.getRawButton(5);
    }

    @Override
    public double getArticulatedIntakeOpenLoopAxis(){
        return -operatorController.getRawAxis(1);
    }

    @Override
    public double getArticulatedIntakePIDTuningAxis(double a, double b){
        return ((tuningJoystick.getRawAxis(2) + 1) / 2) * (b - a) + a;
    }
}