JAVAC=javac --source-path src/ -d .class/
JAVA=java -cp .class/
MODULES=--module-path lib/ --add-modules javafx.controls,javafx.fxml
SRC=src/Game.java 

all:
	$(JAVAC) $(MODULES) $(SRC)

run:
	$(JAVA) $(MODULES) src/Game.java

clean:
	rm -rf .class/*