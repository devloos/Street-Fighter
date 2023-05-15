package edu.st.client.controllers;

import edu.st.client.models.Player;

public class SingleGameController extends BaseController {

  public SingleGameController(Player player, Player cpu) {
    this.player = player;
    this.cpu = cpu;
    // gonna need to init bored
    // and play media music
  }

  private Player player = null;
  private Player cpu = null;
}
