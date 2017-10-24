package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearLeft extends CommandGroup {

    public GearLeft() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
     	addSequential(new DriveEncoder(70, 0.6, 0));
    	addSequential(new TimerDelay(1));
    	addSequential(new GyroTurn(0.3,60));
    	addSequential(new TimerDelay(1));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(80, 0.6, 60));
    	addSequential(new TimerDelay(1));
    	//scoring the gear
    	addSequential(new TimerDelay(1));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(50, 0.6,0));
    	addSequential(new TimerDelay(1));
    	addSequential(new GyroTurn(0.3,0));
    	addSequential(new TimerDelay(1));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(50, 0.6, 0));    
    }
}
