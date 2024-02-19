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

    @Override
    public boolean getShooterClosedLoopTest(int index) {
        return operatorController.getRawButton(index);
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
}