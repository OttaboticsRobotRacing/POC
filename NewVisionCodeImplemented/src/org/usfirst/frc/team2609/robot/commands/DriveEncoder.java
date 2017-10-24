package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2609.robot.*;

import com.ctre.CANTalon.TalonControlMode;

public class DriveEncoder extends Command {
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Voltage);//controlMode);
    }
    
    protected void execute() {
    	//double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	//Robot.drivetrain.driveStraight(RobotMap.driveTalonLeft1.getPosition(), RobotMap.driveTalonRight1.getPosition(), gyroYaw, drivePID, steeringPID);	
    }

    protected boolean isFinished() {
    	//return drivePID.isDone();
    	return true;
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
