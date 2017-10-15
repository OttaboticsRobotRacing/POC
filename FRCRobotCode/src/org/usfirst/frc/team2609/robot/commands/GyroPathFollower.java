package org.usfirst.frc.team2609.robot.commands;

import org.usfirst.frc.team2609.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;

/**
 *
 */
public class GyroPathFollower extends Command {
	EncoderFollower left,right;
    public GyroPathFollower() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	left = new EncoderFollower(RobotMap.gearPath.getLeftTrajectory());
    	left.configureEncoder(RobotMap.driveTalonLeft1.getEncPosition(), 3840, 6/12);
    	left.configurePIDVA(0.0, 0, 0, 1/(15.7937), 0.0);
    	
    	right = new EncoderFollower(RobotMap.gearPath.getRightTrajectory());
    	right.configureEncoder(RobotMap.driveTalonRight1.getEncPosition(), 3840, 6/12);
    	right.configurePIDVA(0.0, 0, 0, 1/(15.7937), 0.0);

    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftOutput = left.calculate(-RobotMap.driveTalonLeft1.getEncPosition());
    	double rightOutput = right.calculate(RobotMap.driveTalonRight1.getEncPosition());
    	
    	double angleError = (Pathfinder.r2d(left.getHeading()) - RobotMap.ahrs.getYaw());
    	
    	double turn = 0.01*Pathfinder.boundHalfDegrees(angleError);
    	
//    	leftOutput = leftOutput-turn;
//    	rightOutput = rightOutput+turn;
    	
    	System.out.println(leftOutput);
//    	System.out.println(rightOutput);
    	
    	RobotMap.driveTalonLeft1.set(leftOutput);
    	RobotMap.driveTalonRight1.set(rightOutput);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (left.isFinished() && right.isFinished());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
