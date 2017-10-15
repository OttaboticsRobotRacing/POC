package org.usfirst.frc.team2609.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class DigitalSensor extends Trigger {
	DigitalInput _input;
	public DigitalSensor(int DigitalInputID){
		_input = new DigitalInput(DigitalInputID);
	}
	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return _input.get();
	}

}
