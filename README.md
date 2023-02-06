## Setup

**Install Maven**
https://maven.apache.org/install.html

**Clone Git Repository**
```
$ git clone git@github.com:Puwya/Java-Tic-Tac-Toe.git
```

**Build Project with Maven**
```
$ cd Java-Tic-Tac-Toe
$ mvn install
```

**Run Application with Maven**
```
$ mvn javafx:run -f pom.xml"
```

**Important**
- According to Maven convention the Java files are under `src/main/java` and the resources files (fxml, images, css etc) are `under src/main/resources`
- All Java and resources files are in the following package: `edu.saddleback.tictactoe`
- Please gitignore any unnecessary files that are added for specific operating system or for specific workspace setups
- When utilizing Scene Builder lets ensure we place the file in the `views` folder

**MAJOR IMPORTANCE**

> Create a separate branch when working with backend code and request a pull request **DO NOT MERGE DIRECTLY INTO MASTER**

> Pull constantly to get newest changes so we dont go through the pain of MERGE CONFLICTS

> If you are unsure on anything ask before committing! We are a team GO SCRUMMY BOYS
