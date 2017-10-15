package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpenAuton;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleLeft;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderReverse;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPRedReverse extends CommandGroup {
	protected void initialize(){
	}
	
    public RightPRedReverse() {
    	double point1 = 30;
    	double point2 = 50;
    	double point3 = 135;
    	double totalArc = 145;
    	
    	double heading1 = -60;
    	double heading2 = 0;
    	double heading3 = -45;
    	double heading4 = -45;
    	//left gear and ? auton with dashboard variables
    	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-75.5,1.0,0));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-75,1.0,-60));
    	addSequential(new autoClawOpen());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(65,1.0,60));
    	addSequential(new GyroTurn(1,0));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-150,1.0,0));
//    	addParallel(new DriveEncoderCurveSimpleLeft(totalArc, 0.6, 0.4, heading1, heading2, heading3, heading4, point1, point2, point3));
//    	addParallel(new BallDoorOpenAuton(point3));
//    	addParallel(new BallLowGoalAuton(point3));
    	
    }
}
