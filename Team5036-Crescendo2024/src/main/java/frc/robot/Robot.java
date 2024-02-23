// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.hardware.*;
import frc.robot.oi.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  IShooterHardware shooterHardware;

  OperatorInterface oi;
  Drivetrain drivetrain;
  Shooter shooter;
  ArticulatedIntake intake;

  IArticulatedIntakeHardware intakeHardware;
  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    shooterHardware = new ShooterHardware();
    intakeHardware = new ArticulatedIntakeHardware();

    oi = new OperatorInterface();
    drivetrain = new Drivetrain(new DrivetrainHardware());
    shooter = new Shooter(shooterHardware);
    intake = new ArticulatedIntake(intakeHardware);

    intakeHardware.resetArmEncoder();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Intake Encoder Pos:", intakeHardware.getPositionIntakeEncoder());
    SmartDashboard.putNumber("Drivetrain Controller Forward", oi.getDriveTrainForward());
    SmartDashboard.putNumber("Drivetrain Controller Rotate", oi.getDriveTrainRotate());
    SmartDashboard.putNumber("Operator Controller Intake Open Loop VALUE OF ANGLE", intake.getCurrentAngle());
    SmartDashboard.putNumber("Front Shooter Vel: ", shooterHardware.getVelocityFrontEncoder());
    SmartDashboard.putNumber("Back Shooter Vel: ", shooterHardware.getVelocityBackEncoder());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different
   * autonomous modes using the dashboard. The sendable chooser code works with
   * the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the
   * chooser code and
   * uncomment the getString line to get the auto name from the text box below the
   * Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure
   * below with additional strings. If using the SendableChooser make sure to add
   * them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // TODO: Implement autonomous modes.
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // TODO: Implement autonomous modes.
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = oi.getDriveTrainForward();
    double rotate = oi.getDriveTrainRotate();

    // Drive
    forward = forward * Math.abs(forward);
    rotate = rotate * rotate * rotate * 0.8;
    drivetrain.arcadeDrive(forward, rotate);

    // Articulation of intake
    if (oi.deployArticulatedIntake()) {
      intake.controllerClosedLoopArticulation(-5);
    } else if (oi.getArticulatedIntakeOpenLoopButton()) {
      intake.controllerOpenLoopArticulation(oi.getArticulatedIntakeOpenLoopAxis() * 0.2);
    } else {
      intake.controllerClosedLoopArticulation(135);
    }

    // Front shooter motor
    if (oi.spinUp() || oi.getShotSpeedButton()) {
      shooter.runOpenLoopFront(1.);
    } else if (oi.getHpIntakeSpeedButton()) {
      shooter.setFrontMotorRunRpm(2500, false);
    } else if (oi.getAmpSpeedSpeedButton()) {
      shooter.setFrontMotorRunRpm(500, true);
    } else {
      shooter.runOpenLoopFront(0);
    }

    // Back shooter motor
    if (oi.getShotSpeedButton()) {
      shooter.runOpenLoopBack(1.);
    } else if (oi.getHpIntakeSpeedButton()) {
      shooter.setBackMotorRunRpm(750, false);
    } else if (oi.getAmpSpeedSpeedButton()) {
      shooter.setBackMotorRunRpm(550, true);
    } else {
      shooter.runOpenLoopBack(0);
    }

    // Intake motor
    if (oi.deployArticulatedIntake()) {
      intake.runOpenLoopIntake(0.9);
    } else if (oi.getShotSpeedButton())  {
      intake.runOpenLoopIntake(-1.);
    } else if (oi.getAmpSpeedSpeedButton()) {
      intake.runOpenLoopIntake(-1.);
    } else if (oi.getArticulatedIntakeOpenLoopButton()) {
      intake.runOpenLoopIntake(oi.getIntakeManualSpeed());
    } else {
      intake.runOpenLoopIntake(0);
    }    
  }
  
  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
