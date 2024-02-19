package frc.robot.oi;

public interface IOperatorInterface {
    public double getDriveTrainForward();

    public double getDriveTrainRotate();

    public double getOpenLoopShooter();

    public boolean getShooterClosedLoopTest(int index); // temporary for testing

    public boolean getShotSpeedButton();

    public boolean getHpIntakeSpeedButton();

    public boolean getAmpSpeedSpeedButton();
}
