package frc.robot.hardware;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArticulatedIntakeHardware implements IArticulatedIntakeHardware {
    private CANSparkMax arm; 
    //private CANSparkMax intake;
    private TalonSRX intake;
    private RelativeEncoder encoder;


    public ArticulatedIntakeHardware() {
        arm = new CANSparkMax(RobotMap.INTAKEROLLER_M1_CAN_ID, MotorType.kBrushless);
        //intake = new CANSparkMax(RobotMap.INTAKEROLLER_M2_CAN_ID, MotorType.kBrushed);
        intake = new TalonSRX(RobotMap.INTAKEROLLER_M2_CAN_ID);
        encoder = arm.getEncoder();
        arm.enableVoltageCompensation(12);
    }

    @Override

    public void setIntakeMotorPower(double val) {
        intake.set(TalonSRXControlMode.PercentOutput, -val); 
    }

 

    @Override
    public void setArmMotorPower(double val) {
        arm.set(val); 
    }
    
    public double getPositionIntakeEncoder() {
        return encoder.getPosition();
    }

    public void resetArmEncoder() {
        encoder.setPosition(0);
    }
    
    public double getArmPower(){
        return intake.getMotorOutputPercent();
    }
}
