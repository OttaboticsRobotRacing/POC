import numpy as np
import cv2
from matplotlib import pyplot as plt

template = cv2.imread('tl_temp.jpg')
template = cv2.cvtColor(template, cv2.COLOR_BGR2GRAY)
template = cv2.Canny(template, 50, 200)
(tH, tW) = template.shape[:2]
plt.imshow("Template", template)