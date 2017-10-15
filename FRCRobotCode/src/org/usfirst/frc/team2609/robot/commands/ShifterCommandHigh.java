package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShifterCommandHigh extends Command {

    public ShifterCommandHigh() {
    }

    protected void initialize() {
    	Robot.shifter.high();
    	SmartDashboard.putBoolean("LED Flash: ", true);
    	//Robot.LedControl.flash(true);
    	
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
