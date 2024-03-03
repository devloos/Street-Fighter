## Demo

https://github.com/devloos/Street-Fighter/assets/58356571/8a1a73fd-4ad2-45d9-ae42-9db0d1c4a66a

## Outcome

### Multithreading:

- Implemented multithreading to handle concurrent gameplay actions, such as player moves and animations.
- Utilized threads for managing game logic separately from graphical rendering for a smoother user experience.
- Ensured thread safety to prevent race conditions and synchronization issues.
  
### Networking (Sockets):

- Integrated socket programming to enable multiplayer functionality.
- Established a client-server architecture for players to connect over a network.
- Implemented data serialization for exchanging game state information between clients and the server.

### Project Management:

- Employed Agile methodologies to plan and organize development tasks.
- Utilized version control systems (e.g., Git) for collaborative coding and tracking changes.
- Scheduled regular meetings and milestones to keep the project on track and ensure team coordination.

## Setup

**Install Maven**
https://maven.apache.org/install.html

**Prerequisites**

- Scene Builder: https://gluonhq.com/products/scene-builder/

**Clone Git Repository**

```
$ git clone git@github.com:devloos/Street-Fighter.git
```

**Build Project with Maven**

- Make sure you build dependencies for common

```
$ cd Street-Fighter
$ mvn install
```

**Run Application with Maven**

```
$ mvn javafx:run
```

**Important**

- According to Maven convention the Java files are under `src/main/java` and the resources files (fxml, images, css etc) are `under src/main/resources`
- All Java and resources files are in the following package: `edu.st.client`
- Please gitignore any unnecessary files that are added for specific operating system or for specific workspace setups
- When utilizing Scene Builder lets ensure we place the file in the `views` folder

**MAJOR IMPORTANCE**

> Create a separate branch when working with backend code and request a pull request **DO NOT MERGE DIRECTLY INTO MASTER**

> Pull constantly to get newest changes so we dont go through the pain of MERGE CONFLICTS

> If you are unsure on anything ask before committing! We are a team GO SCRUMMY BOYS
