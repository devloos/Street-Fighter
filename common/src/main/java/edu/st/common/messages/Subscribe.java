package edu.st.common.messages;

import java.util.ArrayList;

public class Subscribe extends Message {
  private ArrayList<String> channels = null;

  public Subscribe(ArrayList<String> channels) {
    this.channels = channels;
  }

  public Subscribe() {
    this.channels = null;
  }

  public ArrayList<String> getChannels() {
    return channels;
  }
}
