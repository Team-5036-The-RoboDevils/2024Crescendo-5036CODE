package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface implements IOperatorInterface {
    private Joystick drivetrainController;
    private Joystick operatorController;

    private Joystick tuningController;

    public OperatorInterface() {
        drivetrainController = new Joystick(0);
        operatorController = new Joystick(1);
        
        tuningController = new Joystick(2);
    }

    @Override
    public double getDriveTrainForward() {
        return -drivetrainController.getRawAxis(1);

    }

    @Override
    public double getDriveTrainRotate() {
        return drivetrainController.getRawAxis(4);
    }

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
        return operatorController.getRawButton(4);
    }

    @Override
    public boolean getAmpSpeedSpeedButton() {
        return operatorController.getRawButton(2);
    }

    @Override
    public boolean getIntakeOpenLoopButton(){
         return operatorController.getRawButton(4);
        // should just get rid of th
    }

    @Override
    public boolean getOuttakeOpenLoopButton(){
        return operatorController.getRawButton(1);
        // should just get rid of this
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
    public double getArticulatedIntakePIDTuningAxis(){
        double a = 0.02;
        double b = 0.05;
        return ((-tuningController.getRawAxis(1) + 1) / 2) * (b - a) + a;
        // Only reimplement if we need to tune something again.
        //return 0;
    }

    @Override 
    public boolean deployArticulatedIntake() {
        return operatorController.getRawAxis(2) > 0.15;
    }

    @Override
    public double getIntakeManualSpeed() {
        return operatorController.getRawAxis(5);
    }

    @Override
    public boolean getDebugButton() {
        return tuningController.getRawButton(3);
    }

    @Override
    public boolean getSlowMode() {
        return drivetrainController.getRawButton(6);
    }

    @Override
    public boolean getClimbButton() {
        return drivetrainController.getRawButton(5);
    }

    @Override
    public boolean getClimbUnwindButton() {
        return drivetrainController.getRawAxis(2) > 0.15;
    }
}