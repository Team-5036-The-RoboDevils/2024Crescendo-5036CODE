package frc.robot.hardware;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Servo;

public class AmpScorerHardware implements IAmpScorerHardware {
    private Servo ServoShooter;

    public AmpScorerHardware() {
        ServoShooter = new Servo(RobotMap.ServoPort);

    }

    public double setAngle(double val) {
        ServoShooter.setAngle(val);
        return ServoShooter.getAngle();
    }

}
