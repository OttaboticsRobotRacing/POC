package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearGrab extends CommandGroup {

    public VulcanGearGrab() {
        addSequential(new ClawClose());
        addSequential(new ClawUp());
        
    }
}
