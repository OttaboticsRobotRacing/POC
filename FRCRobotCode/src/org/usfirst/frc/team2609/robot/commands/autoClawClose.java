package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class autoClawClose extends Command {

    public autoClawClose() {
    }

    protected void initialize() {
    	Robot.autoClaw.close();
    	//Robot.LedControl.flash(false);
    }

    protected void execute() {
    	//Robot.LedControl.setLed(255,235,59);
    }

    protected boolean isFinished() {
        return timeSinceInitialized()>1;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
