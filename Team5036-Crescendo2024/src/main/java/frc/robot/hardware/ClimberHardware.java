package frc.robot.hardware;


import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class ClimberHardware implements IClimberHardware {
    private VictorSPX climber;
    //private RelativeEncoder ClimberEncoder;

    public ClimberHardware(){
        climber = new VictorSPX(RobotMap.CLIMBER_CAN_ID);
       // RelativeEncoder = new Climber.getEncoder();
    }

    public void setClimberMotorPower(double val){
        climber.set(VictorSPXControlMode.PercentOutput, val);
    }

    /*public void resetClimberEncoder(){
        //Climber.();
    }*/
}
