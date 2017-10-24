package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class toggleLED extends Command {


	public toggleLED() {
    }
 
    protected void initialize() {
    }

    protected void execute() {
    	Robot.drivetrain.toggleLED();
    }

    protected boolean isFinished() {
    	return true;
    }

    protected void end() {
    
    }

    protected void interrupted() {
    
    }
}
