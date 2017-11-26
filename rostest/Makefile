##################################################
# Compiler settings
##################################################

# Set compiler to g++
CXX			= g++

# Compiler flags
XFLAGS		= -g -Wall -std=c++11

##################################################
# Directory macros
##################################################

IDIR		= include/
OBJDIR		= build/
SRCDIR		= src/
OUTDIR		= bin/

##################################################
# Required object dependencies
##################################################

_OBJECTS	=
OBJECTS		= $(patsubst %,$(OBJDIR)/%,$(_OBJECTS))

##################################################
# Libraries
##################################################

# OpenCV library
INC1		= `pkg-config --cflags --libs opencv`
# User-created headers
INC2		= -I$(IDIR)
# ROS library
#INC3		= -lcv_bridge -limage_transport -I/opt/ros/kinetic/include -L/opt/ros/kinetic/lib
INC3		= `pkg-config --cflags --libs roscpp`
# All include directories and libraries
INCDIRS		= $(INC1) $(INC2) $(INC3)

##################################################
# Compiler flags
##################################################

CXXFLAGS	= $(INCDIRS) $(XFLAGS)

##################################################
# Make rules
##################################################

.PHONY: all
all: $(OBJECTS)
	@echo "\n----- Compiling main -----\n"
	@echo $(CXX) $(OBJECTS) $(SRCDIR)main.cpp -o $(OUTDIR)main $(CXXFLAGS)
	@mkdir -p build
	@mkdir -p bin
	@mkdir -p lib
	$(CXX) $(OBJECTS) $(SRCDIR)main.cpp -o $(OUTDIR)main $(CXXFLAGS)

.PHONY: test
test: $(OBJECTS)
	$(CXX) $(OBJECTS) $(SRCDIR)main.cpp -o $(OUTDIR)main -I/usr/local/include/opencv -I/usr/local/include -L/usr/local/lib -lopencv_stitching -lopencv_superres -lopencv_videostab -lopencv_photo -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_ccalib -lopencv_cvv -lopencv_dpm -lopencv_face -lopencv_fuzzy -lopencv_hdf -lopencv_img_hash -lopencv_line_descriptor -lopencv_optflow -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_stereo -lopencv_structured_light -lopencv_viz -lopencv_phase_unwrapping -lopencv_surface_matching -lopencv_tracking -lopencv_datasets -lopencv_text -lopencv_dnn -lopencv_plot -lopencv_ml -lopencv_xfeatures2d -lopencv_shape -lopencv_video -lopencv_ximgproc -lopencv_calib3d -lopencv_features2d -lopencv_highgui -lopencv_videoio -lopencv_flann -lopencv_xobjdetect -lopencv_imgcodecs -lopencv_objdetect -lopencv_xphoto -lopencv_imgproc -lopencv_core -Iinclude/ -I/opt/ros/kinetic/include -L/opt/ros/kinetic/lib -lroscpp -lpthread  -lrosconsole -lrosconsole_log4cxx -lrosconsole_backend_interface -lroscpp_serialization -lrostime -lxmlrpcpp -lcpp_common -g -Wall -std=c++11


$(OBJDIR)%.o: $(SRCDIR)%.cpp
	@echo "\n----- Compiling object file:" $@ "from" $< "-----\n"
	@echo $(CXX) -c -o $@ $< $(CXXFLAGS)
	@mkdir -p build
	$(CXX) -c -o $@ $< $(CXXFLAGS)


.PHONY: clean
clean:
	@echo "\n----- Cleaning object files -----\n"
	rm -rf $(OBJDIR)*.o
