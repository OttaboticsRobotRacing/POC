package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearScore extends CommandGroup {

    public VulcanGearScore() {
        addSequential(new ClawScore());
        
    }
}
