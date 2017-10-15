package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LEDuino extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	I2C _arduino;
	public LEDuino(int address){
		_arduino = new I2C(I2C.Port.kMXP, address);
	}
	public void SetRGB(double Red, double Green, double Blue){
		byte[] data = new byte[3];
		data[0] = (byte)(int)Red;
		data[1] = (byte)(int)Green;
		data[2] = (byte)(int)Blue;
		this._arduino.writeBulk(data);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

