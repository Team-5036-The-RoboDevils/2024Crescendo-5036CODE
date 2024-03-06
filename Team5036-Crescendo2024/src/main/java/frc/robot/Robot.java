// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

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
    SmartDashboard.putNumber("Value of encoder ticks for 1m is:", drivetrain.getEncoderPos());
    SmartDashboard.putNumber("Arm motor power = ", intakeHardware.getArmPower());

    
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
    drivetrain.resetEncoders();
    double targetDist = 200;
    double currentPos = 0;
    double ConvertedDist = 0;
    
    // TODO: Implement autonomous modes.

    // Shooter Auto for Middle Placement
    shooter.runOpenLoopFront(1);
    Timer.delay(5); // play around with this
    shooter.runOpenLoopBack(1);
    Timer.delay(2); // play around with this
    shooter.runOpenLoopFront(0); 
    shooter.runOpenLoopBack(0);

    // figure ticks out by pushing a robot a metre and figuring out how many ticks

    // Drive Auto for Middle Placement

     while(drivetrain.getDistTravelled() <= targetDist && isAutonomous()){
      SmartDashboard.putNumber("Distance covered", ConvertedDist);
      double forward = -0.3;
      double rotate = 0;
      drivetrain.arcadeDrive(forward, rotate);
      intake.controllerClosedLoopArticulation(0);
      intake.runOpenLoopIntake(0.4);
     }
     double forward = 0;
     double rotate = 0;
     drivetrain.arcadeDrive(forward, rotate);
     

    // Intake Note Auto for Middle Placement
    // Move arm down, start intake, keep intake 


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
    drivetrain.resetEncoders();
    double forward = oi.getDriveTrainForward();
    double rotate = oi.getDriveTrainRotate();

    // Drive
    forward = forward * Math.abs(forward);
    rotate = rotate * rotate * rotate * 0.8;
    drivetrain.arcadeDrive(forward, rotate);


    // Articulation of intake
    if (oi.deployArticulatedIntake()) {
      intake.controllerClosedLoopArticulation(-23); // this too
    } else if (oi.getArticulatedIntakeOpenLoopButton()) {
      intake.controllerOpenLoopArticulation(oi.getArticulatedIntakeOpenLoopAxis() * 0.2);
    } else {
      intake.controllerClosedLoopArticulation(145); //play with these 
    }


    // intake
    // if(oi.getIntakeOpenLoopButton()){
    //   intake.setIntakeMotorPower(0.6);
    // } 
    
    // if (oi.getOuttakeOpenLoopButton()) {
    //   intake.setOuttakeMotorPower(0.6);
    // }
    
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
    if (oi.deployArticulatedIntake()) { // Add a condition here so that it only starts running when angle is below a certain amount.
      intake.runOpenLoopIntake(0.7); // Play with this value. See if just 0.6 still works.
    } else if (oi.getShotSpeedButton())  {
      intake.runOpenLoopIntake(-1.);
    } else if (oi.getAmpSpeedSpeedButton()) {
      Timer.delay(1.0);
      intake.runOpenLoopIntake(-0.6);
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
