package frc.robot.autonomous;

public class PIDController {

  public final double kP;
  public final double kI;
  public final double kD;
  public double target, currentError, lastError, outputMax, outputMin, current, errorSum;
  public double integralRange = 10;
  
  public PIDController(double currentSensorReading, double target, double outputMin, double outputMax, double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.target = target;
    this.outputMax = outputMax;
    this.outputMin = outputMin;
    this.lastError = 0;
    this.currentError = target - currentSensorReading;
  }
  public double getOutput() {
    return capOutput(currentError*kP + ((currentError-lastError)*kD) + (errorSum * kI));
  }
  public double capOutput(double val) {
    // caps the output value by using if else loops
    return val >= outputMax ? outputMax : val<outputMin ? outputMin : val;
  }
  public void updateError(double currentSensorReading) {
    // updating the error
    lastError = currentError;
    currentError = target - currentSensorReading;
    errorSum += Math.abs(currentError) < integralRange ? currentError : 0;
  }
}
