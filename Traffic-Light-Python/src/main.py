import numpy as np
import cv2
from matplotlib import pyplot as plt

def main():
    #stream = cv2.VideoCapture(0)

    #while(True):
        #ret, frame = stream.read()
        #gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        #cv2.imshow('frame', frame)
        #print ret
        #if cv2.waitKey(1) & 0xFF == ord('q'):
            #break

    #stream.release()
    #cv2.destroyAllWindows()
    match()

def match():
    img = cv2.imread('messi5.jpg',0)
    img2 = img.copy()
    template = cv2.imread('template.jpg',0)
    w, h = template.shape[::-1]
    
    methods = ['cv2.TM_CCOEFF', 'cv2.TM_CCOEFF_NORMED', 'cv2.TM_CCORR',
            'cv2.TM_CCORR_NORMED', 'cv2.TM_SQDIFF',
            'cv2.TM_SQDIFF_NORMED']
    
    for meth in methods:
        img = img2.copy()
        method = eval(meth)

        # Apply template Matching
        res = cv2.matchTemplate(img,template,method)
        min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res)

        # If the method is TM_SQDIFF or TM_SQDIFF_NORMED, take minimum
        if method in [cv2.TM_SQDIFF, cv2.TM_SQDIFF_NORMED]:
            top_left = min_loc
        else:
            top_left = max_loc
        bottom_right = (top_left[0] + w, top_left[1] + h)

        cv2.rectangle(img,top_left, bottom_right, 255, 2)

        plt.subplot(121),plt.imshow(res,cmap = 'gray')
        plt.title('Matching Result'), plt.xticks([]), plt.yticks([])
        plt.subplot(122),plt.imshow(img,cmap = 'gray')
        plt.title('Detected Point'), plt.xticks([]), plt.yticks([])
        plt.suptitle(meth)

        plt.show()

if __name__ == "__main__":
    main()