package frc.robot.subsystems;

import frc.robot.hardware.IClimberHardware;

public class Climber {
    private IClimberHardware climberHardware;

    public Climber(IClimberHardware climberHardware) {
        this.climberHardware = climberHardware;
    }

    public void retract() {
        climberHardware.setClimberMotorPower(1.);
    }

    public void unwind() {
        climberHardware.setClimberMotorPower(-1.);
    }

    public void stop() {
        climberHardware.setClimberMotorPower(0);
    }
}
