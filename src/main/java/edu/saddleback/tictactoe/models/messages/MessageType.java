package edu.saddleback.tictactoe.models.messages;

public enum MessageType {
  Received,
  SubscribeGameList,
  GameList,
  CreateGame,
  GameCreated,
  GameNotCreated,
  JoinGame,
  GameJoined,
  GameNotjoinable,
  PlayerJoined,
  PlayerReady,
  AvatarSelectionStarted,
  PlayerInfoChanged,
  PlayerInfoConfirmed,
  GameStarted,
  GameNotStarted,
  MakeMove,
  MoveMade,
  GameEnded,
  PlayAgain,
  BackToMainMenu
}
