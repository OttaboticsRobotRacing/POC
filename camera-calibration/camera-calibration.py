'''
Adapted from
https://medium.com/@kennethjiang/calibrate-fisheye-lens-using-opencv-333b05afa0b0
'''

import cv2
assert cv2.__version__[0] == '3', 'The fisheye module requires opencv version >= 3.0.0'

import numpy as np
import os
import glob

class CameraCalibration:
    def __init__(self, profile=None):
        self.CHECKERBOARD = (4,5)

        self.PATH_CHESSBOARD = './chessboard'
        self.PATH_VERIFICATION = './verification'
        # self.MIN_SAMPLES = 200
        self.MIN_SAMPLES = 4

        self.profile = profile

    def calibrate(self):
        self.make_directory(self.PATH_CHESSBOARD)
        self.make_directory(self.PATH_VERIFICATION)

        num_samples = len(glob.glob1(self.PATH_CHESSBOARD, '*.jpg'))
        if num_samples < self.MIN_SAMPLES:
            raise ValueError('Not enough sample chessboard images. Must be >= %i.' % self.MIN_SAMPLES)

        self.profile = self.calibrator()

    def demo(self):
        FIRST_IMAGE_DISTORTED = glob.glob(self.PATH_CHESSBOARD + '/*.jpg')[0]
        FIRST_IMAGE_DISTORTED = cv2.imread(FIRST_IMAGE_DISTORTED)
        FIRST_IMAGE_VERIFICATION = glob.glob(self.PATH_VERIFICATION + '/*.jpg')[0]

        distorted = cv2.imread(FIRST_IMAGE_VERIFICATION)
        undistorted_img = self.undistort(FIRST_IMAGE_DISTORTED)

        cv2.imshow("distorted", distorted)
        cv2.imshow("undistorted", undistorted_img)
        cv2.waitKey(0)
        cv2.destroyAllWindows()

    def calibrator(self):
        subpix_criteria = (cv2.TERM_CRITERIA_EPS+cv2.TERM_CRITERIA_MAX_ITER, 30, 0.1)
        calibration_flags = cv2.fisheye.CALIB_RECOMPUTE_EXTRINSIC+cv2.fisheye.CALIB_CHECK_COND+cv2.fisheye.CALIB_FIX_SKEW

        objp = np.zeros((1, self.CHECKERBOARD[0]*self.CHECKERBOARD[1], 3), np.float32)
        objp[0,:,:2] = np.mgrid[0:self.CHECKERBOARD[0], 0:self.CHECKERBOARD[1]].T.reshape(-1, 2)

        _img_shape = None
        objpoints = [] # 3d point in real world space
        imgpoints = [] # 2d points in image plane.

        images = glob.glob(self.PATH_CHESSBOARD + '/*.jpg')

        for fname in images:
            img = cv2.imread(fname)
            if _img_shape == None:
                _img_shape = img.shape[:2]
            else:
                assert _img_shape == img.shape[:2], "All images must share the same size."

            gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
            # Find the chess board corners
            ret, corners = cv2.findChessboardCorners(gray, self.CHECKERBOARD, cv2.CALIB_CB_ADAPTIVE_THRESH+cv2.CALIB_CB_FAST_CHECK+cv2.CALIB_CB_NORMALIZE_IMAGE)
            # If found, add object points, image points (after refining them)
            if ret == True:
                objpoints.append(objp)
                cv2.cornerSubPix(gray,corners,(3,3),(-1,-1),subpix_criteria)
                imgpoints.append(corners)

                cv2.drawChessboardCorners(img, self.CHECKERBOARD, corners,ret)
                # cv2.imshow('img',img)
                # cv2.imwrite(self.PATH_VERIFICATION + '/%s.jpg' % fname, img)
                filename = fname[len(self.PATH_CHESSBOARD) + 1:]
                cv2.imwrite(self.PATH_VERIFICATION + '/%s.jpg' % filename, img)

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

    def undistort(self, img):
        assert self.profile != None
        profile = self.profile

        DIM = profile[0]
        K=np.array(profile[1])
        D=np.array(profile[2])

        h,w = img.shape[:2]

        map1, map2 = cv2.fisheye.initUndistortRectifyMap(K, D, np.eye(3), K, DIM, cv2.CV_16SC2)
        undistorted_img = cv2.remap(img, map1, map2, interpolation=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT)
        return undistorted_img

    def import_data(self):
        raise NotImplementedError

    def export_data(self):
        profile = tuple(str(result) for result in self.profile)
        with open('profile.txt', 'w') as file:
            file.write('%s\n%s\n%s' % ('DIM = ' + profile[0], 'K = np.array(%s)' % profile[1], 'D = np.array(%s)' % profile[2]))

    @staticmethod
    def make_directory(path):
        if not os.path.isdir(path):
            os.mkdir(path)

def main():
    # DIM = (640, 480)
    # K = np.array([[359.0717640266508, 0.0, 315.08914578097387], [0.0, 358.06497428501837, 240.75242680088732], [0.0, 0.0, 1.0]])
    # D = np.array([[-0.041705903204711826], [0.3677107787593379], [-1.4047363783373128], [1.578157237454529]])
    # profile = (DIM, K, D)
    # CC = CameraCalibration(profile)

    CC = CameraCalibration()
    CC.calibrate()
    CC.export_data()

    CC.demo()

if __name__ == "__main__":
    main()
