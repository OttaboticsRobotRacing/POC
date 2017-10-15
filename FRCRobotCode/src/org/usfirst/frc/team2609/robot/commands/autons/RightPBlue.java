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

public class RightPBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public RightPBlue() {
    	double point1 = -10;
    	double point2 = -120;
    	double point3 = -120;
    	double totalArc = -125;
    	
    	double heading1 = -60;
    	double heading2 = -42;
    	double heading3 = -42;
    	double heading4 = -42;
    	
    	//right gear and balls auton with dashboard variables	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(75,1.0,0));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(70,1.0,-60));
    	addSequential(new VulcanGearScore());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-65,1.0,60));
    	addSequential(new GyroTurn(1,0));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(150,1.0,0));
//    	addSequential(new EncReset());
//    	addParallel(new DriveEncoderCurveSimple(totalArc, 0.8, 0.2, heading1, heading2, heading3, heading4, point1, point2, point3));
////    	addParallel(new DriveEncoderCurveSimple(-125, 0.8, 0.2, -60, -42, -42, -42, -10, -120, -120));
//    	addParallel(new BallDoorOpen());
//    	addParallel(new BallLowGoalAuton(point3));
    	
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(-20,1.0,-60));
//    	addSequential(new GyroTurn(1,-42));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addParallel(new DriveEncoder(-100,1.0,-42));

    }
}
