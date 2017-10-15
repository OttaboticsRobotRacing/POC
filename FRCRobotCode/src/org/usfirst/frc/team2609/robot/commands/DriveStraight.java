package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

public class DriveStraight extends Command {
	SimPID steeringPID;
	double gyroP = 0;
	double gyroI = 0;
	double gyroD = 0;
	double gyroMax = 0;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveStraight() {
        //requires(Robot.drivetrain);
    }

    protected void initialize(){
        this.steeringPID = new SimPID();
    	steeringPID.resetPreviousVal();
        this.steeringPID.setDesiredValue(RobotMap.ahrs.getYaw());
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ",0);
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ",0);
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ",0);
        gyroMax = (double)SmartDashboard.getNumber("Gyro Max: ",0);
        this.steeringPID.setConstants(gyroP, gyroI, gyroD);
        this.steeringPID.setMaxOutput(gyroMax);
        }

    protected void execute() {
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	Robot.drivetrain.driveStraightHuman(gyroYaw, steeringPID);
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
