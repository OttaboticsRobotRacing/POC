import numpy
from networktables import NetworkTable
NetworkTable.setIPAddress("roborio-2609-frc.local")#Change the address to your own
NetworkTable.setClientMode()
NetworkTable.initialize()
sd = NetworkTable.getTable("RaspberryPi")

def hsvWrite(H_L,H_U,S_L,S_U,V_L,V_U): #Write HSV values to the Networktable
    sd.putNumber('H_L',H_L) #H lower
    sd.putNumber('H_U',H_U) #H upper
    sd.putNumber('S_L',S_L) #S lower
    sd.putNumber('S_U',S_U) #S upper
    sd.putNumber('V_L',V_L) #V lower
    sd.putNumber('V_U',V_U) #V upper
    
def hsvRead(): #Read HSV filter values from the Networktable
    H_L = sd.getNumber('H_L') #H lower
    H_U = sd.getNumber('H_U') #H upper
    S_L = sd.getNumber('S_L') #S lower
    S_U = sd.getNumber('S_U') #S upper
    V_L = sd.getNumber('V_L') #V lower
    V_U = sd.getNumber('V_U') #V upper
    display = sd.getNumber("display",0) #Default to Off(0) for display of window
    lower_green = numpy.array([H_L,S_L,V_L]) #Set array of lower HSV limits
    upper_green = numpy.array([H_U,S_U,V_U]) #Set array of upper HSV limits
    return (lower_green,upper_green,display)

def targetWrite(target,centerX,centerY,angleToTarget,loops, distanceToTarget):#Read traget values from the Networktable
    sd.putNumber('target', target) #Put out target 1 if found, -1 if not found
    sd.putNumber('centerX', centerX) #Put out centerX in pixels
    sd.putNumber('centerY', centerY) #Put out centerY in pixels
    sd.putNumber('angleToTarget', angleToTarget) #Put out angle to target in degrees
    sd.putNumber('distanceToTarget', distanceToTarget)
    sd.putNumber('piLoops', loops)
    
def filterRead():
    contourAreaMin = sd.getNumber('contourAreaMin', 0)
    squareRatioMin = sd.getNumber('squareRatioMin', 0)
    squareRationMax = sd.getNumber('squareRationMax', 0)
    solidityMin = sd.getNumber('solidityMin', 0)
    targetHeightMin = sd.getNumber('targetHeightMin', 0)  
    return(contourAreaMin,squareRatioMin,squareRationMax,solidityMin,targetHeightMin)
##    contourAreaMin,squareRatioMin, squareRationMax, solidityMin, targetHeightMin
