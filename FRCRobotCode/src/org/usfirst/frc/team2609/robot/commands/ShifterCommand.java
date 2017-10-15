package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ShifterCommand extends Command {

    public ShifterCommand() {
    }

    protected void initialize() {
    	Robot.shifter.toggle();
    	
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
