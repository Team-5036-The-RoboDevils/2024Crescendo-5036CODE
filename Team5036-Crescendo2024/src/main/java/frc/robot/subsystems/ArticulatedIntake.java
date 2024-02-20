package frc.robot.subsystems;
import frc.robot.hardware.ArticulatedIntakeHardware;
import frc.robot.hardware.IArticulatedIntakeHardware;

public class ArticulatedIntake {

    private IArticulatedIntakeHardware intake;

    public double ENCODER_POS_AT_ZERO = -321.000702;
    public double ENCODER_POS_AT_NINETY = -185.999817;
    

    public ArticulatedIntake(IArticulatedIntakeHardware intake){
        this.intake = intake;
    }


    public double capArticulationInput(double output, double max, double min){
        if(output >= max){
            output = max;
        }
        else if(output <= min){
            output = min;
        }
        return output;
    }
    public void controllerClosedLoopArticulation(double tempParam){
        double currentEncoderPos = intake.getPositionIntakeEncoder();
        double angleValue = encoderPosToAngle(currentEncoderPos);
        double multiplierConstant = 0.14;
        double radianValue = Math.toRadians(angleValue);
        double feedForwardVal = Math.cos(radianValue) * tempParam;


;
    }

    public void controllerOpenLoopArticulation(double val){
        intake.setArmMotorPower(val);
        System.out.println("Value of articulation motor" + val);
    }

    public void runOpenLoopIntake(double val){
        intake.setIntakeMotorPower(val);
        System.out.println("Value of Intake motor" + val);
    }

    public void runOpenLoopOuttake(double val){
        intake.setIntakeMotorPower(val);
        System.out.println("Value of Intake motor" + val);
    }

    public double getEncoderPos(){
        System.out.println("The current position is " + intake.getPositionIntakeEncoder());
        return intake.getPositionIntakeEncoder();
    }
    
    private double encoderPosToAngle(double currentEncoderPos) {
        double m = (90 / (ENCODER_POS_AT_NINETY - ENCODER_POS_AT_ZERO));
        double b = -(ENCODER_POS_AT_ZERO * m);

        double AngleConversion = (m * currentEncoderPos) + b;
        System.out.println("The converted angle from the position " + currentEncoderPos + " is equal to " + AngleConversion);
        return AngleConversion;
    }

    public double getCurrentAngle(){
        double currentEncoderPos = intake.getPositionIntakeEncoder();
        return encoderPosToAngle(currentEncoderPos);
    }

}
    

