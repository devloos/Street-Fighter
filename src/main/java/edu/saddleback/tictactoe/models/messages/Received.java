package edu.saddleback.tictactoe.models.messages;

public class Received extends Message {
  public Received(boolean success) {
    super(MessageType.Received);
    this.success = success;
  }

  public Received(boolean success, String message) {
    super(MessageType.Received, message);
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  private boolean success = false;
}
