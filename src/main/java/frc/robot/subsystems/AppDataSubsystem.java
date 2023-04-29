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
    NetworkTableEntry targetArea; 
    NetworkTableEntry targetFound;
    NetworkTableEntry ledMode;
    NetworkTableEntry pipeline;
    NetworkTableEntry botpose;
    NetworkTableEntry botpose_red;
    NetworkTableEntry botpose_blue;
    NetworkTableEntry ta;
    NetworkTableEntry camMode;
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

    SmartDashboard.putNumber("SetX", 0);
  
  }

  public void setX(int x) {
    this.targetX.setNumber(x);
  }


  public double getTX(){
    return appDataTable.getEntry("x").getDouble(0.0);
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
    

    setX((int)SmartDashboard.getNumber("SetX", 0));
    
  }


}