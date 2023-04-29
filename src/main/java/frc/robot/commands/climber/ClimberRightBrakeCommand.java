// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberRightBrakeCommand extends CommandBase {

  ClimberSubsystem climberSubsystem;
  boolean brake = true;
  /** Creates a new ClimberLeftPivotCommand. */
  public ClimberRightBrakeCommand(RobotContainer robotContainer, boolean brake) {
    // Use addRequirements() here to declare subsystem dependencies.
    climberSubsystem = robotContainer.climberSubsystem;
    this.brake = brake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climberSubsystem.rightBrakeActuate(brake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
