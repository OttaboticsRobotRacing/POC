# import the necessary packages
from pivideostream import PiVideoStream
from WebcamVideoStream import WebcamVideoStream
import time, cv2, numpy, imutils, math, argparse
from networktables import NetworkTable
from shapedetector import ShapeDetector
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
display = 0
utils.hsvWrite(30,90,120,255,120,255) #Write Networktable values Green
#utils.hsvWrite(80,120,80,120,190,255) #Write Networktable values Blue
#utils.hsvWrite(130,120,80,200,190,255) #Write Networktable values Red
if (args["picamera"] > 0):
    cap = PiVideoStream().start()
else:
    cap = WebcamVideoStream().start()
time.sleep(2.0)
distanceTarget = -1
target = -1
centerX = 0
centerY = 0
r1x1 = -1
r1x2 = -1
r2x1 = -1
r2x2 = -1
while True:
    image = cap.read() #Capture frame
    #imageCopy = image

    image = imutils.resize(image, width=320) #resize - needed to allow rest of toolpath to work
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) #Convert from BGR to HSV
    (lower_green,upper_green,display) = utils.hsvRead() #Get lower and Upper HSV values from the Networktable to use
    image_hsv = cv2.inRange(hsv, lower_green, upper_green) #Filter based on lower and upper HSV limits
    #blue_hsv = cv2.inRange(hsv,[80,120,80],[120,190,255]) #Filter based on lower and upper HSV limits
    #subtract Blue and Green to eliminate non green light from the path?
    #image_hsv = image_hsv - blue_hsv

    # convert the resized image to grayscale, blur it slightly,
    # and threshold it
    #gray = cv2.cvtColor(resized, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(image_hsv, (5, 5), 0)
    thresh = cv2.threshold(blurred, 60, 255, cv2.THRESH_BINARY)[1]
	(contourAreaMin,squareRatioMin,squareRationMax,solidityMin,targetHeightMin) = utils.filterRead()
    # find contours in the thresholded image and initialize the
    # shape detector
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
                shape = "Target" if ar >= 0.2 and ar <= 1.5 else "rejected" #ratio of length to heigh, how square is the object
                if shape =="rejected":
                    print("ar rejected"+str(ar))
                    continue
                elif cv2.contourArea(c)/(w*h)<.5:#solidity
                    print("contour area rejected")
                    continue
                elif cY < 160:
                    print("target not at right height ")+str(cY)
                    continue
                if r1x1 == -1:
                    r1x1 = x
                    r1x2 = x+w
                    r1y1 = y
                    r1y2 = y+h
                    h1=h#save for calc distance later
                    print("target 1 "+str(h),cv2.contourArea(c))
                elif r2x1 == -1:
                    r2x1 = x
                    r2x2 = x+w
                    r2y1 = y
                    r2y2 = y+h
                    h2=h#save for calc distance later
                    print("target 2 "+str(h),cv2.contourArea(c))
                else:
                    # Run away!
                    print("run away")
                centerX = (min(r1x1,r2x1)+max(r1x2,r2x2))/2
                centerY = cY
                cont = [x,w,y,h]
                detectArray.append(cont)
                print(detectArray)
                cv2.drawContours(image, [c], -1, (255, 0, 0), 2)
##                centerX = (centerX + cX)/2
##                centerY = (centerY + cY)/2
                detectCenter=detectCenter+1
            else:
                cv2.drawContours(image, [c], -1, (0, 255, 0), 2)
            # multiply the contour (x, y)-coordinates by the resize ratio,
            # then draw the contours and the name of the shape on the image
        for i in range(len(detectArray)):
            for j in range((i+1),len(detectArray)):
                if(abs(detectArray[i][0]-detectArray[j][0])<=10):
                    print("number "+ str(i) + " and " + str(j) + " are matched")
            if detectCenter == 2:
                distanceTarget = 24.80375 + 286.0474*2.718281828459**(-0.07273127*h1)#use h1 to calc distance to target
                print("distance to target "+str(distanceTarget))
                angleToTarget = math.atan((centerX-160)/317.5)*(180/math.pi) #angleToTarget returns angle to target in degrees
                print("angle to target "+str(angleToTarget))
                center = (centerX,centerY)
                cv2.circle(image,(int(centerX),int(centerY)),int(abs(cX-centerX)),(0,255,255),2)
                cv2.circle(image,center,5,(0,255,255),-1)
                target = 1
            else:
                target = -1
            cv2.putText(image, shape+" "+str(shape), (cX, cY), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
        utils.targetWrite(target,centerX,centerY,angleToTarget,loops,distanceTarget)
    if display == 1: #Draw display if turned on 
        cv2.imshow("Frame", image) #Display a screen with outputs
        cv2.imshow("HSV,Blur,Thresh", thresh) #Display a screen with outputs        
        key = cv2.waitKey(1) & 0xFF #Wait for keypress if there is a display
    if key == ord("q"):# if the `q` key was pressed, break from the loop
        break
    r1x1 = -1
    r1x2 = -1
    r1y1 = -1
    r1y2 = -1
    r2x1 = -1
    r2x2 = -1
    r2y1 = -1
    r2y2 = -1
    cv2.imwrite("test.jpg", image)

    
cv2.destroyAllWindows()
cap.stop()
