#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include <iostream>
#include <stdio.h>

using namespace std;
using namespace cv;

/// Global Variables
Mat img; Mat templ; Mat result;
char* image_window = "Source Image";
char* result_window = "Result window";

int match_method;

/// Function Headers
void MatchingMethod( int, void* );

/** @function main */
int main( int argc, char** argv )
{
  /// Load image and template
  templ = imread( argv[1], 1 );
  VideoCapture stream(0);

  if (!stream.isOpened()) {
    cout << "cannot open camera";
  }

  /// Create windows
  namedWindow( image_window, CV_WINDOW_AUTOSIZE );
  //namedWindow( result_window, CV_WINDOW_AUTOSIZE );
  Mat frame;

  for (;;) {
    stream >> frame;
    imshow(image_window, frame);
    waitKey(0);
    /*
    if (waitKey(30) >= 0){ 
      break;
    }
    */
  }

  //MatchingMethod( 0, 0 );

  waitKey(0);
  return 0;
}

/**
 * @function MatchingMethod
 * @brief Trackbar callback
 */
void MatchingMethod( int, void* )
{
  /// Source image to display
  Mat img_display;
  img.copyTo( img_display );

  /// Create the result matrix
  int result_cols =  img.cols - templ.cols + 1;
  int result_rows = img.rows - templ.rows + 1;

  result.create( result_rows, result_cols, CV_32FC1 );

  /// Do the Matching and Normalize
  match_method = CV_TM_CCOEFF_NORMED;
  matchTemplate( img, templ, result, match_method);
  normalize( result, result, 0, 1, NORM_MINMAX, -1, Mat() );

  /// Localizing the best match with minMaxLoc
  double minVal; double maxVal; Point minLoc; Point maxLoc;
  Point matchLoc;

  minMaxLoc( result, &minVal, &maxVal, &minLoc, &maxLoc, Mat() );

  /// For SQDIFF and SQDIFF_NORMED, the best matches are lower values. For all the other methods, the higher the better
  if( match_method  == CV_TM_SQDIFF || match_method == CV_TM_SQDIFF_NORMED )
    { matchLoc = minLoc; }
  else
    { matchLoc = maxLoc; }

  // Create binary mask and black destination image
  cv::Mat mask = cv::Mat(img_display.size(), CV_8UC1, Scalar(0));
  cv::Mat dstImage = cv::Mat::zeros(img_display.size(), img_display.type());

  // Create rectangle in mask
  mask(Rect(matchLoc.x, matchLoc.y, templ.cols, templ.rows)) = Scalar(255);

  // Mask old image to new file
  img_display.copyTo(dstImage, mask);

  // Converts image to HSV
  cv::Mat hsvImg, greenThreshold, redThreshold;
  cvtColor(dstImage, hsvImg, CV_BGR2HSV);

  // Using H bounds and a higher V bound to look for a brighter light,
  // creates two thresholds, one for red, and one for green.
  Mat mask1, mask2;
  cv::inRange(hsvImg, cv::Scalar(38,0,100), cv::Scalar(75,255,255), greenThreshold);
  cv::inRange(hsvImg, cv::Scalar(0,70,100), cv::Scalar(10,255,255), mask1);
  cv::inRange(hsvImg, cv::Scalar(170,70,100), cv::Scalar(180,255,255), mask2);
  redThreshold = mask1 | mask2;

  // Tests for number of white pixels in each threshold
  int redThresholdPixels = 0;
  int greenThresholdPixels = 0;

  for (int x = matchLoc.x; x < matchLoc.x + templ.cols; x++) {
    for (int y = matchLoc.y; y < matchLoc.y + templ.rows; y++) {
      if (redThreshold.at<Vec3b>(x,y) == Vec3b(255,255,255)) {
        redThresholdPixels++;
      }
      if (greenThreshold.at<Vec3b>(x,y) == Vec3b(255,255,255)) {
        greenThresholdPixels++;
      }
    }
  }

  // Print out appropriate message according to which threshold
  // showed more color
  if (redThresholdPixels > greenThresholdPixels) {
    cout << "The traffic light is red" << endl;
  } else {
    cout << "The traffic light is green" << endl;
  }

  //imshow( image_window, dstImage );
  //imshow( result_window, dstImage );

  return;
}