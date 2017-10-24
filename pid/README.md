This is a POC for a PID controller.

The final controller for the robot should accept two input values: the distance to the left lane and the distance to the right lane. The output will be a steering angle (negative for left and positive for right). The controller will try and keep the vehicle in the center of the lane.

PID output = (k_p * p) + (k_i * i) + (k_d * d)
