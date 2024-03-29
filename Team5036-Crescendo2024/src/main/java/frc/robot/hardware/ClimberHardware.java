package frc.robot.hardware;


import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
public class ClimberHardware implements IClimberHardware {
    private CANSparkMax Climber;
    private RelativeEncoder ClimberEncoder;

    public ClimberHardware(){
        Climber = new CANSparkMax(RobotMap.CLIMBER_CAN_ID, MotorType.kBrushless);
       // RelativeEncoder = new Climber.getEncoder();
    }

    public void setClimberMotorPower(double val){
        Climber.set(val);
    }

    public void resetClimberEncoder(){
        //Climber.();
    }
}
