package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraPivotTurn extends Command {
	private SimPID cameraPID;
	private SimPID pivotPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	double CameraP = 0;
	double CameraI = 0;
	double CameraD = 0;
	double CameraMax = 0;
	double CameraEps = 0;
	double maxPower = 0;
	double centerXlocal;
	double errorX;
	
    public CameraPivotTurn(double maxPower) {
    	this.cameraPID = new SimPID();
        this.pivotPID = new SimPID();
    }
 
    protected void initialize() {
        CameraP = (double)SmartDashboard.getNumber("camera P: ");
        CameraI = (double)SmartDashboard.getNumber("camera I: ");
        CameraD = (double)SmartDashboard.getNumber("camera D: ");
        CameraMax = (double)SmartDashboard.getNumber("camera Max: ");
        CameraEps = (double)SmartDashboard.getNumber("camera Eps: ");        
        this.pivotPID.setDesiredValue(0);
        this.pivotPID.setConstants(0.01, 0, 0);
        this.pivotPID.setMaxOutput(1);
    	this.cameraPID.resetPreviousVal();
        this.cameraPID.setConstants(CameraP, CameraI, CameraD);
        this.cameraPID.setMaxOutput(CameraMax);
        this.cameraPID.setErrorEpsilon(CameraEps);
        this.cameraPID.setDoneRange(1);
        this.cameraPID.setDesiredValue(160);
        this.cameraPID.setMinDoneCycles(20);
    }

    protected void execute() {
    	this.centerXlocal = Robot.table.getNumber("centerX",0);
        //errorX = (centerXlocal - 320)*(0.084375); //why can't java divide
    	Robot.drivetrain.cameraPivotTurn(cameraPID, pivotPID, centerXlocal);
    	System.out.println(centerXlocal);
    	Timer.delay(SmartDashboard.getNumber("camera Delay: "));
    }

    protected boolean isFinished() {
    	System.out.println("CameraPID.isDone "+ cameraPID.isDone());
    	return cameraPID.isDone();
    }

    protected void end() {
    	System.out.println("CameraPID.isDone "+ cameraPID.isDone());
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
