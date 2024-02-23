package frc.robot.hardware;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Servo;

public class AmpScorerHardware implements IAmpScorerHardware {
    private Servo ServoShooter;

    public AmpScorerHardware() {
        ServoShooter = new Servo(RobotMap.AMP_SERVO_PWM);

    }

    public double setAngle(double val) {
        ServoShooter.setAngle(val);
        return ServoShooter.getAngle();
    }
}
