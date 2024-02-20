package frc.robot.oi;

public interface IOperatorInterface {
    public double getDriveTrainForward();

    public double getDriveTrainRotate();

   // public double getOpenLoopShooter();

    public boolean spinUp(); // temporary for testing

    public boolean getShotSpeedButton();

    public boolean getHpIntakeSpeedButton();

    public boolean getAmpSpeedSpeedButton();

    public boolean getArticulatedIntakeOpenLoopButton();

    public boolean getOuttakeOpenLoopButton();

    public boolean getIntakeOpenLoopButton();

    public double getArticulatedIntakeOpenLoopAxis();
}
