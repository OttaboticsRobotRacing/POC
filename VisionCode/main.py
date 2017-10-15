<<<<<<< HEAD
# import the necessary packages
from pivideostream import PiVideoStream
from WebcamVideoStream import WebcamVideoStream
import time, cv2, numpy, imutils, math, argparse
from networktables import NetworkTable
from utils import *
import utils
from cv2 import countNonZero

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-p", "--picamera",
	help="whether or not the Raspberry Pi camera should be used")
args = vars(ap.parse_args())

kernel = numpy.ones((5,5), numpy.uint8)
#frameNum = 0 # TODO: find out how to determine unique frames
loops = 0
key = 0
centerX = 0
centerY = 0
angleToTarget = 0
#utils.hsvWrite(30,90,120,255,120,255) #Write Networktable values Green
utils.hsvWrite(80,120,80,120,190,255) #Write Networktable values Blue
#utils.hsvWrite(130,120,80,200,190,255) #Write Networktable values Red
if (args["picamera"] > 0):
    cap = PiVideoStream().start()
else:
    cap = WebcamVideoStream().start()
time.sleep(2.0)
target = -1
while True:
    image = cap.read() #Capture frame
    imageCopy = image
##    if loops == 0:
##        oldimageCopy = imageCopy
    image = imutils.resize(image, width=320) #resize - needed to allow rest of toolpath to work
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
    (lower_green,upper_green,display) = utils.hsvRead() #Get lower and Upper HSV values from the Networktable to use
    image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits
    cnts = cv2.findContours(image_hsv.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]# Find contours
##    difference = cv2.subtract(imageCopy, oldimageCopy)    
##    result = not numpy.any(difference)
##    if result is True:
##        print "Pictures are the same"
##    else:
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
            angleToTarget = math.atan((centerX-160)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
            target = 1
            if display != 0: #Draw bounding circle and center dot if display turned on
                cv2.circle(image,(int(x),int(y)),int(radius),(0,255,255),2)
                cv2.circle(image,center,5,(0,255,255),-1)
        else: # if no target is large enough
            target = -1 # return -1  for target
    else: # if no contours are found
        target = -1 # return -1  for target
    # show the frame and other images, output values to networktable
    if display != 0: #Draw display if turned on 
        cv2.imshow("Frame", image) #Display a screen with outputs        key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a display
##    oldimageCopy=imageCopy
    utils.targetWrite(target,centerX,centerY,angleToTarget,loops)
    if key == ord("q"):# if the `q` key was pressed, break from the loop
            break
    loops += 1
    
cv2.destroyAllWindows()
cap.stop()
=======
# import the necessary packages
from pivideostream import PiVideoStream
from WebcamVideoStream import WebcamVideoStream
import time, cv2, imutils, math, argparse
from shapedetector import ShapeDetector
from utils import *
import utils
import numpy as np
from numpy.lib.utils import source
#from cv2 import countNonZero

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-p", "--picamera",
	help="whether or not the Raspberry Pi camera should be used")
args = vars(ap.parse_args())

OFFSET_TO_FRONT = 0
CAMERA_WIDTH = 320
WIDTH_BETWEEN_TARGET = 4.25
shouldRun = True
# loops = 0
# key = 0
# centerX = 0
# centerY = 0
# angleToTarget = 0
lengthBetweenContours = -1
distanceFromTarget = -1
lengthError = -1
centerX = -1
utils.hsvWrite(30,90,120,255,120,255) #Write Networktable values Green
#utils.hsvWrite(80,120,80,120,190,255) #Write Networktable values Blue
#utils.hsvWrite(130,120,80,200,190,255) #Write Networktable values Red

def processImage(source):
	print("Processing Started")
	tracker.process(source)
	returnCenterX()
	#print(getAngle())
	table.putDouble("distanceFromTarget", distanceFromTarget())
	table.putDouble("angleFromGoal", getAngle())
	table.putNumberArray("centerX", tracker.centerX)
	videoCapture.read(matOriginal)

