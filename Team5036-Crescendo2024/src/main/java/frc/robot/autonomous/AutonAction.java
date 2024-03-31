package frc.robot.autonomous;

public class AutonAction {
    protected static boolean isInAutoTime(double startTime){
        
        double currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > 15000){
            return false;
        }
        return true;
    }
}
