import numpy as np
import cv2
import time

def difference(img1, img2):
    diff = cv2.absdiff(img1, img2)
    mask = cv2.cvtColor(diff, cv2.COLOR_BGR2GRAY)

    th = 1
    imask = mask>th

    canvas = np.zeros_like(img2, np.uint8)
    canvas[imask] = img2[imask]

    return canvas


cap = cv2.VideoCapture("K9YWnXI.mp4")
isFirst = True
frame = None
initial = cap.read()
runTimes = []

while(cap.isOpened()):
    prev = frame
    ret, frame = cap.read()

    if not isFirst:
        start_time = time.time()
        diff = difference(prev, frame)
        runTimes.append(time.time() - start_time)

        # print difference run time for each second of video
        if len(runTimes)%24 == 0:
            print(sum(runTimes)/len(runTimes))

        cv2.imshow("difference", diff)
        cv2.imshow("frame", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
    isFirst = False


cap.release()
cv2.destroyAllWindows()
