package frc.robot.hardware;

import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArticulatedIntakeHardware implements IArticulatedIntakeHardware {
    private CANSparkMax arm; 
    private CANSparkMax intake;
    private RelativeEncoder encoder;

    public ArticulatedIntakeHardware() {
        arm = new CANSparkMax(RobotMap.INTAKEROLLER_M1_CAN_ID, MotorType.kBrushless);
        intake = new CANSparkMax(RobotMap.INTAKEROLLER_M2_CAN_ID, MotorType.kBrushless);
        encoder = arm.getEncoder();
    }

    @Override

    public void setIntakeMotorPower(double val) {
        System.out.println("Value Passed in to INTAKE is = " + val);
        intake.set(val); 
    }

    @Override
    public void setArmMotorPower(double val) {
        System.out.println("Value Passed in to ARM MOTOR is = " + val);
        arm.set(val); 
    }
    
    public double getPositionIntakeEncoder() {
        return encoder.getPosition();
    }

    public void resetArmEncoder() {
        encoder.setPosition(0);
    }
}
