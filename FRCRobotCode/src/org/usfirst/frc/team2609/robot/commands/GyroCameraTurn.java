package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroCameraTurn extends Command {
	private SimPID cameraPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	double gyroCameraP = 0;
	double gyroCameraI = 0;
	double gyroCameraD = 0;
	double gyroCameraMax = 0;
	double gyroCameraEps = 0;
	double maxPower = 0;
	double angleToTarget;
	double errorX;
	double OldFrameNum;
	int endCounter = 0;
	
    public GyroCameraTurn(double maxPower) {
        gyroCameraP = (double)SmartDashboard.getNumber("turn P: ", 0);
        gyroCameraI = (double)SmartDashboard.getNumber("turn I: ", 0);
        gyroCameraD = (double)SmartDashboard.getNumber("turn D: ", 0);
        gyroCameraMax = (double)SmartDashboard.getNumber("turn Max: ", 0);
        gyroCameraEps = (double)SmartDashboard.getNumber("turn Eps: ", 0);
        System.out.println("gyroCameraTURN CLASS INITED");
    	this.cameraPID = new SimPID();

        this.cameraPID.setMaxOutput(gyroCameraMax);
        this.cameraPID.setDoneRange(1);
    }
 
    protected void initialize() {
    	Robot.LedControl.trackLED(true);
    	cameraPID.resetPreviousVal();
        this.cameraPID.setConstants(gyroCameraP, gyroCameraI, gyroCameraD);
        this.cameraPID.setErrorEpsilon(gyroCameraEps);
    	this.angleToTarget = Robot.table.getNumber("angleToTarget",0); //*(180/Math.PI)
        this.cameraPID.setDesiredValue(RobotMap.ahrs.getYaw()+angleToTarget);
        this.OldFrameNum = Robot.table.getNumber("piLoops", 0);
    }

    protected void execute() {
    	Robot.drivetrain.gyroCameraTurn(cameraPID, cameraPID);
    	System.out.println(angleToTarget);
    }

    protected boolean isFinished() {
    	boolean isDone = cameraPID.isDone();
    	System.out.println("CameraPID.isDone NOT REALLY"+ isDone);
    	//return cameraPID.isDone();
    	if(isDone){
    		System.out.println("CameraPID.isDone REALLY "+ isDone);
    		System.out.println("endCounter "+ endCounter);
    		endCounter++; // DELETE THIS ONCE THE FRAME COUNTER IS WORKING!!!
    		if(OldFrameNum != Robot.table.getNumber("piLoops" , -1)){
    			endCounter++;
            	this.angleToTarget = Robot.table.getNumber("angleToTarget",0); //*(180/Math.PI)
            	this.cameraPID.resetPreviousVal();
                this.cameraPID.setDesiredValue(RobotMap.ahrs.getYaw()+angleToTarget);
    		}
            if(endCounter >= 5){
            	return true;
            }
    	}
    	else{
    		endCounter=0;
    	}
    	this.OldFrameNum = Robot.table.getNumber("piLoops", -1);
    	return false;
    }

    protected void end() {
    	System.out.println("CameraPID.isDone "+ cameraPID.isDone());
//    	Robot.LedControl.trackLED(false);
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
