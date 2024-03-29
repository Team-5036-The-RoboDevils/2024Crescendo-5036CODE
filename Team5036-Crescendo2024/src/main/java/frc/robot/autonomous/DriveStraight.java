package frc.robot.autonomous;

import frc.robot.subsystems.Drivetrain;

public class DriveStraight {

    private static boolean isInAutoTime(double startTime){
        
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
        }
        return true;
    }
    
    public static void execute(Drivetrain drivetrain, double targetDist, boolean driveBack, double forward, double startTime){

        double rotate = 0;
        if(!driveBack){
            drivetrain.resetEncoders();
            while(drivetrain.getDistTravelled() <= targetDist && isInAutoTime(startTime)){
                drivetrain.arcadeDrive(forward, rotate);
            }
            drivetrain.arcadeDrive(0, 0);
        } else if(driveBack){
            drivetrain.resetEncoders();
            while (drivetrain.getDistTravelled() >= -targetDist && isInAutoTime(startTime)) { 
                drivetrain.arcadeDrive(forward, rotate);
            }
            drivetrain.arcadeDrive(0, 0);
        }
    }
}

