package frc.robot.autonomous;

import frc.robot.subsystems.ArticulatedIntake;
import frc.robot.subsystems.Shooter;


public class MoveArm extends AutonAction {

    public static void execute(double autonStartTime, ArticulatedIntake intake, Shooter shooter, double desiredAngle, double shooterSpeed, double intakeSpeed) {
        int inRangeCount = 0;
        final int IN_RANGE_COUNT_REQ = 10;
        final double ACCEPTABLE_RANGE = 15;
        
        while (inRangeCount < IN_RANGE_COUNT_REQ && isInAutoTime(autonStartTime) ) {
            shooter.runOpenLoopFront(shooterSpeed);
            shooter.runOpenLoopBack(shooterSpeed);

            intake.runOpenLoopIntake(intakeSpeed);

            intake.controllerClosedLoopArticulation(desiredAngle);

            if (Math.abs(intake.getCurrentAngle() - desiredAngle) < ACCEPTABLE_RANGE) {
                inRangeCount += 1;
            }
        }
    }
}
