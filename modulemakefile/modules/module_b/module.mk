#SRC += modules/module_b/src/m_b.cpp

IDIR += modules/module_b/include/

# $(info $(OBJECTS))
# _OBJECTS	:= m_b.o
# $(info $(_OBJECTS))
# OBJECTS		+= $(patsubst %,modules/module_b/build/%,$(_OBJECTS))

OBJECTS += modules/module_b/build/m_b.o

# $(info $(OBJECTS))

# CXXFLAGS += -I$(IDIR)

#modules/module_b/build/m_b.o: modules/module_b/src/m_b.cpp
	#@echo "\n----- Compiling object file:" $@ "from" $< "-----\n"
	#@echo $(CXX) -c -o $@ $< $(CXXFLAGS)
	#@mkdir -p modules/module_b/build
	#$(CXX) -c -o $@ $< $(CXXFLAGS)
