package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.enums.BallDoorState;
import org.usfirst.frc.team2609.enums.VulcanClawState;
import org.usfirst.frc.team2609.enums.VulcanDeployState;
import org.usfirst.frc.team2609.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem to move the arm/door while staying inside our volume.
 */
// TODO: Set the solenoids in a better way. See: VulcanClaw.java todo comments
// TODO: !!!Add cases for neutral!!!
public class R03 extends Subsystem {
	BallDoorState currentBallDoorState;
	VulcanClawState currentVulcanClawState;
	VulcanDeployState currentVulcanDeployState;
	
   
	public void StayInsideR03(BallDoorState desiredState){
		getStates();
		if(this.currentVulcanDeployState == VulcanDeployState.DOWN && this.currentVulcanClawState == VulcanClawState.CLOSED){
			System.out.println("ARMS STICKING OUT AT THE BOTTOM, PUT THE ARM UP!");
		}else if (this.currentVulcanDeployState == VulcanDeployState.UP){
			Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(desiredState));
		}else if(this.currentVulcanDeployState == VulcanDeployState.DOWN && this.currentVulcanClawState == VulcanClawState.OPEN){
			Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(desiredState));
//			System.out.println("THE ROBOT IS IN GEAR MODE, GO INTO BALLMODE TO SET THE DOOR TO " + desiredState);
		}
		Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(desiredState));
		
	}
	public void StayInsideR03(VulcanClawState desiredState){
		getStates();
		if(this.currentVulcanClawState == desiredState){
			// if the current state matches the desired state, dont do anything
			// can setting a solenoid again help get to it's actual place? if so, uncomment next line
			//Robot.vulcanclaw.forceSetClaw(Robot.vulcanclaw.setClaw(desiredState));
			return;
		}
		if(this.currentVulcanDeployState == VulcanDeployState.DOWN && desiredState == VulcanClawState.CLOSED){
			if(this.currentBallDoorState == BallDoorState.OPEN){
				System.out.println("THE BALL DOOR IS OPEN, IN ORDER TO STAY INSIDE THE VOLUME, THE DOOR WILL BE CLOSED");
				Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(BallDoorState.CLOSED));
			}else{
				System.out.println("The ball door seems to be closed, forcing it to close anyways");
				Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(BallDoorState.CLOSED));
			}
		}
		Robot.vulcanclaw.forceSetClaw(Robot.vulcanclaw.setClaw(desiredState));
	}
	public void StayInsideR03(VulcanDeployState desiredState){
		getStates();
		// Everytime we move the arm up/down, we are going outside of our volume at the front.
		// No matter what state the door is in, close it.
		// If the door is open, print out that it needed to be closed
		if(this.currentBallDoorState == BallDoorState.OPEN){
			System.out.println("THE BALL DOOR IS OPEN, IN ORDER TO STAY INSIDE THE VOLUME THE DOOR WILL BE CLOSED!");
			Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(BallDoorState.CLOSED));
		}else if(this.currentBallDoorState == BallDoorState.CLOSED){
			System.out.println("The ball door is closed, we are free to move the arm");
			Robot.autoClaw.forceSetDoor(Robot.autoClaw.setDoor(BallDoorState.CLOSED));
		}
		Robot.vulcanclaw.forceSetDeployClaw(Robot.vulcanclaw.setClaw(desiredState));
		
	}
	public void getStates(){
		this.currentBallDoorState = Robot.autoClaw.getState();
		this.currentVulcanClawState = Robot.vulcanclaw.getClawState();
		this.currentVulcanDeployState = Robot.vulcanclaw.getDeployState();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

