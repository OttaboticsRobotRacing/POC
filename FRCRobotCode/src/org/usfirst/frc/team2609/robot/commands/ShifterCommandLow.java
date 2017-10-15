package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShifterCommandLow extends Command {

    public ShifterCommandLow() {
    }

    protected void initialize() {
    	Robot.shifter.low();
    	SmartDashboard.putBoolean("LED Flash: ", false);
    	//Robot.LedControl.flash(false);
    }

    protected void execute() {
    	//Robot.LedControl.setLed(255,235,59);
    }

    protected boolean isFinished() {
        return true;//timeSinceInitialized()>1;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
