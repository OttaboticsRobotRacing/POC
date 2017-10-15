package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.RingLED;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.autoClawClose;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto1 extends CommandGroup {
	double autonDistance1 = 0;
	double autonDistance2 = 0;
	double autonDistance3 = 0;
	double autonHeading1 = 0;
	double autonHeading2 = 0;
	double autonHeading3 = 0;
	double autonAngle1 = 0;
	double autonAngle2 = 0;
	double autonAngle3 = 0;
	protected void initialize(){

	}
	
    public Auto1() {
		autonDistance1 = (double)SmartDashboard.getNumber("auton distance 1: ",0);
		autonDistance2 = (double)SmartDashboard.getNumber("auton distance 2: ",0);
		autonDistance3 = (double)SmartDashboard.getNumber("auton distance 3: ",0);
		autonHeading1 = (double)SmartDashboard.getNumber("auton heading 1: ",0);
		autonHeading2 = (double)SmartDashboard.getNumber("auton heading 2: ",0);
		autonHeading3 = (double)SmartDashboard.getNumber("auton heading 3: ",0);
		autonAngle1 = (double)SmartDashboard.getNumber("auton angle 1: ",0);
		autonAngle2 = (double)SmartDashboard.getNumber("auton angle 2: ",0);
		autonAngle3 = (double)SmartDashboard.getNumber("auton angle 3: ",0);

		addSequential(new TimerDelay(2));
		addSequential(new autoClawClose());
		addSequential(new TimerDelay(2));
		addSequential(new autoClawOpen());
    	
    	
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	//addSequential(new CameraPointTurn(0.3));
    	/*
    	addSequential(new RingLED(true));
    	addSequential(new TimerDelay(0.5));
        addSequential(new GearLeft());
        addSequential(new RingLED(false));*/
    	/*
    	addSequential(new DriveEncoder(-6.8, 0.3, 0));
    	addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(3, 0.3, 0));*/
    	
    	//left gear auton with dashboard variables
//    	addSequential(new DriveEncoder(autonDistance1,0.8,autonHeading1));
//    	addSequential(new EncReset());
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new GyroTurn(1,autonAngle1));
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(autonDistance2,0.8,autonHeading2));
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new ClawOpen());
//    	addSequential(new DriveEncoder(autonDistance3,0.8,autonHeading3)); 	
    	/*
    	addSequential(new DriveEncoder(58.0,0.8,0.0));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new GyroTurn(1,30));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(80.0,0.8,30.0));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new ClawOpen());
    	addSequential(new DriveEncoder(-40.0,0.8,30.0));
    	*/
    	
//    	//right gear auton with dashboard variables	
//    	addSequential(new DriveEncoder(80,1,0.0));
//    	addSequential(new EncReset());
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new GyroTurn(1,-60));
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(45,1,-60));
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new ClawOpen());
//    	addSequential(new EncReset());
//    	//addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(-50,1,-60)); 	
//    	addSequential(new DriveEncoder(80.0,0.5,0.0));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new GyroTurn(1,-60));
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(45.0,0.5,-60.0));
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new ClawOpen());
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(-50.0,0.5,-60.0));

    	//right gear auton with vision    	
//    	addSequential(new DriveEncoder(80.0,0.5,0.0));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new GyroTurn(1,-60));
//    	addSequential(new TimerDelay(0.2));   	
    	/*
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	*/
    }
}
