# Player
CREATE TABLE IF NOT EXISTS Player
(
  uuid CHAR(36) PRIMARY KEY NOT NULL
);

# Sky Wars Player Statistics
CREATE TABLE IF NOT EXISTS SWPlayerStatistics
(
  player CHAR(36) NOT NULL,
  statistic TINYTEXT NOT NULL,
  value INT NOT NULL,
  CONSTRAINT PRIMARY KEY (player, statistic),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);

# Sky Wars Player Achievements
CREATE TABLE IF NOT EXISTS SWPlayerAchievements
(
  player CHAR(36) NOT NULL,
  achievement TINYTEXT NOT NULL,
  unlockDate DATE NOT NULL,
  CONSTRAINT PRIMARY KEY (player, achievement),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);

# Sky Wars Log
CREATE TABLE IF NOT EXISTS SWLog
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  winner CHAR(36) NOT NULL,
  startDate DATE NOT NULL,
  endDate DATE NOT NULL,
  CONSTRAINT fk_player FOREIGN KEY (winner) REFERENCES Player(uuid)
);

# Sky Wars Chat Log
CREATE TABLE IF NOT EXISTS SWChatLog
(
  session INT NOT NULL,
  message TINYTEXT NOT NULL,
  CONSTRAINT PRIMARY KEY (session, message),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES SWLog(id)
);

# Sky Wars Events Log
CREATE TABLE IF NOT EXISTS SWEventsLog
(
  session INT NOT NULL,
  event TINYTEXT NOT NULL,
  CONSTRAINT PRIMARY KEY (session, event),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES SWLog(id)
);

# Sky Wars Player Games
CREATE TABLE IF NOT EXISTS SWPlayerGames
(
  session INT NOT NULL,
  player INT NOT NULL,
  CONSTRAINT PRIMARY KEY (session, player),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES SWLog(id),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);