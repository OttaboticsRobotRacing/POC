package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearMode extends CommandGroup {

    public VulcanGearMode() {
        addSequential(new ClawClose());
        addSequential(new ClawDown());
        addSequential(new ClawOpen());
        
    }
}
