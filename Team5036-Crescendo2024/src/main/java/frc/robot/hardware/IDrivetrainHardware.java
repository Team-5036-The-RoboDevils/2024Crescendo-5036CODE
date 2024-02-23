package frc.robot.hardware;

public interface IDrivetrainHardware {
    public void setLeftSidePower(double val);

    public void setRightSidePower(double val);

    public double getLeftEncoderPos();

    public double getRightEncoderPos();

    public void resetEncoderPos();

    public void resetGyro();

    public double getAngle();
}
