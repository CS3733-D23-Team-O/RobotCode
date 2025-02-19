// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.simpleTest;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TestVoltCommand extends CommandBase {
  DrivetrainSubsystem drivetrainSubsystem;

  /** Creates a new TestVoltCommand. */
  public TestVoltCommand(RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrainSubsystem = robotContainer.drivetrainSubsystem;
    addRequirements(this.drivetrainSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrainSubsystem.tankDriveVolts(6, 6);
    drivetrainSubsystem.differentialDrive.feed();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.tankDriveVolts(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
