package frc.robot.hardware;

public interface IArticulatedIntakeHardware {
    public void setIntakeMotorPower(double val); 

    public void setArmMotorPower(double val); 
        
    public double getPositionIntakeEncoder();

    public void resetArmEncoder(); 
}
