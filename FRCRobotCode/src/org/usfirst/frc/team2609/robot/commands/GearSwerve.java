package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.GearPath;
import edu.wpi.first.wpilibj.command.Command;

public class GearSwerve extends Command {
	GearPath gearPath;
	
    public GearSwerve() {
    }

    protected void initialize() {
    	gearPath = new GearPath();
    	gearPath.gyro = RobotMap.ahrs.getYaw();
    	gearPath.angleToTarget = Robot.table.getNumber("angleToTarget", 0);
    	gearPath.distanceToTarget = Robot.table.getNumber("distanceToTarget",0); // Get the distance to target from networktables
    	gearPath.calc();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	return true;//(this.gearPath.angleToDrive!=0 && this.gearPath.distanceToDrive!=0);
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
