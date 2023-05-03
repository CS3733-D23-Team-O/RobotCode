// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AppDataSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AppDriveRobotCommand extends CommandBase {

  DrivetrainSubsystem drivetrainSubsystem;
  AppDataSubsystem appDataSubsystem;


  /** Creates a new AppGoToServiceCommand. */
  public AppDriveRobotCommand(RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drivetrainSubsystem = robotContainer.drivetrainSubsystem;
    this.appDataSubsystem = robotContainer.appDataSubsystem;
    addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    switch(appDataSubsystem.getAppDirection()) {
      case "STOP":
        drivetrainSubsystem.westCoastDrive(0, 0, false);

        break;

      case "Forward":

        drivetrainSubsystem.westCoastDrive(0.15, 0.15, false);

        break;

      case "Back":
        drivetrainSubsystem.westCoastDrive(-0.15, -0.15, false);

        break;

      case "Left":
        drivetrainSubsystem.westCoastDrive(-0.175, 0.175, false);

        break;

      case "Right":
        drivetrainSubsystem.westCoastDrive(0.175, -0.175, false);

        break;

      default:
        drivetrainSubsystem.westCoastDrive(0, 0, false);

        break;

    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
