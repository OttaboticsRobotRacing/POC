package org.usfirst.frc.team2609.robot.commands;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class gearRollerSetSpeed extends Command {
	double speed = 0;
    public gearRollerSetSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.gearRoller.set(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.gearRoller.set(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
