package edu.st.common.messages.server;

import edu.st.common.messages.Message;
import edu.st.common.models.Token;

public class MoveMade extends Message {
  private Integer row = null;
  private Integer col = null;
  private Token moveMaker = null;

  public MoveMade() {
  }

  public MoveMade(Integer row, Integer col, Token moveMaker) {
    this.row = row;
    this.col = col;
    this.moveMaker = moveMaker;
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

  public Token getMoveMaker() {
    return moveMaker;
  }

  public void setMoveMaker(Token moveMaker) {
    this.moveMaker = moveMaker;
  }
}
