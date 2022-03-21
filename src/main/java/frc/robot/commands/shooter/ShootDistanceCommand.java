// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootDistanceCommand extends CommandBase {

  ShooterSubsystem shooterSubsystem;
  LimeLightSubsystem limeLightSubsystem;
  double distance;

  /** Creates a new ShootDistanceCommand. */
  public ShootDistanceCommand(RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.shooterSubsystem = robotContainer.shooterSubsystem;
    this.limeLightSubsystem = robotContainer.limeLightSubsystem;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.distance = limeLightSubsystem.getDistanceToTarget();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooterSubsystem.adjustHood(distance);
    shooterSubsystem.adjustFlywheelRPM(distance);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooterSubsystem.hoodMotionCommplete() && shooterSubsystem.flywheelAtTargetRPM();
  }
}
