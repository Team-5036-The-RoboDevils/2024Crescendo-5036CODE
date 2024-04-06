package frc.robot.subsystems;

import frc.robot.hardware.IArticulatedIntakeHardware;

public class ArticulatedIntake {

    private IArticulatedIntakeHardware intake;

    private final double ENCODER_POS_AT_ZERO = -300;
    private final double ENCODER_POS_AT_NINETY = -113.999817;
    private final double EXTREME_VAL_MIN = 10;
    private final double EXTREME_VAL_MAX = 133;
    private final double ACCEPTABLE_RANGE = 15;
    private final double kFGravity = 0.082188;
    private final double kP = 0.0019;
    //private final double kP = 0.0038;

    public ArticulatedIntake(IArticulatedIntakeHardware intake){
        this.intake = intake;
    }

    private double capArticulationInput(double output, double max, double min){
        if(output >= max){
            output = max;
        }
        else if(output <= min){
            output = min;
        }
        return output;
    }

    public void controllerClosedLoopArticulation(double desiredAngle) {
        double currentAngle = getCurrentAngle();
        double error = desiredAngle - currentAngle;
        double feedForwardVal = Math.cos(Math.toRadians(currentAngle)) * kFGravity;

        if (Math.abs(error) <= ACCEPTABLE_RANGE) {
            /*if (currentAngle < EXTREME_VAL_MIN || currentAngle > EXTREME_VAL_MAX) {
                // We're at the extremes so, no need for power.
                intake.setArmMotorPower(0);
            } else {
                // Just pass feed forward value.
                intake.setArmMotorPower(feedForwardVal);
            }*/
            intake.setArmMotorPower(feedForwardVal);
        } else {
            // Run proportional controller + feed forward value
            double output = (error * kP) + feedForwardVal;
            output = capArticulationInput(output, 0.8, -0.8);
            intake.setArmMotorPower(output);
        }
    }

    public void controllerOpenLoopArticulation(double val){
        intake.setArmMotorPower(val);
    }

    public void runOpenLoopIntake(double val){
        intake.setIntakeMotorPower(val);
    }

    public double getEncoderPos(){
        return intake.getPositionIntakeEncoder();
    }
    
    private double encoderPosToAngle(double currentEncoderPos) {
        double m = (90 / (ENCODER_POS_AT_NINETY - ENCODER_POS_AT_ZERO));
        double b = -(ENCODER_POS_AT_ZERO * m);
        double angleConversion = (m * currentEncoderPos) + b;
        return angleConversion;
    }

    public double getCurrentAngle(){
        double currentEncoderPos = intake.getPositionIntakeEncoder();
        return encoderPosToAngle(currentEncoderPos);
    }
}

    

