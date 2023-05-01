// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AppDataSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AppGoToServiceCommand extends CommandBase {

  DrivetrainSubsystem drivetrainSubsystem;
  LimeLightSubsystem limeLightSubsystem;
  AppDataSubsystem appDataSubsystem;
  double distThreshold;


  enum RobotState {
    START,
    STOP,
    SEARCHING_REQUEST,
    SEARCHING_LOCATION,
    DRIVE_TO_REQUEST,
    DRIVE_TO_LOCATION;
  }

  RobotState robotState = RobotState.START;

  /** Creates a new AppGoToServiceCommand. */
  public AppGoToServiceCommand(RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.drivetrainSubsystem = robotContainer.drivetrainSubsystem;
    this.limeLightSubsystem = robotContainer.limeLightSubsystem;
    this.appDataSubsystem = robotContainer.appDataSubsystem;
    addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    robotState = RobotState.START;
    distThreshold = 5;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    if (appDataSubsystem.getAppDirection().equals("STOP") || appDataSubsystem.getTagFromRequest() != -1) {
      switch(robotState) {
        case START:
          drivetrainSubsystem.westCoastDrive(0, 0, false);
  
          if (appDataSubsystem.getTagFromRequest() != -1) {
            robotState = RobotState.SEARCHING_REQUEST;
            appDataSubsystem.setRobotStatus("Searching for Request...");
          }
  
          break;
  
        case SEARCHING_REQUEST:
  
          drivetrainSubsystem.westCoastDrive(0.175, -0.175, false);
  
          if (limeLightSubsystem.getTID().getDouble(0) == appDataSubsystem.getTagFromRequest()) {
            robotState = RobotState.DRIVE_TO_REQUEST;
            appDataSubsystem.setRobotStatus("Apporoaching Request...");
          }
  
          break;
  
        case DRIVE_TO_REQUEST:
  
          drivetrainSubsystem.goToTag(distThreshold);
          if (drivetrainSubsystem.getInRange(distThreshold)) {
            robotState = RobotState.SEARCHING_LOCATION;
            appDataSubsystem.setRobotStatus("Searching for Location...");
          }
  
          break;
        case SEARCHING_LOCATION:
  
          drivetrainSubsystem.westCoastDrive(0.175, -0.175, false);
  
          if (limeLightSubsystem.getTID().getDouble(0) == appDataSubsystem.getTagFromLocation()) {
            robotState = RobotState.DRIVE_TO_LOCATION;
            appDataSubsystem.setRobotStatus("Apporoaching Location...");
          }
          break;
  
        case DRIVE_TO_LOCATION:
  
          drivetrainSubsystem.goToTag(distThreshold);
          if (drivetrainSubsystem.getInRange(distThreshold)) {
            robotState = RobotState.START;
            appDataSubsystem.setServiceRequest("None");
            appDataSubsystem.setRobotStatus("Finished Request");
          }
          break;
        
      }
    } else {
      
      if (!appDataSubsystem.getAppDirection().equals("STOP")) {
        drivetrainSubsystem.driveApp(appDataSubsystem.getAppDirection());
      }
      // appDataSubsystem.setRobotStatus("Driving");
      // appDataSubsystem.setServiceRequest("None");
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
