package frc.robot.subsystems;

import frc.robot.hardware.IAmpScorerHardware;

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
        ampScorerHardware.setAngle(170.0);
    }

    public void flipOut() {
        ampScorerHardware.setAngle(10.0);
    }
}
