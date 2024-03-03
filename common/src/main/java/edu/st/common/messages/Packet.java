package edu.st.common.messages;

import java.io.Serializable;

public class Packet<T extends Message> implements Serializable {
  private T message;
  private String channel;

  public Packet() {
    this.message = null;
    this.channel = null;
  }

  public Packet(T message, String channel) {
    this.message = message;
    this.channel = channel;
  }

  public T getMessage() {
    return message;
  }

  public void setMessage(T message) {
    this.message = message;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  @Override
  public String toString() {
    return "Packet{" +
        "message=" + message +
        ", channel='" + channel + '\'' +
        '}';
  }
}
