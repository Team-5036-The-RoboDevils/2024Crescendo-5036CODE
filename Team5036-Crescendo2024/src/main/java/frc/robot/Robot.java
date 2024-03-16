// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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
  IDrivetrainHardware drivetrainHardware;
  private static final String middleAutoBlue = "MiddleBlue"; 
  private static final String rightAuto = "Right"; 
  private static final String leftAuto = "Left"; 
  private static final String nothing = "Nothing";
  private static final String backward = "Backwards";
  private static final String shootPreload = "ShootPreload";
  private static final String middleAutoRed = "MiddleRed"; 

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

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
  /** This function is called periodically during autonomous. */

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Middle Auto Position - Blue", middleAutoBlue);
    m_chooser.addOption("Middle Auto Position - Red", middleAutoRed);
    m_chooser.addOption("Right Auto Position", rightAuto); 
    m_chooser.addOption("Left Auto Position", leftAuto);
    m_chooser.addOption("Do nothing", nothing);
    m_chooser.addOption("Backwards Auto", backward);
    m_chooser.addOption("Shoot Preload", shootPreload);
    SmartDashboard.putData("Auto choices", m_chooser);
    shooterHardware = new ShooterHardware();
    intakeHardware = new ArticulatedIntakeHardware();
    drivetrainHardware = new DrivetrainHardware();

    oi = new OperatorInterface();
    drivetrain = new Drivetrain(drivetrainHardware);
    shooter = new Shooter(shooterHardware);
    intake = new ArticulatedIntake(intakeHardware);

    intakeHardware.resetArmEncoder();

    drivetrain.resetEncoders();
    drivetrain.resetGyro();
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
    SmartDashboard.putNumber("DEBUG - Gyro angle:", drivetrain.getAngle());
    SmartDashboard.putNumber("DEBUG - Encoder distance:", drivetrain.getDistTravelled());
    SmartDashboard.putNumber("DEBUG - Left encoder raw distance:", drivetrainHardware.getLeftEncoderPos());
    SmartDashboard.putNumber("DEBUG - Right encoder raw distance:", drivetrainHardware.getRightEncoderPos());

    if (oi.getDebugButton()) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro();
    }
  }

  private boolean isInAutoTime(long startTime) {
    long currentTime = System.currentTimeMillis();
    if (!isAutonomous()) return false;
    if ((currentTime - startTime) > 15000) return false;
    return true;
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
    long startTime = System.currentTimeMillis();

    double targetDist = 200;

    m_autoSelected = m_chooser.getSelected();
    
    if (m_autoSelected == middleAutoBlue) {
      // Shooter Auto for Middle Placement: Run shooter, score pre-Note
      shooter.runOpenLoopFront(1);
      shooter.runOpenLoopBack(1);
      Timer.delay(5); // play around with this
      intake.runOpenLoopIntake(-1);
      Timer.delay(2); // play around with this
      shooter.runOpenLoopFront(0); 
      shooter.runOpenLoopBack(0);
      intake.runOpenLoopIntake(0);
      System.out.println("MIDDLE AUTO: Shot pre-load");
      if (!isInAutoTime(startTime)) return;

      // Drive Auto for Middle Placement: Run backwards, pick up a note
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      //double initialDist = drivetrain.getDistTravelled();
      while(drivetrain.getDistTravelled() <= targetDist && isInAutoTime(startTime)) {
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled());
        double forward = -0.1;
        double rotate = 0;
        drivetrain.arcadeDrive(forward, rotate);
        intake.controllerClosedLoopArticulation(-23);
        intake.runOpenLoopIntake(1);
      }
      System.out.println("MIDDLE AUTO: Drove back");
      if (!isInAutoTime(startTime)) return;
      
      // Stop drivetrain, intake and shooter
      drivetrain.arcadeDrive(0, 0);
      intake.runOpenLoopIntake(0);
      shooter.runOpenLoopBack(0);
      shooter.runOpenLoopFront(0);
      if (!isInAutoTime(startTime)) return;
      
      // Drive toward speaker again
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      //initialDist = drivetrain.getDistTravelled();
      while (drivetrain.getDistTravelled() >= -targetDist && isInAutoTime(startTime)) { // head back toward speaker
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled());
        drivetrain.arcadeDrive(0.3, 0);
        intake.controllerClosedLoopArticulation(145); //Move arm back to inwards position
        shooter.runOpenLoopFront(1); // Spin up front motor
        shooter.runOpenLoopBack(1); // Spin up back motor
      }
      System.out.println("MIDDLE AUTO: Drive toward speaker again");
      if (!isInAutoTime(startTime)) return;

      // Stop drivetrain
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      // Send shot
      intake.runOpenLoopIntake(-1); // run outtake 
      Timer.delay(2); // play around with this
      System.out.println("MIDDLE AUTO: SENT SHOT");
      if (!isInAutoTime(startTime)) return;
    }
    
    else if (m_autoSelected == rightAuto) {
      // Shoot
      shooter.runOpenLoopFront(1);
      shooter.runOpenLoopBack(1);
      Timer.delay(5); // play around with this
      intake.runOpenLoopIntake(-1);
      Timer.delay(2); // play around with this
      shooter.runOpenLoopFront(0); 
      shooter.runOpenLoopBack(0);
      intake.runOpenLoopIntake(0);
      System.out.println("RIGHT AUTO: SENT SHOT");
      if (!isInAutoTime(startTime)) return;

      // Drive a bit back
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while(drivetrain.getDistTravelled() <= 200 && isInAutoTime(startTime)) {
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled()); 
        double forward = -0.3; 
        double rotate = 0.0; 
        drivetrain.arcadeDrive(forward, rotate); 
      }
      System.out.println("RIGHT AUTO: DROVE BACK AGAIN");
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;
      
      // Stop
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;
     
      // Turn
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while (drivetrain.getAngle() <= 45 && isInAutoTime(startTime)) {
        drivetrain.arcadeDrive(0, 0.3);
      }
      drivetrain.arcadeDrive(0, 0);
      System.out.println("RIGHT AUTO: TURNED");
      if (!isInAutoTime(startTime)) return;

      // Stop
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      // Drive a bit back
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while((drivetrain.getDistTravelled() <= targetDist && isInAutoTime(startTime))) {
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled()); 
        double forward = -0.3; 
        double rotate = 0.0; 
        drivetrain.arcadeDrive(forward, rotate); 
      }    
      drivetrain.arcadeDrive(0, 0);
      System.out.println("RIGHT AUTO: DROVE BACK AGAIN");
    } else if (m_autoSelected == leftAuto) {
      // Shoot
      shooter.runOpenLoopFront(1);
      shooter.runOpenLoopBack(1);
      Timer.delay(5); // play around with this
      intake.runOpenLoopIntake(-1);
      Timer.delay(2); // play around with this
      shooter.runOpenLoopFront(0); 
      shooter.runOpenLoopBack(0);
      intake.runOpenLoopIntake(0);
      if (!isInAutoTime(startTime)) return;

      // Drive a bit back
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while(drivetrain.getDistTravelled() <= 200 && isInAutoTime(startTime)) {
        //SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled() - initialDist); 
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled()); 
        double forward = -0.3; 
        double rotate = 0.0; 
        drivetrain.arcadeDrive(forward, rotate); 
      }
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;
      
      // Stop
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;
      
      // Turn
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while (drivetrain.getAngle() >= -45 && isInAutoTime(startTime)) {
        drivetrain.arcadeDrive(0, -0.3);
      }
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      // Stop
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      // Drive a bit back
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      //initialDist = drivetrain.getDistTravelled();
      while(drivetrain.getDistTravelled() <= 200 && isInAutoTime(startTime)) {
        //SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled() - initialDist); 
        double forward = -0.3; 
        double rotate = 0.0; 
        drivetrain.arcadeDrive(forward, rotate); 
      }
      drivetrain.arcadeDrive(0, 0);
    } else if (m_autoSelected == nothing) {

    } else if (m_autoSelected == backward) {
      // Drive a bit back
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while(drivetrain.getDistTravelled() <= 300 && isInAutoTime(startTime)) {
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled()); 
        double forward = -0.3; 
        double rotate = 0.0; 
        drivetrain.arcadeDrive(forward, rotate); 
      }
      drivetrain.arcadeDrive(0, 0);
    } else if (m_autoSelected == shootPreload) {
      // Shoot
      shooter.runOpenLoopFront(1);
      shooter.runOpenLoopBack(1);
      Timer.delay(5); // play around with this
      intake.runOpenLoopIntake(-1);
      Timer.delay(2); // play around with this
      shooter.runOpenLoopFront(0); 
      shooter.runOpenLoopBack(0);
      intake.runOpenLoopIntake(0);
      System.out.println("PRELOAD AUTO: SENT SHOT");
      if (!isInAutoTime(startTime)) return;
    } else if (m_autoSelected == middleAutoRed) {
      // Shooter Auto for Middle Placement: Run shooter, turn 90, score pre-Note
      shooter.runOpenLoopFront(1);
      shooter.runOpenLoopBack(1);

      // Turn
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      while (drivetrain.getAngle() >= -90 && isInAutoTime(startTime)) {
        drivetrain.arcadeDrive(0, -0.3);
      }
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      Timer.delay(5); // play around with this
      intake.runOpenLoopIntake(-1);
      Timer.delay(2); // play around with this
      shooter.runOpenLoopFront(0); 
      shooter.runOpenLoopBack(0);
      intake.runOpenLoopIntake(0);
      System.out.println("MIDDLE AUTO: Shot pre-load");
      if (!isInAutoTime(startTime)) return;

      // Drive Auto for Middle Placement: Run backwards, pick up a note
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      //double initialDist = drivetrain.getDistTravelled();
      while(drivetrain.getDistTravelled() <= targetDist && isInAutoTime(startTime)) {
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled());
        double forward = -0.1;
        double rotate = 0;
        drivetrain.arcadeDrive(forward, rotate);
        intake.controllerClosedLoopArticulation(-23);
        intake.runOpenLoopIntake(1);
      }
      System.out.println("MIDDLE AUTO: Drove back");
      if (!isInAutoTime(startTime)) return;
      
      // Stop drivetrain, intake and shooter
      drivetrain.arcadeDrive(0, 0);
      intake.runOpenLoopIntake(0);
      shooter.runOpenLoopBack(0);
      shooter.runOpenLoopFront(0);
      if (!isInAutoTime(startTime)) return;
      
      // Drive toward speaker again
      drivetrain.resetEncoders();
      drivetrain.resetGyro(); 
      Timer.delay(0.1);
      //initialDist = drivetrain.getDistTravelled();
      while (drivetrain.getDistTravelled() >= -targetDist && isInAutoTime(startTime)) { // head back toward speaker
        SmartDashboard.putNumber("Distance covered", drivetrain.getDistTravelled());
        drivetrain.arcadeDrive(0.3, 0);
        intake.controllerClosedLoopArticulation(145); //Move arm back to inwards position
        shooter.runOpenLoopFront(1); // Spin up front motor
        shooter.runOpenLoopBack(1);
      }
      System.out.println("MIDDLE AUTO: Drive toward speaker again");
      if (!isInAutoTime(startTime)) return;

      // Stop drivetrain
      drivetrain.arcadeDrive(0, 0);
      if (!isInAutoTime(startTime)) return;

      // Send shot
      intake.runOpenLoopIntake(-1); // run outtake 
      Timer.delay(2); // play around with this
      System.out.println("MIDDLE AUTO: SENT SHOT");
      if (!isInAutoTime(startTime)) return;
 
    }

    drivetrain.arcadeDrive(0, 0);
    intake.runOpenLoopIntake(0);
    intake.controllerOpenLoopArticulation(0);
    shooter.runOpenLoopFront(0);
    shooter.runOpenLoopBack(0);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // In case we exit autonomousInit early
    drivetrain.arcadeDrive(0, 0);
    intake.runOpenLoopIntake(0);
    intake.controllerOpenLoopArticulation(0);
    shooter.runOpenLoopFront(0);
    shooter.runOpenLoopBack(0);
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    drivetrain.arcadeDrive(0, 0);
    intake.runOpenLoopIntake(0);
    intake.controllerOpenLoopArticulation(0);
    shooter.runOpenLoopFront(0);
    shooter.runOpenLoopBack(0);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = oi.getDriveTrainForward();
    double rotate = oi.getDriveTrainRotate();

    // Drive
    forward = forward * Math.abs(forward);
    if (!oi.getSlowMode()) {
      rotate = rotate * rotate * rotate * 0.8;
    } else {
      rotate = rotate * rotate * rotate * 0.4;
    }
    drivetrain.arcadeDrive(-forward, rotate);


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
    if (oi.spinUp() || oi.getShotSpeedButton()) {
      shooter.runOpenLoopBack(1.);
    } else if (oi.getHpIntakeSpeedButton()) {
      shooter.setBackMotorRunRpm(750, false);
    } else if (oi.getAmpSpeedSpeedButton()) {
      shooter.setBackMotorRunRpm(550, true);
    } else {
      shooter.runOpenLoopBack(0);
    }

    // Intake motor
    if (oi.getArticulatedIntakeOpenLoopButton()) {
      intake.runOpenLoopIntake(oi.getIntakeManualSpeed());
    } else if (oi.deployArticulatedIntake()) { // Add a condition here so that it only starts running when angle is below a certain amount.
      intake.runOpenLoopIntake(1); // Play with this value. See if just 0.6 still works.
    } else if (oi.getShotSpeedButton())  {
      intake.runOpenLoopIntake(-1.);
    } else if (oi.getAmpSpeedSpeedButton()) {
      //Timer.delay(1.0);
      intake.runOpenLoopIntake(-1);
    } else if (oi.getHpIntakeSpeedButton()) {
      intake.runOpenLoopIntake(1);
    } else {
      intake.runOpenLoopIntake(0);
    }    

    if (oi.getDebugButton()) {
      drivetrain.resetEncoders();
      drivetrain.resetGyro();
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
