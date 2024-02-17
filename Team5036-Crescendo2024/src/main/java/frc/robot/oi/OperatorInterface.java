package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface implements IOperatorInterface {
    private Joystick drivetrainController;
    private Joystick operatorController;

    public OperatorInterface() {
        drivetrainController = new Joystick(0);
        operatorController = new Joystick(1);
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
    public double getOpenLoopShooter() {
        return -operatorController.getRawAxis(1);
    }

}