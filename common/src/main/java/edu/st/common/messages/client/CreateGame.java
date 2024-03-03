package edu.st.common.messages.client;

import edu.st.common.messages.Message;

public class CreateGame extends Message {
  private String hostname = null;

  public CreateGame() {
  }

  public CreateGame(String hostname) {
    this.hostname = hostname;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
}