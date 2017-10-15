package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.*;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearMode extends CommandGroup {

    public VulcanGearMode() {
    	addSequential(new GearPushIn());
    	addSequential(new TimerDelay(0.1));
        addSequential(new ClawClose());
        addSequential(new ClawDown());
        addSequential(new TimerDelay(0.1)); //without this the claw doesnt open again
        addSequential(new ClawOpen());
        addSequential(new gearRollerSetSpeed(0.8));
        
    }
}
