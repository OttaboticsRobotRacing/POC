package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallDoorOpenAuton extends Command {
	double Distance = 0;

    public BallDoorOpenAuton(double Distance) {
    	this.Distance = Distance;
    }

    protected void initialize() {
    	//Robot.LedControl.flash(true);
    	
    }

    protected void execute() {
    	if (RobotMap.driveTalonLeft1.getPosition()<Distance){
        	Robot.autoClaw.open();
    	}
    	//Robot.LedControl.setLed(255,235,59);
    }

    protected boolean isFinished() {
    	return timeSinceInitialized()>15.0;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
