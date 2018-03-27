import numpy as np
import cv2
import imutils
from matplotlib import pyplot as plt

template = cv2.imread('tl_temp.jpg')
#template = cv2.cvtColor(template, cv2.COLOR_BGR2GRAY)
#template = cv2.Canny(template, 50, 200)
(tH, tW) = template.shape[:2]

#template = cv2.cvtColor(template, cv2.COLOR_BGR2RGB)

image = cv2.imread("tl_red.jpg")
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
found = None

for scale in np.linspace(0.2, 1.0, 20)[::-1]:
    resized = imutils.resize(gray, width = int(gray.shape[1] * scale))
    r = gray.shape[1] / float(resized.shape[1])
    if resized.shape[0] < tH or resized.shape[1] < tW:
		break
    result = cv2.matchTemplate(resized, template, cv2.TM_SQDIFF)
    (_, maxVal, _, maxLoc) = cv2.minMaxLoc(result)

    clone = np.dstack([resized, resized, resized])
    cv2.rectangle(clone, (maxLoc[0], maxLoc[1]), (maxLoc[0] + tW, maxLoc[1] + tH), (0, 0, 255), 2)
    cv2.imshow("Visualize", clone)
    cv2.waitKey(0)

    if found is None or maxVal > found[0]:
	    found = (maxVal, maxLoc, r)

(_, maxLoc, r) = found
(startX, startY) = (int(maxLoc[0] * r), int(maxLoc[1] * r))
(endX, endY) = (int((maxLoc[0] + tW) * r), int((maxLoc[1] + tH) * r))
cv2.rectangle(image, (startX, startY), (endX, endY), (0, 0, 255), 2)

plt.imshow(template)
plt.imshow(image)
plt.show()