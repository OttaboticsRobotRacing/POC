#include <iostream>
#include "pid.hpp"

using std::cout;
using std::cin;
using std::endl;

int main(int argc, char const *argv[]) {
    cout << "---Testing pid module---" << endl;
    cout << "PID(0.5, 0.1, 0.1)" << endl;

    PID test = PID(0.5, 0.1, 0.1);

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    cout << "-----\nLeft: 10\nRight: 0" << endl;
    cout << "Output: " << test.getNext(10, 0) << endl;

    cout << "-----\nLeft: 5\nRight: 0" << endl;
    cout << "Output: " << test.getNext(5, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    /////

    cout << "-----\nLeft: 0\nRight: 10" << endl;
    cout << "Output: " << test.getNext(0, 10) << endl;

    cout << "-----\nLeft: 0\nRight: 50" << endl;
    cout << "Output: " << test.getNext(0, 50) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    /////

    cout << "-----\nLeft: 0\nRight: 1" << endl;
    cout << "Output: " << test.getNext(0, 1) << endl;

    cout << "-----\nLeft: 0\nRight: 1" << endl;
    cout << "Output: " << test.getNext(0, 1) << endl;

    cout << "-----\nLeft: 0\nRight: 1" << endl;
    cout << "Output: " << test.getNext(0, 1) << endl;

    cout << "-----\nLeft: 0\nRight: 1" << endl;
    cout << "Output: " << test.getNext(0, 1) << endl;

    cout << "-----\nLeft: 0\nRight: 1" << endl;
    cout << "Output: " << test.getNext(0, 1) << endl;

    cout << "-----\nLeft: 10\nRight: 0" << endl;
    cout << "Output: " << test.getNext(10, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;

    cout << "-----\nLeft: 0\nRight: 0" << endl;
    cout << "Output: " << test.getNext(0, 0) << endl;


    return 0;
}
