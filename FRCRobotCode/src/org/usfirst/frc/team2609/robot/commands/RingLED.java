package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RingLED extends Command {

	
    private boolean state;

	public RingLED(boolean state) {
    	this.state = state;
    }
 
    protected void initialize() {
    	Robot.LedControl.trackLED(state);
    }

    protected void execute() {
    	Robot.LedControl.trackLED(state);
    }

    protected boolean isFinished() {
    	return true;
    }

    protected void end() {
    
    }

    protected void interrupted() {
    
    }
}
