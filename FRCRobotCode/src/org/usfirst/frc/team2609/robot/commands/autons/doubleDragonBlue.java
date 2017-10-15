package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpenAuton;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleLeft;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleReverseRight;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.GyroTurnReverse;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.autoClawClose;
import org.usfirst.frc.team2609.robot.commands.gearRollerSetSpeed;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearMode;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class doubleDragonBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public doubleDragonBlue() {
    	double point1 = 15;
    	double point2 = 125;
    	double point3 = 125;
    	double totalArc = 125;
    	
    	double heading1 = 0;
    	double heading2 = 65;
    	double heading3 = 65;
    	double heading4 = 65;
    	
    	double point1a = 55;
    	double point2a = 125;
    	double point3a = 125;
    	double totalArca = 125;
    	
    	double heading1a = -65;
    	double heading2a = 0;
    	double heading3a = 0;
    	double heading4a = 0;
    	
//    	addSequential(new autoClawClose());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-85,1.0,0));
    	addSequential(new autoClawOpen());
    	addSequential(new EncReset());
    	addParallel(new VulcanGearMode());
    	addParallel(new gearRollerSetSpeed(0.8));
    	addSequential(new DriveEncoderCurveSimpleLeft(totalArc, 0.6, 0.4, heading1, heading2, heading3, heading4, point1, point2, point3));
    	addSequential(new autoClawClose());
    	addSequential(new gearRollerSetSpeed(0));
    	addSequential(new GyroTurnReverse(1,-65));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoderCurveSimpleReverseRight(totalArca, 0.6, 0.4, heading1a, heading2a, heading3a, heading4a, point1a, point2a, point3a));
    	addSequential(new VulcanGearScore());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-40,1.0,0));
    }
}
