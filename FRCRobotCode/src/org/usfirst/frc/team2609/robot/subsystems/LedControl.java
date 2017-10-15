package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LedControl extends Subsystem {
	double oldColour;
	boolean oldFlash;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLed(){
    	boolean flash = SmartDashboard.getBoolean("LED Flash: ", false);
    	double colour = SmartDashboard.getNumber("LED Colour: ", 1);
    	if(this.oldFlash != flash || this.oldColour!=colour){
        	System.out.println("NEW STATE");

	    	if (colour == 1){
				if (flash){
					RobotMap.frameLights.writeRegister(0, .35, 255,  0,  0); // on .25 second
					RobotMap.frameLights.writeRegister(1, 0.25, 0,  0,    0); // off .25 second
					RobotMap.frameLights.cycle(0, 1);
					System.out.println("Blinking Red");
			    	System.out.println("\""+flash+"\"1");

				}
				else{
					RobotMap.frameLights.cycle(0,0);
					RobotMap.frameLights.showRGB(255, 0, 0);
			    	System.out.println("\""+flash+"\"2");

				}
				Robot.leduino.SetRGB(255, 0, 0);
	    	}
			if (colour == 2){
				if (flash){
					RobotMap.frameLights.writeRegister(0, .25, 0,  255,  0); // on .25 second
					RobotMap.frameLights.writeRegister(1, 0.25, 0,  0,    0); // off .25 second
					RobotMap.frameLights.cycle(0, 1);
				}
				else{
					RobotMap.frameLights.cycle(0,0);
					RobotMap.frameLights.showRGB(0, 255, 0);
				}
				Robot.leduino.SetRGB(0, 255, 0);
				
			}
			if (colour == 3){
				if (flash){
					RobotMap.frameLights.writeRegister(0, .25, 0,  0,  255); // on .25 second
					RobotMap.frameLights.writeRegister(1, 0.25, 0,  0,    0); // off .25 second
					RobotMap.frameLights.cycle(0, 1);
				}
				else{
					RobotMap.frameLights.showRGB(0, 0, 255);
				}
				Robot.leduino.SetRGB(0, 0, 255);
	    	
			}
			if (colour == 4){
				if (flash){
					RobotMap.frameLights.writeRegister(0, .25, 255,  200,  0); // on .25 second
					RobotMap.frameLights.writeRegister(1, 0.25, 0,  0,    0); // off .25 second
					RobotMap.frameLights.cycle(0, 1);
				}
				else{
					RobotMap.frameLights.showRGB(255, 200, 0);
				}
				Robot.leduino.SetRGB(255, 0, 255);
	    	
			}
    	}

    	this.oldColour = colour;
    	this.oldFlash = flash;
    }
    
    public void flash(boolean On){
    	if (On){
    		RobotMap.frameLights.blinkLED(1);
    	}
    	else{
    		RobotMap.frameLights.blinkLED(0);
    	}
    	
    }
    
	public void toggleLED(){
    	if (RobotMap.ringLED.get() == Relay.Value.kOff){
    		RobotMap.ringLED.set(Relay.Value.kReverse);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
	public void trackLED(boolean state){
    	if (state){
    		RobotMap.ringLED.set(Relay.Value.kReverse);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
}

