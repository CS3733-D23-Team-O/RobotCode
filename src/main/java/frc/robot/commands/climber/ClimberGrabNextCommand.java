// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberGrabNextCommand extends CommandBase {

  ClimberSubsystem climberSubsystem = null;
  Timer timer = null;

  /** Creates a new ClimberGrabNextCommand. */
  public ClimberGrabNextCommand(RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.

    climberSubsystem = robotContainer.climberSubsystem;
    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climberSubsystem.extenderActuate();

    timer.start();
    if (timer.hasElapsed(1))
      climberSubsystem.pivotActuate();

    if (timer.hasElapsed(1.5))
      climberSubsystem.clamperToggle();
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