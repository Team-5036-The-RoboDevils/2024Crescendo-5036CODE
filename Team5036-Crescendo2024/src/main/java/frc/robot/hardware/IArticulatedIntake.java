package frc.robot.hardware;

public interface IArticulatedIntake {
    public void setIntakeMotorPower(double val); 

    public void setArmMotorPower(double val); 
        
    public double getPositionIntakeEncoder(); 

}
