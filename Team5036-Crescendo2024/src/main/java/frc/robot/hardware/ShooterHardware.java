package frc.robot.hardware;

import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterHardware implements IShooterHardware {
    private CANSparkMax shooter1;
    private CANSparkMax shooter2;
    private RelativeEncoder encoder1;
    private RelativeEncoder encoder2;  


    public ShooterHardware() {
        shooter1 = new CANSparkMax(RobotMap.SHOOTER_S1_CAN_ID, MotorType.kBrushless);
        shooter2 = new CANSparkMax(RobotMap.SHOOTER_S2_CAN_ID, MotorType.kBrushless);
        encoder1 = shooter1.getEncoder(); 
        encoder2 = shooter2.getEncoder(); 
    }

    // CAN ID values will be stored in RobotMap.java but for now, just random till
    // we can go to ACE

    @Override
    public void setFrontMotorPower(double val) {
        shooter1.set(val); // can be +/- need to check in person
        //System.out.println("DEBUG - FRONT MOTOR POWER " + val);
    }

    @Override
    public void setBackMotorPower(double val) {
        shooter2.set(val); // can be +/- need to check in person
        //System.out.println("DEBUG - BACK MOTOR POWER " + val);
    }

    @Override
    public double getVelocityFrontEncoder() {
        return encoder1.getVelocity();
    }

    @Override
    public double getVelocityBackEncoder() {
        return encoder2.getVelocity();
    }
}
