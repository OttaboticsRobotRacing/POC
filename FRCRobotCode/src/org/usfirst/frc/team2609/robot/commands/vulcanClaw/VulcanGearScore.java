package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.TimerDelay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearScore extends CommandGroup {

    public VulcanGearScore() {
        addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.1));
    	addSequential(new GearPushOut());
    	addSequential(new TimerDelay(0.5));
    	addSequential(new GearPushIn());
        
    }
}
