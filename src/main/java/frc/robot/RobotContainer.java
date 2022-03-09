/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Turret.VisionCommand;
import frc.robot.commands.auto.simpleTest.testAuto;
import frc.robot.commands.drivetrain.DefaultArcadeDriveCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.input.AttackThree;
import frc.robot.input.XboxOneController;
import frc.robot.subsystems.CollectorSubsystem;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import frc.robot.commands.climber.ClimberJumpGrabCommand;
import frc.robot.commands.drivetrain.DefaultArcadeDriveCommand;
import frc.robot.input.AttackThree;
import frc.robot.input.XboxOneController;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
* This class is where the bulk of the robot should be declared. Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
* subsystems, commands, and button mappings) should be declared here.
*/
public class RobotContainer {

    // Choosers
    public final SendableChooser<Integer> bottomRPMChooser = new SendableChooser<>();
    public final SendableChooser<Integer> topRPMChooser = new SendableChooser<>();

    /*
    * Subsystems
    */

    public final DrivetrainSubsystem drivetrainSubsystem =
            new DrivetrainSubsystem(
                    Constants.DrivetrainConstants.P,
                    Constants.DrivetrainConstants.I,
                    Constants.DrivetrainConstants.D);
    public final CollectorSubsystem collectorSubsystem = new CollectorSubsystem();

    public final LimeLightSubsystem limeLightSubsystem =
            new LimeLightSubsystem();

    public final TurretSubsystem turretSubsystem = 
        new TurretSubsystem(
                Constants.TurretConstants.P,
                Constants.TurretConstants.I,
                Constants.TurretConstants.D,
                Constants.TurretConstants.F,
                limeLightSubsystem);

    public final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

        public final ShooterSubsystem shooterSubsystem = 
                new ShooterSubsystem();

    /*
    * Input
    */
   public final AttackThree leftStick =
           new AttackThree(Constants.InputConstants.LEFT_JOYSTICK_CHANNEL);
    public final AttackThree rightStick =
            new AttackThree(Constants.InputConstants.RIGHT_JOYSTICK_CHANNEL);
    public final XboxOneController driverXboxController =
            new XboxOneController(Constants.InputConstants.DRIVER_XBOX_CHANNEL);

    /**
    * Constructor for the robot container Called when the Rio is powered on, and is only called once.
    * We use this to configure commands from buttons and default commands
    */
    public RobotContainer() {
        leftStick.triggerButton.toggleWhenPressed(new RunCommand(()-> collectorSubsystem.collect(.75), collectorSubsystem));
        leftStick.leftFaceButton.toggleWhenPressed(new InstantCommand(()-> collectorSubsystem.toggle(), collectorSubsystem));
        leftStick.middleFaceButton.whenPressed(new RunCommand(()-> collectorSubsystem.upperBallPath(.75), collectorSubsystem));

        //TODO 
        new JoystickButton(driverXboxController, 5)
        .whenPressed(new InstantCommand(()-> turretSubsystem.turretPID(-25), turretSubsystem))
        .whenReleased(new InstantCommand(()-> turretSubsystem.turretManual(0), turretSubsystem));

        new JoystickButton(driverXboxController, 6)
        .whenPressed(new InstantCommand(()-> turretSubsystem.turretPID(25), turretSubsystem))
        .whenReleased(new InstantCommand(()-> turretSubsystem.turretManual(0), turretSubsystem));

        new JoystickButton(driverXboxController, 2)
        .whenPressed(new InstantCommand(()-> turretSubsystem.turretManual(.11), turretSubsystem))
        .whenReleased(new InstantCommand(()-> turretSubsystem.turretManual(0), turretSubsystem));

        new JoystickButton(driverXboxController, 3)
        .whenPressed(new InstantCommand(()-> turretSubsystem.turretManual(-.11), turretSubsystem))
        .whenReleased(new InstantCommand(()-> turretSubsystem.turretManual(0), turretSubsystem));

        new JoystickButton(driverXboxController, 4)
                .whenPressed(new InstantCommand(() -> turretSubsystem.resetEncoder(), turretSubsystem));

        new JoystickButton(driverXboxController, 1)
                .whenPressed(new VisionCommand(this));


        driverXboxController.aButton.whenPressed(new InstantCommand(()-> drivetrainSubsystem.resetAll(), drivetrainSubsystem));
        leftStick.middleFaceButton.whenPressed(new InstantCommand(()-> drivetrainSubsystem.resetGyro(false), drivetrainSubsystem));
        drivetrainSubsystem.gyro.calibrate();
        rightStick.middleFaceButton.whenPressed(() -> {
            new ClimberJumpGrabCommand(this).schedule();
        });
    }

    /**
    * Use this to pass the autonomous command to the main {@link Robot} class.
    *
    * @return the command to run in autonomous
    */
    public Command getAutonomousCommand() {
        //return null;
        return new testAuto(this);
    }

    public void setDefaultCommands() {
        // Default drive command
        // drivetrainSubsystem.setDefaultCommand(new DefaultArcadeDriveCommand(this));
        turretSubsystem.setDefaultCommand(new VisionCommand(this));
        //drivetrainSubsystem.setDefaultCommand(new DefaultArcadeDriveCommand(this));
        //climberSubsystem.setDefaultCommand(new ClimberJumpGrabCommand(this));
    }

    public void periodic() {
        if (!climberSubsystem.jumperLimitSwitch.get()) {
            new ClimberJumpGrabCommand(this).schedule();
        }
    }
}
