package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.ArticulatedIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class PIDTurn extends AutonAction {
    private static final double EPSILON_RANGE = 5;
    private static final int IN_RANGE_COUNT_REQ = 10;
    
    public static void execute(double autonStartTime, Drivetrain drivetrain, Shooter shooter, ArticulatedIntake intake, double desiredAngle, double maxPower, double shooterSpeed, double intakeAngle) {
        int inRangeCount = 0;
        
        drivetrain.resetGyro(); 
        Timer.delay(0.1);

        PIDController turnPidController = new PIDController(drivetrain.getAngle(), desiredAngle, -maxPower, maxPower, 0.015, 0, 0.05);

        while (inRangeCount < IN_RANGE_COUNT_REQ && isInAutoTime(autonStartTime)) {
            turnPidController.updateError(drivetrain.getAngle());
            //System.out.println("PID Output: " + turnPidController.getOutput());
            drivetrain.arcadeDrive(0, turnPidController.getOutput());

            shooter.runOpenLoopFront(shooterSpeed);
            shooter.runOpenLoopBack(shooterSpeed);

            intake.controllerClosedLoopArticulation(intakeAngle);

            if (Math.abs(drivetrain.getAngle() - desiredAngle) <= EPSILON_RANGE) {
                inRangeCount += 1;
            }

            Timer.delay(0.02);
        }

        drivetrain.arcadeDrive(0, 0);
    }
}
