package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

public class DriveEncoderSimBotics1114Style extends Command {
	SimPID drivePID;
	SimPID steeringPID;
	double gyroP = 0;
	double gyroI = 0;
	double gyroD = 0;
	double gyroMax = 0;
	double driveP = 0;
	double driveI = 0;
	double driveD = 0;
	double driveMax = 0;
	double driveEps = 0;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoderSimBotics1114Style(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ");
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ");
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ");
        gyroMax = (double)SmartDashboard.getNumber("Gyro Max: ");
        driveP = (double)SmartDashboard.getNumber("Drive P: ");
        driveI = (double)SmartDashboard.getNumber("Drive I: ");
        driveD = (double)SmartDashboard.getNumber("Drive D: ");
        driveMax = (double)SmartDashboard.getNumber("Drive Max: ");
        driveEps = (double)SmartDashboard.getNumber("Drive Eps: ");
        this.steeringPID = new SimPID();
        this.steeringPID.setDesiredValue(driveHeading);
        this.steeringPID.setConstants(gyroP, gyroI, gyroD);
        this.steeringPID.setMaxOutput(gyroMax);
        this.drivePID = new SimPID();
        this.drivePID.setDesiredValue(driveTarget);
        this.drivePID.setConstants(driveP, driveI, driveD);
        this.drivePID.setMaxOutput(driveMax);
        this.drivePID.setDoneRange(1);
        this.drivePID.setErrorEpsilon(driveEps);
    }

    protected void initialize() 																						{
    	steeringPID.resetPreviousVal();
    	drivePID.resetPreviousVal();
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ")																;
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ")																;
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ")																;
        gyroMax = (double)SmartDashboard.getNumber("Gyro Max: ")															;
        this.steeringPID.setConstants(gyroP, gyroI, gyroD)																	;
        this.steeringPID.setMaxOutput(gyroMax)																				;
        driveP = (double)SmartDashboard.getNumber("Drive P: ")																;
        driveI = (double)SmartDashboard.getNumber("Drive I: ")																;
        driveD = (double)SmartDashboard.getNumber("Drive D: ")																;
        driveMax = (double)SmartDashboard.getNumber("Drive Max: ")															;
        driveEps = (double)SmartDashboard.getNumber("Drive Eps: ")															;
        this.drivePID.setConstants(driveP, driveI, driveD)																	;
        this.drivePID.setMaxOutput(driveMax)																				;
        this.drivePID.setDoneRange(1)																						;
        this.drivePID.setErrorEpsilon(driveEps)																				;
    																													}

    protected void execute() {
    	//double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	Robot.drivetrain.driveStraight(RobotMap.driveTalonLeft1.getPosition(), RobotMap.driveTalonRight1.getPosition(), gyroYaw, drivePID, steeringPID);	
    }

    protected boolean isFinished() {
    	return drivePID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
