package frc.robot.subsystems;

import frc.robot.hardware.IDrivetrainHardware;
import java.lang.Math;

public class Drivetrain {
    private IDrivetrainHardware hardware; // Make IDrivetrain variable

    public Drivetrain(IDrivetrainHardware hardware) {
        this.hardware = hardware; // Create Member Variable
    }

    private double capInput(double val, double min, double max) {
        if (val < min)
            return min;
        if (val > max)
            return max;
        return val;
    }

    public void arcadeDrive(double forward, double rotate) {
        double leftSidePower = capInput(forward + rotate, -1., 1.);
        double rightSidePower = capInput(forward - rotate, -1., 1.);
        hardware.setLeftSidePower(leftSidePower);
        hardware.setRightSidePower(rightSidePower);
    }

    public void resetEncoders() {
        hardware.resetEncoderPos();
    }

    private double getEncoderPos() {
        return (hardware.getLeftEncoderPos() + hardware.getRightEncoderPos()) / 2;
    }

    public double getDistTravelled() {
        // Comment from Tahmid: What is this dogwater? We're using NEOs, not NEO 550s. Also, gear ratio plays a role here.
        double currentpos = getEncoderPos();
        double singularRotation = (2 * Math.PI * 3) / 42; // 42 encoder ticks on NEO 550
        // Attempting to Calculate Distance - Will push for now
        return 1.;
    }

    public void resetGyro() {
        hardware.resetGyro();
    }

    public double getAngle() {
        return hardware.getAngle();
    }

    public double getHeading() {
        return hardware.getAngle() % 360.;
    }
}
