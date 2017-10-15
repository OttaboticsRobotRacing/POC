package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClawUpWithGear extends Command {

	boolean isFinished;
	
    public ClawUpWithGear() {
        //requires(Robot.vulcanclaw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.vulcanclaw.getGearSensor()){
        	Robot.vulcanclaw.upClaw();
    	}
    	else{
        	isFinished = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !RobotMap.clawUpSensor.get() || timeSinceInitialized()>1 || isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
