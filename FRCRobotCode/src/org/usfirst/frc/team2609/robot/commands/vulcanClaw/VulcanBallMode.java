package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.gearRollerSetSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanBallMode extends CommandGroup {

    public VulcanBallMode() {
    	addSequential(new GearPushIn());
    	addSequential(new TimerDelay(0.1));
        addSequential(new ClawClose());
        addSequential(new TimerDelay(0.3));
        addSequential(new ClawUp());
        addSequential(new gearRollerSetSpeed(0.8));
        
    }
}
