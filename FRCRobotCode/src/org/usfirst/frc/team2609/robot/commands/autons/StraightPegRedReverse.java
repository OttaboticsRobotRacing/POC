package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpenAuton;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleLeft;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraightPegRedReverse extends CommandGroup {

	protected void initialize(){
	}
	
    public StraightPegRedReverse() {

    	/*
		addSequential(new DriveEncoder(-6.8, 0.3, 0));
    	addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(3, 0.3, 0));
    	*/
    	
    	double point1 = -25;
    	double point2 = -173;
    	double point3 = -195;
    	double totalArc = -200;
    	
    	double heading1 = 0;
    	double heading2 = -90; //inverted after first match
    	double heading3 = -45;
    	double heading4 = -45;
    	
    	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-85,1.0,0));
    	addSequential(new autoClawOpen());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(80,1.0,0));
//    	addParallel(new DriveEncoderCurveSimpleLeft(totalArc, 0.6, 0.4, heading1,
//    			heading2, heading3, heading4, point1, point2, point3));
//    	addParallel(new BallDoorOpenAuton(point3));
//    	addParallel(new BallLowGoalAuton(point3));
    }
}
