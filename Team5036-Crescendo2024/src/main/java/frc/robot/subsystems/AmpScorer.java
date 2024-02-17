package frc.robot.subsystems;

import frc.robot.hardware.AmpScorerHardware;
import frc.robot.hardware.IAmpScorerHardware;
import frc.robot.hardware.IDrivetrainHardware;

import java.lang.Math;

public class AmpScorer {
    private IAmpScorerHardware ampScorerHardware;

    public AmpScorer(IAmpScorerHardware ampScorerHardware) {
        this.ampScorerHardware = ampScorerHardware;
    }

    public double capInput(double val) {
        double max = 180;
        double min = 0;
        if (val > max) {
            return max;
        }
        if (val < min) {
            return min;
        }
        return val;
    }

    public void flipIn() {
        this.ampScorerHardware.setAngle(170.0);
    }

    public void flipOut() {
        this.ampScorerHardware.setAngle(10.0);
    }

}
