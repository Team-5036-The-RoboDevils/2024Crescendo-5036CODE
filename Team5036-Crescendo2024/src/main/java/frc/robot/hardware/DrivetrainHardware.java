package frc.robot.hardware;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DrivetrainHardware implements IDrivetrainHardware {
    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax right1;
    private CANSparkMax right2;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;
    private AHRS gyro;

    public DrivetrainHardware() {
        left1 = new CANSparkMax(RobotMap.DRIVE_L1_CAN_ID, MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.DRIVE_L2_CAN_ID, MotorType.kBrushless);
        right1 = new CANSparkMax(RobotMap.DRIVE_R1_CAN_ID, MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.DRIVE_R2_CAN_ID, MotorType.kBrushless);
        leftEncoder = left2.getEncoder();
        rightEncoder = right2.getEncoder();
        gyro = new AHRS(SPI.Port.kMXP);
        while (gyro.isCalibrating()) {};
        resetGyro();
    }

    @Override
    public void setLeftSidePower(double val) {
        left1.set(val);
        left2.set(val);
    }

    @Override
    public void setRightSidePower(double val) {
        right1.set(-val);
        right2.set(val);
    }

    @Override
    public double getLeftEncoderPos() {
        return leftEncoder.getPosition();
    }

    @Override
    public double getRightEncoderPos() {
        return rightEncoder.getPosition();
    }

    @Override
    public void resetEncoderPos() {
        REVLibError res1 = leftEncoder.setPosition(0.);
        REVLibError res2 = rightEncoder.setPosition(0.);
        System.out.println("RESET LEFT RES: " + res1.kOk);
        System.out.println("RESET RIGHT RES: " + res2.kOk);
    }

    @Override
    public void resetGyro() {
        //gyro.reset();
        //gyro.resetDisplacement();
        //gyro.setAngleAdjustment(-getAngle());
        gyro.zeroYaw();
    }

    @Override
    public double getAngle() {
        return gyro.getAngle();
        //return gyro.getYaw();
    }
}