package frc.robot.oi;

public interface IOperatorInterface {
    public double getDriveTrainForward();

    public double getDriveTrainRotate();

    public boolean spinUp(); // temporary for testing

    public boolean getShotSpeedButton();

    public boolean getHpIntakeSpeedButton();

    public boolean getAmpSpeedSpeedButton();

    public boolean getArticulatedIntakeOpenLoopButton();

    public boolean getOuttakeOpenLoopButton();

    public boolean getIntakeOpenLoopButton();

    public double getArticulatedIntakeOpenLoopAxis();

    public double getArticulatedIntakePIDTuningAxis(double a, double b);

    public boolean deployArticulatedIntake();

    public double getIntakeManualSpeed();

    public boolean getDebugButton();

    public boolean getSlowMode();
}
