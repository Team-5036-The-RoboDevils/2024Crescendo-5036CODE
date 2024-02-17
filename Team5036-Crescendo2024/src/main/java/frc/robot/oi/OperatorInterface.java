package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface implements IOperatorInterface {
    private Joystick drivetrainController;

    public OperatorInterface() {
        drivetrainController = new Joystick(0); 
    }

    @Override 
    public double getDriveTrainForward() {
        return -drivetrainController.getRawAxis(1);

    }

    @Override 
    public double getDriveTrainRotate() {
        return drivetrainController.getRawAxis(4); 
    }

}