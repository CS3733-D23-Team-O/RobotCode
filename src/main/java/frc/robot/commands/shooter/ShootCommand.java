// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CollectorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
  ShooterSubsystem shooterSubsystem = null;
  CollectorSubsystem collectorSubsystem = null;
  RobotContainer robotContainer;
  double speed = 0;
  double startTime;
  boolean tripped = true;
  
  /** Creates a new ShootCommand. */
  public ShootCommand(RobotContainer robotContainer, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.

    shooterSubsystem = robotContainer.shooterSubsystem;
    collectorSubsystem = robotContainer.collectorSubsystem;
    this.robotContainer = robotContainer;

    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = RobotController.getFPGATime() / 1000.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    

    if (!shooterSubsystem.getIsRunning()) {
      
        new ShootCommand(robotContainer, speed).schedule();
        shooterSubsystem.setIsRunning(true);
    } else {
      shooterSubsystem.flywheelManual(0);
      shooterSubsystem.setIsRunning(false);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}