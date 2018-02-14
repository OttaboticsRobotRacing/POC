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
  img = imread( argv[1], 1 );
  templ = imread( argv[2], 1 );

  /// Create windows
  namedWindow( image_window, CV_WINDOW_AUTOSIZE );
  namedWindow( result_window, CV_WINDOW_AUTOSIZE );

  MatchingMethod( 0, 0 );

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

  // Multiple failed attempts at using masking to black out image
  // Out of sake of time, for loop was used instead.
  // Masking to be implemented in the future.

  //rectangle( img_display, matchLoc, Point( matchLoc.x + templ.cols , matchLoc.y + templ.rows ), Scalar::all(0), 2, 8, 0 );
  //cv::Mat srcImage = cv::imread(img_display,CV_LOAD_IMAGE_GRAYSCALE);
  //cv::Mat mask = cv::Mat::zeros(srcImage.size(), srcImage.type());
  //cv::Mat dstImage = cv::Mat::zeros(srcImage.size(), srcImage.type());
  //cv::Rect rects = Rect(matchLoc, Point( matchLoc.x + templ.cols , matchLoc.y + templ.rows ));
  //cv::rectangle(mask, matchLoc, Point( matchLoc.x + templ.cols , matchLoc.y + templ.rows ), cv::Scalar(255, 0, 0), 2, 8, 0 );
  //mask(Rect(matchLoc.x, matchLoc.y, templ.cols, templ.rows)) = Scalar(255,255,255);
  //srcImage.copyTo(dstImage, mask);
  //rectangle( result, matchLoc, Point( matchLoc.x + templ.cols , matchLoc.y + templ.rows ), Scalar::all(0), 2, 8, 0 );

  // Blacks out uninteresting parts of image
  for (int i = 0; i < img_display.cols; i++) {
    for (int j = 0; j < img_display.rows; j++) {
      if (i > matchLoc.x && i < matchLoc.x + templ.cols && j > matchLoc.y && j < matchLoc.y + templ.rows) {

      } else {
        Vec3b color = img_display.at<Vec3b>(Point(i,j));
        color[0] = 0;
        color[1] = 0;
        color[2] = 0;
        img_display.at<Vec3b>(Point(i,j)) = color;
      }
    }
  }

  // Converts image to HSV
  cv::Mat hsvImg, greenThreshold, redThreshold;
  cvtColor(img_display, hsvImg, CV_BGR2HSV);

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
    
  } else {

  }

  imshow( image_window, redThreshold );
  //imshow( result_window, dstImage );

  return;
}