##################################################
# Compiler settings
##################################################

# Set compiler to g++
CXX			:= g++

# Compiler flags
XFLAGS		:= -g -Wall -std=c++11

##################################################
# Directory macros
##################################################

IDIR		:= include/
OBJDIR		:= build/
SRCDIR		:= src/
OUTDIR		:= bin/

##################################################
# Required object dependencies
##################################################

_OBJECTS	:=
#OBJECTS		= $(patsubst %,$(OBJDIR)/%,$(_OBJECTS))

#####

MODULES 	:= core module_a module_b

# include the *.mk files
include $(patsubst %,modules/%/module.mk,$(MODULES))


#IDIR += $(patsubst %,modules/%/include/,$(MODULES))

$(info $(OBJECTS))

##################################################
# Libraries
##################################################

# OpenCV library
INC1		:= `pkg-config --cflags --libs opencv`
# User-created headers
INC2		:= $(patsubst %,-I%,$(IDIR))
# All include directories and libraries
INCDIRS		:= $(INC1) $(INC2)

##################################################
# Compiler flags
##################################################

CXXFLAGS	:= $(INCDIRS) $(XFLAGS)

##################################################
# Make rules
##################################################

.PHONY: core
core: $(OBJECTS)
	@echo "\n----- Compiling core -----\n"
	@mkdir -p $(OUTDIR)
	$(CXX) $(OBJECTS) modules/core/src/core.cpp -o $(OUTDIR)core $(CXXFLAGS)

%.o:
	$(eval FILE_NAME = $(firstword $(subst ., ,$(lastword $(subst /, ,$@)))))
	$(eval MODULE_NAME = $(word 2,$(subst /, ,$@)))
	$(eval BUILD_DIR = modules/$(MODULE_NAME)/build/)
	@mkdir -p $(BUILD_DIR)
	@echo "\n----- Compiling object file:" $@ "from modules/"$(MODULE_NAME)"/src/"$(FILE_NAME)".cpp -----\n"
	$(CXX) -c -o $@ modules/$(MODULE_NAME)/src/$(FILE_NAME).cpp $(CXXFLAGS)







.PHONY: clean
clean:
	@echo "\n----- Cleaning object files -----\n"
	rm -rf $(OBJECTS)
