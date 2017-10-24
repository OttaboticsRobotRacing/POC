package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.GearPath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearSwerve extends Command {
	GearPath gearPath;
	
    public GearSwerve() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gearPath = new GearPath();
    	gearPath.gyro = RobotMap.ahrs.getAngle();
    	gearPath.angleToDrive = Robot.table.getNumber("angleToTarget", 0);
    	gearPath.distanceToTarget = 48; // Get the distance to target from networktables
    	gearPath.calc();
    	//RobotMap.gearPath = this.gearPath;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (this.gearPath.angleToDrive!=0 && this.gearPath.distanceToDrive!=0);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
