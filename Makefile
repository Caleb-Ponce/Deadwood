# Define the Java compiler to use
JAVAC = javac

# Define the flags to pass to the Java compiler
JAVAC_FLAGS = -g

# Define the list of Java files to compile
JAVA_FILES = \
	./Board.java \
	./CastingOffice.java \
	./Controller.java \
	./DeadWood.java \
	./GameManager.java \
	./Player.java \
	./Position.java \
	./Room.java \
	./Scene.java \
	./XMLParser.java

# Define the default make target
all: $(JAVA_FILES)
	$(JAVAC) $(JAVAC_FLAGS) $(JAVA_FILES)

# Define a rule to clean the compiled class files
clean:
	rm -f $(SRC_DIR)/*.class
