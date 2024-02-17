
//Lets have 2 different classes
/*Start with ShooterHardware
Similar to drivetrain hardware
1 method that sets power of one motor
1 method that sets power of second motor
1 method that gets encoder velocity from one motor
1 method that gets encoder velocity from the other motor
Then we can have a Shooter class that takes a ShooterHardware object and perform 
any necessary logic like going a certain speed */
//First 2 methods just print something
//2nd 2 methods return 0//
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI;

public class shooterHardware implements IShooterHardware {
    private CANSparkMax shooter1;
    private CANSparkMax shooter2;

    public shooterHardware() {
        shooter1 = new CANSparkMax(2, MotorType.kBrushless);
        shooter2 = new CANSparkMax(3, MotorType.kBrushless);
    }

    // CAN ID values will be stored in RobotMap.java but for now, just random till
    // we can go to ACE

    @Override
    public void setFrontMotorPower(double val) {
        shooter1.set(val); // can be +/- need to check in person
        System.out.println("What is the motor power value ?  It's" + val);
    }

    @Override
    public void setBackMotorPower(double val) {
        shooter2.set(val); // can be +/- need to check in person
        System.out.println(val);
    }

    @Override
    public double getVelocityFrontEncoder() {
        return (0.0);
    }

    @Override
    public double getVelocityBackEncoder() {
        return (0.0);
    }
}
