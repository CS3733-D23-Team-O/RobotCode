package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase {

    Solenoid jumper = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.JUMPER_ID);
    Solenoid release_jumper = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.RELEASE_JUMPER_ID);
    Solenoid clamper = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.CLAMPER_ID);
    Solenoid pivot = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.PIVOT_ID);
    Solenoid extender = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.EXTENDER_ID);

    DigitalInput jumperLimitSwitch = new DigitalInput(0); // Limit switch is pressed when in neutral state,
                                                                 // not pressed when "jumping"

    WPI_TalonFX climber_motor = new WPI_TalonFX(ClimberConstants.CLIMBER_MOTOR_CHANNEL);

    // Choosers
    private final SendableChooser<Double> delayTimeChooser = new SendableChooser<>();

    public ClimberSubsystem() {

        for (double i = 0.01; i < 0.5; i+=0.01) {
            delayTimeChooser.addOption(i+" Seconds", i);
        }

        SmartDashboard.putData(delayTimeChooser);

    }

    /**
     * Turns the jumper solenoid on
     */
    public void jumperActuate() {
        jumper.set(true);
    }

    /**
     * Turns the release jumper solenoid on
     */
    public void releaseJumperActuate() {
        release_jumper.set(true);
    }

    /**
     * Toggles the clamper solenoid on and off
     */
    public void clamperToggle() {
        clamper.toggle();
    }

    /**
     * Turns the pivot solenoid on
     */
    public void pivotActuate() {
        pivot.set(true);
    }

    /**
     * Turns the extender solenoid on
     */
    public void extenderActuate() {
        extender.set(true);
    }

    /**
     * Extends the climber
     * @param speed the speed of the climber motor [-1, 1]
     */
    public void extendClimber(double speed) {
        climber_motor.set(speed);
    }

    /**
     * Determines if the climber extender has reached the forward limit
     * @return true if it has reached the limit, false otherwise
     */
    public boolean climberLimitFwd() {
        return climber_motor.isFwdLimitSwitchClosed() == 1;
    }

    /**
     * Determines if the climber extender has reached the reverse limit
     * @return true if it has reached the limit, false otherwise
     */
    public boolean climberLimitRev() {
        return climber_motor.isRevLimitSwitchClosed() == 1;
    }

    public Double getDelay() {
        return delayTimeChooser.getSelected();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Jumper Limit Switch", jumperLimitSwitch.get());
    }


}
