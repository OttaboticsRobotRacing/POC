# https://medium.com/@kennethjiang/calibrate-fisheye-lens-using-opencv-333b05afa0b0

import cv2
import numpy as np
import sys


# You should replace these 3 lines with the output in calibration step
DIM=(640, 480)
K=np.array([[359.0717640266508, 0.0, 315.08914578097387], [0.0, 358.06497428501837, 240.75242680088732], [0.0, 0.0, 1.0]])
D=np.array([[-0.041705903204711826], [0.3677107787593379], [-1.4047363783373128], [1.578157237454529]])

def undistort(img_path):

    img = cv2.imread(img_path)
    h,w = img.shape[:2]

    map1, map2 = cv2.fisheye.initUndistortRectifyMap(K, D, np.eye(3), K, DIM, cv2.CV_16SC2)
    undistorted_img = cv2.remap(img, map1, map2, interpolation=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT)

    cv2.imshow("undistorted", undistorted_img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

if __name__ == '__main__':
    for p in sys.argv[1:]:
        undistort(p)