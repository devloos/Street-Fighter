package edu.st.common.messages.server;

import edu.st.common.messages.GameResult;
import edu.st.common.messages.Message;

public class GameEnded extends Message {
  private GameResult result = null;
  private Integer row = null;
  private Integer col = null;

  public GameEnded() {
  }

  public GameEnded(GameResult result, Integer row, Integer col) {
    this.result = result;
    this.row = row;
    this.col = col;
  }

  public GameResult getResult() {
    return result;
  }

  public void setResult(GameResult result) {
    this.result = result;
  }

  public Integer getRow() {
    return row;
  }

  public void setRow(Integer row) {
    this.row = row;
  }

  public Integer getCol() {
    return col;
  }

  public void setCol(Integer col) {
    this.col = col;
  }
}
