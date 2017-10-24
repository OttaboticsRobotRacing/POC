package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearCenter extends CommandGroup {

    public GearCenter() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	addSequential(new DriveEncoder(100, 0.6, 0));
    	addSequential(new TimerDelay(1));
    	//scoring the gear
    	addSequential(new TimerDelay(1));
        addSequential(new EncReset());
    	addSequential(new DriveEncoder(-50, 0.6, 0));
        
     
    }
}
