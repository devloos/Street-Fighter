package edu.st.common.messages.client;

import edu.st.common.messages.Message;

public class PlayerAvatarChange extends Message {
  private String avatarSelected = null;
  private String prevAvatar = null;

  public PlayerAvatarChange() {
  }

  public PlayerAvatarChange(String avatarSelected, String prevAvatar) {
    this.avatarSelected = avatarSelected;
    this.prevAvatar = prevAvatar;
  }

  public String getAvatarSelected() {
    return avatarSelected;
  }

  public void setAvatarSelected(String avatarSelected) {
    this.avatarSelected = avatarSelected;
  }

  public String getPrevAvatar() {
    return prevAvatar;
  }

  public void setPrevAvatar(String prevAvatar) {
    this.prevAvatar = prevAvatar;
  }
}
