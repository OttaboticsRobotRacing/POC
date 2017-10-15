package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleLeft;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPRed extends CommandGroup {
	protected void initialize(){
	}
	
    public LeftPRed() {
    	double point1 = -20;
    	double point2 = -128;
    	double point3 = -128;
    	double totalArc = -135;
    	
    	double heading1 = 60;
    	double heading2 = 42;
    	double heading3 = 42;
    	double heading4 = 42;
		// Curves
    	//left gear auton with dashboard variables,		123 from centre to edge of robot
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(75,1.0,0));
    	addSequential(new GyroTurn(1,60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(70,1.0,60));
    	addSequential(new VulcanGearScore());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-65,1.0,60));
    	addSequential(new GyroTurn(1,0));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(150,1.0,0));
//    	addSequential(new EncReset());
//    	addParallel(new DriveEncoderCurveSimple(totalArc, 0.8, 0.2, heading1, heading2, heading3, heading4, point1, point2, point3));
////    	addParallel(new DriveEncoderCurveSimple(-135, 0.8,0.2, 60, 42, 42, 42, -20, -128, -128));
//    	addParallel(new BallDoorOpen());
//    	addParallel(new BallLowGoalAuton(point3));
    	
    	
    	
    }
}
