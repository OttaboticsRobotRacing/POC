import numpy as np
import cv2
import imutils
from matplotlib import pyplot as plt

cap = cv2.VideoCapture(0)

while (True):
  ret, image = cap.read()

  template = cv2.imread('tl_temp2.jpg')
  #template = cv2.cvtColor(template, cv2.COLOR_BGR2GRAY)
  #template = cv2.Canny(template, 50, 200)
  (tH, tW) = template.shape[:2]

  #template = cv2.cvtColor(template, cv2.COLOR_BGR2RGB)

  #image = cv2.imread("tl_green.jpg")
  #gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
  found = None

  for scale in np.linspace(0.2, 1.0, 20)[::-1]:
    resized = imutils.resize(image, width = int(image.shape[1] * scale))
    r = image.shape[1] / float(resized.shape[1])
    if resized.shape[0] < tH or resized.shape[1] < tW:
		  break
    result = cv2.matchTemplate(resized, template, cv2.TM_CCOEFF)
    (_, maxVal, _, maxLoc) = cv2.minMaxLoc(result)

    #clone = np.dstack([resized, resized, resized])
    cv2.rectangle(resized, (maxLoc[0], maxLoc[1]), (maxLoc[0] + tW, maxLoc[1] + tH), (0, 0, 255), 2)
    #cv2.imshow("Visualize", resized)
    #cv2.waitKey(0)

    if found is None or maxVal > found[0]:
	    found = (maxVal, maxLoc, r)

  (_, maxLoc, r) = found
  (startX, startY) = (int(maxLoc[0] * r), int(maxLoc[1] * r))
  (endX, endY) = (int((maxLoc[0] + tW) * r), int((maxLoc[1] + tH) * r))
  cv2.rectangle(image, (startX, startY), (endX, endY), (0, 0, 255), 2)

  cv2.imshow("template", template)
  cv2.imshow("image", image)

  if cv2.waitKey(1) & 0xFF == ord('q'):
    break
#plt.imshow(template)
#plt.imshow(image)
#plt.show()

cap.release()
cv2.destroyAllWindows()