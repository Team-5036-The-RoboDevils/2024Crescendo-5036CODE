package frc.robot.subsystems;
import frc.robot.hardware.ArticulatedIntakeHardware;
import frc.robot.hardware.IArticulatedIntakeHardware;

public class ArticulatedIntake {

    private IArticulatedIntakeHardware intake;

    private final double ENCODER_POS_AT_ZERO = -321.000702;
    private final double ENCODER_POS_AT_NINETY = -185.999817;
    private final double EXTREME_VAL_MIN = 16;
    private final double EXTREME_VAL_MAX = 210;
    private final double ACCEPTABLE_RANGE = 2;
    private final double kFGravity = 0;
    private final double kP = 0;

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

    public void controllerClosedLoopArticulation(double desiredAngle, double tempkP, double tempkFGravity) {
        double currentAngle = getCurrentAngle();
        double error = desiredAngle - currentAngle;
        //double feedForwardVal = Math.cos(Math.toRadians(currentAngle)) * kFGravity;
        double feedForwardVal = Math.cos(Math.toRadians(currentAngle)) * tempkFGravity;

        if (Math.abs(error) <= ACCEPTABLE_RANGE || true) {
            if (currentAngle < EXTREME_VAL_MIN || currentAngle > EXTREME_VAL_MAX) {
                // We're at the extremes so, no need for power.
                intake.setArmMotorPower(0);
            } else {
                // Just pass feed forward value.
                System.out.println("DEBUGGING Feed forward val: " + feedForwardVal);
                intake.setArmMotorPower(feedForwardVal);
            }
        } else {
            // Run proportional controller + feed forward value
            intake.setArmMotorPower((error * kP) + feedForwardVal);
        }

        /* 
        double desiredAngle = 35;
        double currentEncoderPos = intake.getPositionIntakeEncoder();
        double angleValue = encoderPosToAngle(currentEncoderPos);
        double multiplierConstant = 0.14;
        double radianValue = Math.toRadians(angleValue);
        double feedForwardVal = Math.cos(radianValue) * tempParam;
        double error = desiredAngle - angleValue;

        if(angleValue > EXTREME_VAL_MAX && Math.abs(error) <= acceptableRange){
            intake.setArmMotorPower(0);
                if(angleValue < EXTREME_VAL_MIN && Math.abs(error) <= acceptableRange){
                    intake.setArmMotorPower(0);
                        }
                        if(!(angleValue > EXTREME_VAL_MAX) && !(angleValue < EXTREME_VAL_MIN) && Math.abs(error) <= acceptableRange){
                            intake.setArmMotorPower(feedForwardVal);
                                }
                                    }
        
        if(angleValue == desiredAngle){
            
        }
        */
    }

    public void controllerOpenLoopArticulation(double val){
        intake.setArmMotorPower(val);
        //System.out.println("Value of articulation motor" + val);
    }

    public void runOpenLoopIntake(double val){
        intake.setIntakeMotorPower(val);
        //System.out.println("Value of Intake motor" + val);
    }

    public void runOpenLoopOuttake(double val){
        intake.setIntakeMotorPower(val);
        //System.out.println("Value of Intake motor" + val);
    }

    public double getEncoderPos(){
        //System.out.println("The current position is " + intake.getPositionIntakeEncoder());
        return intake.getPositionIntakeEncoder();
    }
    
    private double encoderPosToAngle(double currentEncoderPos) {
        double m = (90 / (ENCODER_POS_AT_NINETY - ENCODER_POS_AT_ZERO));
        double b = -(ENCODER_POS_AT_ZERO * m);

        double AngleConversion = (m * currentEncoderPos) + b;
        //System.out.println("The converted angle from the position " + currentEncoderPos + " is equal to " + AngleConversion);
        return AngleConversion;
    }

    public double getCurrentAngle(){
        double currentEncoderPos = intake.getPositionIntakeEncoder();
        return encoderPosToAngle(currentEncoderPos);
    }

}
    

