#include <iostream>
#include "m_a.hpp"
#include "m_b.hpp"
#include "opencv2/opencv.hpp"

using std::cout;
using std::endl;
using std::cin;

int main()
{
    cout << "core" << endl;
    module_a_function();
    module_b_function();

    cout << "opencv version: " << CV_VERSION << endl;

    return 0;
}
