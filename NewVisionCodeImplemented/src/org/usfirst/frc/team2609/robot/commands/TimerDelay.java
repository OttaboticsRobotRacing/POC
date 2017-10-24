package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;

public class TimerDelay extends Command {

	private double delaySet;

    public TimerDelay(double delaySet) {
        this.delaySet = delaySet;
    }

    protected void initialize() {
    	setTimeout(delaySet);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
