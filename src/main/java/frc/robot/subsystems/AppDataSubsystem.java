// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class AppDataSubsystem extends SubsystemBase {
  NetworkTable appDataTable; 
    public NetworkTableEntry targetX; 
    public NetworkTableEntry targetY; 
    public NetworkTableEntry targetV;
    NetworkTableEntry serviceRequest;
    NetworkTableEntry driveDirection;
    NetworkTableEntry robotStatus;

    public boolean enableVision = true;

  //read values periodically
  double x;
  double y;
  double v;
  double area;

  /** Creates a new ExampleSubsystem. */
  public AppDataSubsystem() {
    appDataTable = NetworkTableInstance.getDefault().getTable("appData");
    targetX = appDataTable.getEntry("x");
    targetY = appDataTable.getEntry("y");
    targetV = appDataTable.getEntry("tv");
    serviceRequest = appDataTable.getEntry("ServiceRequest");
    driveDirection = appDataTable.getEntry("Instruction");
    robotStatus = appDataTable.getEntry("RobotStatus");


    // SmartDashboard.putNumber("SetX", 0);
    // SmartDashboard.putString("Service Request", "defualt");
  
  }

  public void setX(int x) {
    this.targetX.setNumber(x);
  }


  public double getTX(){
    return appDataTable.getEntry("x").getDouble(0.0);
  }


  public void setRobotStatus(String status) {
    robotStatus.setString(status);
  }

  public void setServiceRequest(String request) {
    String[] array = {request, "Nothing"}; // TODO: fix
    serviceRequest.setStringArray(array);
  }

  public String getAppDirection() {
    return driveDirection.getString("Not Found");
  }

  public String[] getServiceRequestArray() {
    return serviceRequest.getStringArray(null);
  }

  public int getTagFromRequest() {

    String request;
    try {
      request = getServiceRequestArray()[0];
    } catch (NullPointerException e) {
      request = "Null";
    }

    switch (request) {

      case "Meal Request":
        return 3;

      case "Flower Request":
        return 2;

      default:

        return -1;

    }

  }

  public int getTagFromLocation() {

    String location;
    try {
      location = getServiceRequestArray()[1];
    } catch (NullPointerException e) {
      location = "Null";
    }

    switch (location) {

      case "75 Lobby":
        return 4;

      case "75 Lobby Valet Cashier":
        return 1;

      default:

        return -1;

    }
    
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //read values periodically
    x = targetX.getDouble(0.0);
    y = targetY.getDouble(0.0);
    v = targetV.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("AppData X", x);
    SmartDashboard.putNumber("AppData Y", y);
    
    // serviceRequest.setString(SmartDashboard.getString("Service Request", "AAh"));
    // SmartDashboard.putString("Getting Request", serviceRequest.getString("default2"));
    SmartDashboard.putString("Direction Input", driveDirection.getString("aaaahh"));
    SmartDashboard.putNumber("GetTagFromRequest", this.getTagFromLocation());
    SmartDashboard.putNumber("GetTagFromLocation", this.getTagFromLocation());
    SmartDashboard.putString("GetServiceRequest", driveDirection.getString("aaaahh"));

    // setX((int)SmartDashboard.getNumber("SetX", 0));
    
  }


}