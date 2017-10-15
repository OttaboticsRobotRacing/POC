# import the necessary packages
from __future__ import print_function
from pivideostream import PiVideoStream
from imutils.video import FPS
from picamera.array import PiRGBArray
from picamera import PiCamera
import argparse
import imutils
import time, math, numpy
import cv2
from networktables import NetworkTable
NetworkTable.setIPAddress("roborio-2609-frc.local")#Change the address to your own
NetworkTable.setClientMode()
NetworkTable.initialize()
sd = NetworkTable.getTable("RaspberryPi")

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-n", "--num-frames", type=int, default=100,
	help="# of frames to loop over for FPS test")
ap.add_argument("-d", "--display", type=int, default=-1,
	help="Whether or not frames should be displayed")
args = vars(ap.parse_args())
# initialize the camera and stream
camera = PiCamera()
camera.resolution = (640, 480)
camera.framerate = 32
rawCapture = PiRGBArray(camera, size=(640, 480))
stream = camera.capture_continuous(rawCapture, format="bgr", use_video_port=True)
# allow the camera to warmup and start the FPS counter
print("[INFO] sampling frames from `picamera` module...")
time.sleep(2.0)
fps = FPS().start()
# loop over some frames
for (i, f) in enumerate(stream):
	# grab the frame from the stream and resize it to have a maximum
	# width of 640 pixels
	frame = f.array
	frame = imutils.resize(frame, width=640)
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
        #Get values from the Networktable to use
        H_L = sd.getNumber('H_L',30) #H lower
        H_U = sd.getNumber('H_U',90) #H upper
        S_L = sd.getNumber('S_L',120) #S lower
        S_U = sd.getNumber('S_U',255) #S upper
        V_L = sd.getNumber('V_L',120) #V lower
        V_U = sd.getNumber('V_U',255) #V upper
        #display = sd.getNumber("display",1) #Default to Off(0) for display of window
        #display = 1
        lower_green = numpy.array([H_L,S_L,V_L]) #Set array of lower HSV limits
        upper_green = numpy.array([H_U,S_U,V_U]) #Set array of upper HSV limits
        image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits
        cnts = cv2.findContours(image_hsv.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]# Find contours
        if len(cnts) > 0:# only proceed if at least one contour was found
                c = max(cnts, key=cv2.contourArea)
                ((x,y),radius) = cv2.minEnclosingCircle(c)
                M = cv2.moments(c)
                if (M["m00"]!=0): #Catch a div/0 error!
                        center = (int(M["m10"] /(M["m00"])), int(M["m01"] / (M["m00"])))
                else:
                        center = (int(M["m10"] /1), int(M["m01"] / 1))
                if radius > 25: #Only if radius meets a min size
                        centerX = center[0]
                        centerY = center[1]
                        angleToTarget = math.atan((centerX-320)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
                        cv2.circle(frame,(int(x),int(y)),int(radius),(0,255,255),2)
                        cv2.circle(frame,center,5,(0,255,255),-1)    
                else: # if no target is large enough
                        centerX = -1 # return -1  for centerX (check if it is -1 in robot code)
                        centerY = -1 # return -1  for centerY (check if it is -1 in robot code)
                        angleToTarget = 0 # return 0 degrees as angle to target (assuming we are on target we just can't see it)
        else: # if no contours are found
                centerX = -1 # return -1  for centerX (check if it is -1 in robot code)
                centerY = -1 # return -1  for centerY (check if it is -1 in robot code)
                angleToTarget = 0 # return 0 degrees as angle to target (assuming we are on target we just can't see it)
        # show the frame and other images, output values to networktable
        #cv2.imshow("Frame", frame) #Display a screen with outputs
        #key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a 'screen'
        sd.putNumber('centerX', centerX) #Put out centerX in pixels
        sd.putNumber('centerY', centerY) #Put out centerY in pixels
        sd.putNumber('angleToTarget', angleToTarget) #Put out angle to target in degrees
	# check to see if the frame should be displayed to our screen
	if args["display"] > 0:
		cv2.imshow("Frame", frame)
		key = cv2.waitKey(1) & 0xFF
	# clear the stream in preparation for the next frame and update
	# the FPS counter
	rawCapture.truncate(0)
	fps.update()
	# check to see if the desired number of frames have been reached
	if i == args["num_frames"]:
		break
# stop the timer and display FPS information
fps.stop()
print("[INFO] elasped time: {:.2f}".format(fps.elapsed()))
print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))
# do a bit of cleanup
cv2.destroyAllWindows()
stream.close()
rawCapture.close()
camera.close()

