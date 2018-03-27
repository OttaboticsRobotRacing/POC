import numpy as np
import cv2
import time

# ==============================================================================

def blend_non_transparent(face_img, overlay_img):
    # https://stackoverflow.com/a/37198079
    gray_overlay = cv2.cvtColor(overlay_img, cv2.COLOR_BGR2GRAY)
    overlay_mask = cv2.threshold(gray_overlay, 1, 255, cv2.THRESH_BINARY)[1]

    background_mask = 255 - overlay_mask

    overlay_mask = cv2.cvtColor(overlay_mask, cv2.COLOR_GRAY2BGR)
    background_mask = cv2.cvtColor(background_mask, cv2.COLOR_GRAY2BGR)

    face_part = (face_img * (1 / 255.0)) * (background_mask * (1 / 255.0))
    overlay_part = (overlay_img * (1 / 255.0)) * (overlay_mask * (1 / 255.0))

    return np.uint8(cv2.addWeighted(face_part, 255.0, overlay_part, 255.0, 0.0))

def difference(img1, img2):
    # https://stackoverflow.com/a/47938979
    diff = cv2.absdiff(img1, img2)
    mask = cv2.cvtColor(diff, cv2.COLOR_BGR2GRAY)

    th = 1
    imask = mask>th

    canvas = np.zeros_like(img2, np.uint8)
    canvas[imask] = img2[imask]

    return canvas

def testTime(t0, t1):
    times.append(t1 - t0)
    if len(times)%24 == 0:
        print("Running average:", sum(times)/len(times), "s")

def compress(img):
    cv2.imwrite("compressed.png", img,  [cv2.IMWRITE_PNG_COMPRESSION, 1])
    return cv2.imread("compressed.png")


# ==============================================================================


cap = cv2.VideoCapture("K9YWnXI.mp4")

isFirst = True
whoops = True

image1 = None
canvas = None

times = []

while(cap.isOpened() and whoops):
    image2 = image1
    ret, image1 = cap.read()

    if not isFirst:
        try:
            t0 = time.clock()

            diff = difference(image2, image1)
            # diff = compress(diff)
            canvas = blend_non_transparent(canvas, diff)

            t1 = time.clock()
            testTime(t0, t1)

            cv2.imshow("diff", diff)
            cv2.imshow("canvas", canvas)
            cv2.imshow("org", image1)
        except:
            print(":p")
            whoops = False
    else:
        canvas = image1

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
    isFirst = False

cap.release()
cv2.destroyAllWindows()
