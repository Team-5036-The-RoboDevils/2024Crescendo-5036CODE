package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.ArticulatedIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class PIDDriveStraight extends AutonAction {
    private static final double EPSILON_RANGE = 5;
    private static final int IN_RANGE_COUNT_REQ = 10;
    
    public static void execute(double autonStartTime, Drivetrain drivetrain, Shooter shooter, ArticulatedIntake intake, double desiredDist, double maxPower, double shooterSpeed, double intakeAngle, double intakeSpeed) {
        int inRangeCount = 0;
        
        drivetrain.resetEncoders();
        drivetrain.resetGyro(); 
        Timer.delay(0.2);

        PIDController driveStraightController = new PIDController(drivetrain.getDistTravelled(), desiredDist, -maxPower, maxPower, 0.07, 0, 0.175);
        PIDController turnPidController = new PIDController(drivetrain.getAngle(), 0, -0.3, 0.3, 0.015, 0, 0.05);
        //PIDController turnPidController = new PIDController(drivetrain.getAngle(), 0, -0.1, 0.1, 0.015, 0, 0.0);

        while (inRangeCount < IN_RANGE_COUNT_REQ && isInAutoTime(autonStartTime)) {
            driveStraightController.updateError(drivetrain.getDistTravelled());
            turnPidController.updateError(drivetrain.getAngle());
            
            //System.out.println("Target: " + desiredDist + ", Current sensor reading: " + drivetrain.getDistTravelled());
            //System.out.println("PID Drive straight Output: " + driveStraightController.getOutput());
            drivetrain.arcadeDrive(-driveStraightController.getOutput(), turnPidController.getOutput());

            shooter.runOpenLoopFront(shooterSpeed);
            shooter.runOpenLoopBack(shooterSpeed);

            intake.controllerClosedLoopArticulation(intakeAngle);
            intake.runOpenLoopIntake(intakeSpeed);

            if (Math.abs(drivetrain.getDistTravelled() - desiredDist) <= EPSILON_RANGE) {
                inRangeCount += 1;
            }

            Timer.delay(0.02);
        }

        drivetrain.arcadeDrive(0, 0);
    }
}
