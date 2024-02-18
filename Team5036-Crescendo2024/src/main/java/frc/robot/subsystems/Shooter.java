package frc.robot.subsystems;

import frc.robot.hardware.IShooterHardware;

/*public class Shooter {

    public static void main(String[] args) {

        shooterHardware shooter = new shooterHardware();
        System.out.println("CURRENT ROBOT STATE:");
        shooter.setFrontMotorPower(20);
        shooter.setBackMotorPower(30);
        System.out.println(shooter.getVelocityFrontEncoder());
        System.out.println(shooter.getVelocityBackEncoder());
    }

}*/
public class Shooter {
    private IShooterHardware hardware;
    private final double kP; 

    public Shooter(IShooterHardware hardware) {
        this.hardware = hardware;
        kP = 0; 
    }

    public void runOpenLoopFront(double val) {
        hardware.setFrontMotorPower(val);
    }

    public void runOpenLoopBack(double val) {
        hardware.setBackMotorPower(val);
    }

    private double getProportionalControllerOutput(double currentRPM, double desiredRPM) {
        double error = currentRPM - desiredRPM; 
        double output = error * kP; 
        if (output <= 0) {
            output = 0; 
        } else if (output >= 1) {
            output = 1; 
        }
        return output; 
    }

    public void setFrontMotorRunRpm(double desiredRPM) {
        double currentRPM = hardware.getVelocityFrontEncoder();
        double output = getProportionalControllerOutput(currentRPM, desiredRPM);
        hardware.setFrontMotorPower(output);
    }

    public void setBackMotorRunRpm(double desiredRPM) {
        double currentRPM = hardware.getVelocityBackEncoder();
        double output = getProportionalControllerOutput(currentRPM, desiredRPM); 
        hardware.setBackMotorPower(output);
    }
}