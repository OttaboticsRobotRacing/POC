# https://medium.com/@kennethjiang/calibrate-fisheye-lens-using-opencv-333b05afa0b0

import cv2
assert cv2.__version__[0] == '3', 'The fisheye module requires opencv version >= 3.0.0'

import numpy as np
import os
import glob

# TODO 'main' function
# TODO undistort function taking in an image instead of image path
# TODO name the result as a lens profile

def make_directory(path):
    if not os.path.isdir(path):
        os.mkdir(path)

def demo(profile):
    FIRST_IMAGE_DISTORTED = glob.glob(PATH_CHESSBOARD + '/*.jpg')[0]
    FIRST_IMAGE_VERIFICATION = glob.glob(PATH_VERIFICATION + '/*.jpg')[0]

    distorted = cv2.imread(FIRST_IMAGE_VERIFICATION)
    undistorted_img = undistort(profile, FIRST_IMAGE_DISTORTED)

    cv2.imshow("distorted", distorted)
    cv2.imshow("undistorted", undistorted_img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

def export_data(profile):
    profile = tuple(str(result) for result in profile)
    with open('profile.txt', 'w') as file:
        file.write('%s\n%s\n%s' % ('DIM=' + profile[0], 'K=np.array(%s)' % profile[1], 'D=np.array(%s)' % profile[2]))

def undistort(profile, image_path):
    DIM = profile[0]
    K=np.array(profile[1])
    D=np.array(profile[2])

    img = cv2.imread(image_path)
    h,w = img.shape[:2]

    map1, map2 = cv2.fisheye.initUndistortRectifyMap(K, D, np.eye(3), K, DIM, cv2.CV_16SC2)
    undistorted_img = cv2.remap(img, map1, map2, interpolation=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT)
    return undistorted_img

def calibrate():
    subpix_criteria = (cv2.TERM_CRITERIA_EPS+cv2.TERM_CRITERIA_MAX_ITER, 30, 0.1)
    calibration_flags = cv2.fisheye.CALIB_RECOMPUTE_EXTRINSIC+cv2.fisheye.CALIB_CHECK_COND+cv2.fisheye.CALIB_FIX_SKEW

    objp = np.zeros((1, CHECKERBOARD[0]*CHECKERBOARD[1], 3), np.float32)
    objp[0,:,:2] = np.mgrid[0:CHECKERBOARD[0], 0:CHECKERBOARD[1]].T.reshape(-1, 2)

    _img_shape = None
    objpoints = [] # 3d point in real world space
    imgpoints = [] # 2d points in image plane.

    images = glob.glob(PATH_CHESSBOARD + '/*.jpg')

    for fname in images:
        img = cv2.imread(fname)
        if _img_shape == None:
            _img_shape = img.shape[:2]
        else:
            assert _img_shape == img.shape[:2], "All images must share the same size."

        gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
        # Find the chess board corners
        ret, corners = cv2.findChessboardCorners(gray, CHECKERBOARD, cv2.CALIB_CB_ADAPTIVE_THRESH+cv2.CALIB_CB_FAST_CHECK+cv2.CALIB_CB_NORMALIZE_IMAGE)
        # If found, add object points, image points (after refining them)
        if ret == True:
            objpoints.append(objp)
            cv2.cornerSubPix(gray,corners,(3,3),(-1,-1),subpix_criteria)
            imgpoints.append(corners)

            cv2.drawChessboardCorners(img, CHECKERBOARD, corners,ret)
            # cv2.imshow('img',img)
            # cv2.imwrite(PATH_VERIFICATION + '/%s.jpg' % fname, img)
            filename = fname[len(PATH_CHESSBOARD) + 1:]
            cv2.imwrite(PATH_VERIFICATION + '/%s.jpg' % filename, img)

    N_OK = len(objpoints)
    K = np.zeros((3, 3))
    D = np.zeros((4, 1))
    rvecs = [np.zeros((1, 1, 3), dtype=np.float64) for i in range(N_OK)]
    tvecs = [np.zeros((1, 1, 3), dtype=np.float64) for i in range(N_OK)]
    rms, _, _, _, _ = \
        cv2.fisheye.calibrate(
            objpoints,
            imgpoints,
            gray.shape[::-1],
            K,
            D,
            rvecs,
            tvecs,
            calibration_flags,
            (cv2.TERM_CRITERIA_EPS+cv2.TERM_CRITERIA_MAX_ITER, 30, 1e-6)
        )

    profile = (_img_shape[::-1], K.tolist(), D.tolist())
    return profile

CHECKERBOARD = (4,5)

PATH_CHESSBOARD = './chessboard'
PATH_VERIFICATION = './verification'
MIN_SAMPLES = 200

make_directory(PATH_CHESSBOARD)
make_directory(PATH_VERIFICATION)

num_samples = len(glob.glob1(PATH_CHESSBOARD, '*.jpg'))
if num_samples < MIN_SAMPLES:
    raise ValueError('Not enough sample chessboard images. Must be >= %i.' % MIN_SAMPLES)

profile = calibrate()
export_data(profile)
demo(profile)