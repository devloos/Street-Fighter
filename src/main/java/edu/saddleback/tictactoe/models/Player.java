package edu.saddleback.tictactoe.models;

import java.net.URL;

public class Player {
  public Player(URL path, String name) {
    avatarPath = path;
    this.name = name;
  }

  public URL getAvatarPath() {
    return avatarPath;
  }

  public void setAvatarPath(URL path) {
    avatarPath = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private URL avatarPath = null;
  private String name = null;
}
