package frc.robot.hardware;

public interface IShooterHardware {
    public void setFrontMotorPower(double val);

    public void setBackMotorPower(double val);

    public double getVelocityFrontEncoder();

    public double getVelocityBackEncoder();
}
