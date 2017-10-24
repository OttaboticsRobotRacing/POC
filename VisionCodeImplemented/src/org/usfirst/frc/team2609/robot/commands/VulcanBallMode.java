package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanBallMode extends CommandGroup {

    public VulcanBallMode() {
        addSequential(new ClawClose());
        addSequential(new ClawUp());
        addSequential(new ClawOpen());
        
    }
}
