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

    public double getEncoderPos() {
        return (hardware.getLeftEncoderPos() + hardware.getRightEncoderPos()) / 2; // getting the average of the two encoders 
    }

    public double getDistTravelled() {
        // Comment from Tahmid: What is this dogwater? We're using NEOs, not NEO 550s. Also, gear ratio plays a role here.
        double currentPos = getEncoderPos();
        return convertEncoderPosToCm(currentPos);
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

    private double convertEncoderPosToCm(double encoderPos){
        // 375.2 encoder ticks for 100 cm (1 m) measured with middle wheel. 
        double converterConst = -3.752;
        double convertedDist = encoderPos / converterConst;
        return convertedDist;

    }
}
