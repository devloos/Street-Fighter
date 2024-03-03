package edu.st.common.messages.client;

import edu.st.common.messages.Message;

public class MakeMove extends Message {
  private Integer row = null;
  private Integer col = null;

  public MakeMove() {
  }

  public MakeMove(Integer row, Integer col) {
    this.row = row;
    this.col = col;
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
