package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class ScorePreNote {
    public static void execute(Shooter shooter, ArticulatedIntake intake, boolean turnOffShooter, boolean turnOffIntake) {
        shooter.runOpenLoopFront(1);
        shooter.runOpenLoopBack(1);
        Timer.delay(5); // play around with this
        intake.runOpenLoopIntake(-1);
        Timer.delay(2); // play around with this
        if (turnOffShooter) {
            shooter.runOpenLoopFront(0); 
            shooter.runOpenLoopBack(0); 
        }
        if (turnOffIntake) {
            intake.runOpenLoopIntake(0); 
        }
    }
}