# created a *threaded *video stream, allow the camera sensor to warmup,
# and start the FPS counter
print("[INFO] sampling THREADED frames from `picamera` module...")
vs = PiVideoStream().start()
time.sleep(2.0)
fps = FPS().start()
# loop over some frames...this time using the threaded stream
while fps._numFrames < args["num_frames"]:
	# grab the frame from the threaded video stream and resize it
	# to have a maximum width of 400 pixels
	frame = vs.read()
        frame = imutils.resize(frame, width=640)
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
        #Get values from the Networktable to use
        H_L = sd.getNumber('H_L',30) #H lower
        H_U = sd.getNumber('H_U',90) #H upper
        S_L = sd.getNumber('S_L',120) #S lower
        S_U = sd.getNumber('S_U',255) #S upper
        V_L = sd.getNumber('V_L',120) #V lower
        V_U = sd.getNumber('V_U',255) #V upper
        #display = sd.getNumber("display",1) #Default to Off(0) for display of window
        #display = 1
        lower_green = numpy.array([H_L,S_L,V_L]) #Set array of lower HSV limits
        upper_green = numpy.array([H_U,S_U,V_U]) #Set array of upper HSV limits
        image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits
        cnts = cv2.findContours(image_hsv.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]# Find contours
        if len(cnts) > 0:# only proceed if at least one contour was found
                c = max(cnts, key=cv2.contourArea)
                ((x,y),radius) = cv2.minEnclosingCircle(c)
                M = cv2.moments(c)
                if (M["m00"]!=0): #Catch a div/0 error!
                        center = (int(M["m10"] /(M["m00"])), int(M["m01"] / (M["m00"])))
                else:
                        center = (int(M["m10"] /1), int(M["m01"] / 1))
                if radius > 25: #Only if radius meets a min size
                        centerX = center[0]
                        centerY = center[1]
                        angleToTarget = math.atan((centerX-320)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
                        cv2.circle(frame,(int(x),int(y)),int(radius),(0,255,255),2)
                        cv2.circle(frame,center,5,(0,255,255),-1)    
                else: # if no target is large enough
                        centerX = -1 # return -1  for centerX (check if it is -1 in robot code)
                        centerY = -1 # return -1  for centerY (check if it is -1 in robot code)
                        angleToTarget = 0 # return 0 degrees as angle to target (assuming we are on target we just can't see it)
        else: # if no contours are found
                centerX = -1 # return -1  for centerX (check if it is -1 in robot code)
                centerY = -1 # return -1  for centerY (check if it is -1 in robot code)
                angleToTarget = 0 # return 0 degrees as angle to target (assuming we are on target we just can't see it)
        # show the frame and other images, output values to networktable
        #cv2.imshow("Frame", frame) #Display a screen with outputs
        #key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a 'screen'
        sd.putNumber('centerX', centerX) #Put out centerX in pixels
        sd.putNumber('centerY', centerY) #Put out centerY in pixels
        sd.putNumber('angleToTarget', angleToTarget) #Put out angle to target in degrees
	# check to see if the frame should be displayed to our screen
	if args["display"] > 0:
		cv2.imshow("Frame", frame)
		key = cv2.waitKey(1) & 0xFF
	# update the FPS counter
	fps.update()
# stop the timer and display FPS information
fps.stop()
print("[INFO] elasped time: {:.2f}".format(fps.elapsed()))
print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))

# do a bit of cleanup
cv2.destroyAllWindows()
vs.stop()
