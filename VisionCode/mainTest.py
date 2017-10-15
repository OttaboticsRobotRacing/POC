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
#utils.hsvWrite(80,120,80,120,190,255) #Write Networktable values Blue
utils.hsvWrite(130,120,80,200,190,255) #Write Networktable values Red
if (args["picamera"] > 0):
    cap = PiVideoStream().start()
else:
    cap = WebcamVideoStream().start()
time.sleep(2.0)
target = -1
font = cv2.FONT_HERSHEY_SIMPLEX
while True:
    image = cap.read() #Capture frame
    image = imutils.resize(image, width=320) #resize - needed to allow rest of toolpath to work
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
    (lower_green,upper_green,display) = utils.hsvRead() #Get lower and Upper HSV values from the Networktable to use
    image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits

    center = (160, 120)

    centerX = 160
    centerY = 120
    angleToTarget = 0 #angleToTarget returns angle to target in degrees
    target = 1
    roi = hsv[centerX,centerY]
    #print (roi)
    roi = ("H"+str(roi[0])+" S"+str(roi[1])+" V"+str(roi[2]))
    #print (roi)
    cv2.putText(image,roi, (10,20),font, 1,(255,255,255),2) #hsv[centerX,centerY]
    cv2.circle(image,(160,120),20,(0,255,255),2)
    cv2.circle(image,center,5,(0,255,255),-1)

    cv2.imshow("Frame", image) #Display a screen with outputs
    key = cv2.waitKey(1) & 0xFF #Wait for keypress

    utils.targetWrite(target,centerX,centerY,angleToTarget,loops)
    if key == ord("q"):# if the `q` key was pressed, break from the loop
            break
    loops += 1
    
cv2.destroyAllWindows()
cap.stop()