time.sleep(2.0)
target = -1
centerX = 0
centerY = 0
r1x1 = -1
r1x2 = -1
r2x1 = -1
r2x2 = -1
while True:
	while(shouldRun):
		if (args["picamera"] > 0):
			cap = PiVideoStream().start()
		else:
			cap = WebcamVideoStream().start()
			
		while(cap.stopped):
			print("camera not open")
		while(cap.start):
			processImage()
			
			try {
//				opens up the camera stream and tries to load it
				videoCapture = new VideoCapture();
				tracker = new LiftTracker();
				videoCapture.open("http://roborio-1806-frc.local:1181/?action=stream");
				// change that to your team number boi("http://roborio-XXXX-frc.local:1181/?action=stream");
				while(!videoCapture.isOpened()){
					System.out.println("Didn't open Camera, restart jar");
				}
//				time to actually process the acquired images
				while(videoCapture.isOpened()){
					processImage();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
//		make sure the java process quits when the loop finishes
		videoCapture.release();
		System.exit(0);
	

    imageCopy = image

    image = imutils.resize(image, width=320) #resize - needed to allow rest of toolpath to work
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
    (lower_green,upper_green,display) = utils.hsvRead() #Get lower and Upper HSV values from the Networktable to use
    image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits
    blue_hsv = cv2.inRange(hsv,[80,120,80],[120,190,255])
    # subtract Blue from image_hsv to eliminate non green light from the path?
    image_hsv = image_hsv - blue_hsv

    # blur it slightly and threshold it
    blurred = cv2.GaussianBlur(image_hsv, (5, 5), 0)
    thresh = cv2.threshold(blurred, 60, 255, cv2.THRESH_BINARY)[1]

    # find contours in the thresholded image and initialize the shape detector
    cnts = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    cnts = cnts[0] if imutils.is_cv2() else cnts[1]
    sd = ShapeDetector()
    detectCenter=0
    detectArray=[]
    if len(cnts) > 0:# only proceed if at least one contour was found
        for c in cnts:
            if cv2.contourArea(c) < 50:
                continue
            # compute the center of the contour, then detect the name of the
            # shape using only the contour
            M = cv2.moments(c)
                               
            shape = "Target" #sd.detect(c)
            if (M["m00"]!=0): #Catch a div/0 error!
                cX = int(M["m10"] / M["m00"])
                cY = int(M["m01"] / M["m00"])
            else:
                cX = int(M["m10"] / 1)
                cY = int(M["m01"] / 1)
            
            if shape == "Target":
                #peri = cv2.arcLength(c, True)
                #approx = cv2.approxPolyDP(c, 0.1 * peri, True)
                (x, y, w, h) = cv2.boundingRect(c)
                ar = float(w) / float(h)
                shape = "Target" if ar >= 0.2 and ar <= 0.7 else "rejected"
                if shape =="rejected":
                    print("ar rejected")
                    continue
                elif cv2.contourArea(c)/(w*h)<.5:
                    print("contour area rejected")
                    continue
                elif cY < 80 or cY > 160:
                    print("target not right height ")+str(cY)
                    continue
                if r1x1 == -1:
                    r1x1=x
                    r1x2=x+w
                    print("target 1")
                elif r2x1 == -1:
                    r2x1=x
                    r2x2=x+w
                    print("target 2")
                else:
                    # Run away!
                    print("run away")
                centerX = (min(r1x1,r2x1)+max(r1x2,r2x2))/2
                centerY = cY
##                detectArray.append(c)
##                print(detectArray)
                cv2.drawContours(image, [c], -1, (255, 0, 0), 2)
##                centerX = (centerX + cX)/2
##                centerY = (centerY + cY)/2
                detectCenter=detectCenter+1
            else:
                cv2.drawContours(image, [c], -1, (0, 255, 0), 2)
            # multiply the contour (x, y)-coordinates by the resize ratio,
            # then draw the contours and the name of the shape on the image
            if detectCenter == 2:
                angleToTarget = math.atan((centerX-160)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
                center = (centerX,centerY)
                cv2.circle(image,(int(centerX),int(centerY)),int(abs(cX-centerX)),(0,255,255),2)
                cv2.circle(image,center,5,(0,255,255),-1)
            cv2.putText(image, shape+" "+str(cv2.contourArea(c)), (cX, cY), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
    utils.targetWrite(target,centerX,centerY,angleToTarget,loops)
    if display != 0: #Draw display if turned on 
        cv2.imshow("Frame", image) #Display a screen with outputs
        cv2.imshow("HSV,Blur,Thresh", thresh) #Display a screen with outputs        
        key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a display
    if key == ord("q"):# if the `q` key was pressed, break from the loop
        break
    r1x1 = -1
    r1x2 = -1
    r2x1 = -1
    r2x2 = -1    

##        if radius > 25: #Only if radius meets a min size
##            centerX = center[0]
##            centerY = center[1]
##            angleToTarget = math.atan((centerX-160)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
##            target = 1
##            if display != 0: #Draw bounding circle and center dot if display turned on
##                cv2.circle(image,(int(x),int(y)),int(radius),(0,255,255),2)
##                cv2.circle(image,center,5,(0,255,255),-1)
##        else: # if no target is large enough
##            target = -1 # return -1  for target
##    else: # if no contours are found
##        target = -1 # return -1  for target
##    # show the frame and other images, output values to networktable
##    if display != 0: #Draw display if turned on 
##        cv2.imshow("Frame", image) #Display a screen with outputs
##        key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a display
####    oldimageCopy=imageCopy
##    utils.targetWrite(target,centerX,centerY,angleToTarget,loops)
##    if key == ord("q"):# if the `q` key was pressed, break from the loop
##            break
##    loops += 1
    
cv2.destroyAllWindows()
cap.stop()
>>>>>>> 21258d75c632b68317105aa06fc52244ea074669
