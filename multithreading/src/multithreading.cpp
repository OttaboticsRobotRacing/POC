#include <iostream>
#include <cstdlib>
#include <unistd.h>
#include <thread>
#include <mutex>
#include "opencv2/opencv.hpp"

using std::cout;
using std::endl;

cv::Mat frame_raw;
cv::Mat frame_processed;
std::mutex mutex_frame_raw;
std::mutex mutex_frame_processed;

cv::VideoCapture cap(0);

void hello()
{
    for (int i = 0; i < 5; ++i)
    {
        cout << "Hello! (" << i << ")" << endl;
        sleep(2);
    }
}

void readFrame()
{
    for (;;)
    {
        mutex_frame_raw.lock();
        if (cap.isOpened())
        {
            cap >> frame_raw;
        }
        mutex_frame_raw.unlock();
    }
}

void processFrame()
{
    for (;;)
    {
        mutex_frame_processed.lock();
        if (frame_raw.cols > 0 && frame_raw.rows > 0)
        {
            if (frame_raw.channels() != 1)
                cv::cvtColor(frame_raw, frame_processed, cv::COLOR_BGR2GRAY);
            cv::GaussianBlur(frame_processed, frame_processed, cv::Size(7,7), 1.5, 1.5);
            cv::Canny(frame_processed, frame_processed, 0, 30, 3);
        }
        mutex_frame_processed.unlock();
    }
}

int main()
{
    std::thread getFrame(readFrame);
    getFrame.detach();
    std::thread getProcessedFrame(processFrame);
    getProcessedFrame.detach();

    cout << ">>>>> DEBUG <<<<<" << endl;

    cv::namedWindow("test", 1);

    //sleep(5);

    cout << ">>>>> DEBUG 2 <<<<<" << endl;

    for (;;)
    {
        /*
        cap >> frame_processed;
        if (frame_processed.channels() != 1)
            cv::cvtColor(frame_processed, frame_processed, cv::COLOR_BGR2GRAY);
        cv::GaussianBlur(frame_processed, frame_processed, cv::Size(7,7), 1.5, 1.5);
        cv::Canny(frame_processed, frame_processed, 0, 30, 3);
        */

        cout << ">>>>> DEBUG <<<<<" << endl;
        cout << "RAW: " << frame_raw.cols << ", " << frame_raw.rows << endl;
        cout << "PROCESSED: " << frame_processed.cols << ", " << frame_processed.rows << endl;
        if (frame_processed.cols > 0 && frame_processed.rows > 0)
        {
            //mutex_frame_processed.lock();
            cv::Mat copy = frame_processed.clone();
            //mutex_frame_processed.unlock();
            cv::imshow("test", copy);
        }
        if(cv::waitKey(30) == 27) break; // exit by pressing esc, value can by found on asciitable.com under Dec column
    }

    /*
    std::thread test(hello);
    std::thread test2(hello);

    test.join();
    cout << "hi" << endl;
    test2.join();
    cout << "hi2" << endl;

    std::thread test_detach(hello);
    test_detach.detach();

    sleep(5);

    cout << "hi3" << endl;
    */

    return 0;
}
