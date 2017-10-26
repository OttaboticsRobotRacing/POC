#SRC += modules/module_a/src/m_a.cpp

IDIR += modules/module_a/include/

# _OBJECTS	:= m_a.o
# $(info $(_OBJECTS))
# OBJECTS		+= $(patsubst %,modules/module_a/build/%,$(_OBJECTS))

OBJECTS += modules/module_a/build/m_a.o

# $(info $(OBJECTS))

# CXXFLAGS += -I$(IDIR)

#modules/module_a/build/m_a.o: modules/module_a/src/m_a.cpp
	#@echo "\n----- Compiling object file:" $@ "from" $< "-----\n"
	#@echo $(CXX) -c -o $@ $< $(CXXFLAGS)
	#@mkdir -p modules/module_a/build
	#@echo $(subst /, ,$@)
	#@echo $(filter-out modules,$(subst /, ,$@))
	#@echo $(lastword $(subst /, ,$@))
	#@echo $(subst ., ,$(lastword $(subst /, ,$@)))
	#@echo $(firstword $(subst ., ,$(lastword $(subst /, ,$@))))
	#$(eval FILE_NAME = $(firstword $(subst ., ,$(lastword $(subst /, ,$@)))))
	#@echo FILENAME $(FILE_NAME)
	#@echo $(word 2,$(subst /, ,$@))
	#$(eval MODULE_NAME = $(word 2,$(subst /, ,$@)))
	#@echo MODULENAME $(MODULE_NAME)
	#$(CXX) -c -o $@ $< $(CXXFLAGS)
