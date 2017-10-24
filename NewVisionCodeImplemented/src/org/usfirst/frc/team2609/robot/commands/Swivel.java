package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Swivel extends CommandGroup {
    
    public  Swivel() {

    	addSequential(new DriveEncoder(980, 0.6, 0));
    	addSequential(new EncReset());
    	addSequential(new GyroTurn(0.3,-90));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(395, 0.6, -90));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(5));
    	addSequential(new DriveEncoder(364, 0.6, -90));
    	addSequential(new EncReset());
    	addSequential(new GyroTurn(0.3,0));
    	addSequential(new EncReset());
    	

    	
    }
}
