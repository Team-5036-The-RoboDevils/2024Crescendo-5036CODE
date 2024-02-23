package frc.robot.subsystems;

import frc.robot.hardware.IShooterHardware;

public class Shooter {
    private IShooterHardware hardware;
    private double kP;
    private final double FEED_FORWARD_MULTIPLIER;

    public Shooter(IShooterHardware hardware) {
        this.hardware = hardware;

        // Adjust these constants as necessary
        kP = 0.00015;
        FEED_FORWARD_MULTIPLIER = 0.00017;
    }

    public void runOpenLoopFront(double val) {
        hardware.setFrontMotorPower(val);
    }

    public void runOpenLoopBack(double val) {
        hardware.setBackMotorPower(val);
    }

    private double getProportionalControllerOutput(double currentRPM, double desiredRPM, boolean directionOutward) {
        double error = Math.abs(desiredRPM) - Math.abs(currentRPM); 
        double output = (error * kP) + (desiredRPM * FEED_FORWARD_MULTIPLIER);
        
        if (!directionOutward) {
            output *= -1;
        }

        if (directionOutward) {
            if (output <= 0) {
                output = 0; 
            } else if (output >= 1) {
                output = 1; 
            }
        } else {
            if (output <= -1) {
                output = -1; 
            } else if (output >= 0) {
                output = 0; 
            }
        }

        return output; 
    }

    public void setFrontMotorRunRpm(double desiredRPM, boolean directionOutward) {
        double currentRPM = hardware.getVelocityFrontEncoder();
        double output = getProportionalControllerOutput(currentRPM, desiredRPM, directionOutward);
        hardware.setFrontMotorPower(output);
    }

    public void setBackMotorRunRpm(double desiredRPM, boolean directionOutward) {
        double currentRPM = hardware.getVelocityBackEncoder();
        double output = getProportionalControllerOutput(currentRPM, desiredRPM, directionOutward); 
        hardware.setBackMotorPower(output);
    }
}