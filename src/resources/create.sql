# Player
CREATE TABLE IF NOT EXISTS Player
(
  uuid CHAR(36) PRIMARY KEY NOT NULL
);

# Player Achievements
CREATE TABLE IF NOT EXISTS PlayerAchievements
(
  player CHAR(36) NOT NULL,
  achievement TINYTEXT NOT NULL,
  unlockDate DATE NOT NULL,
  CONSTRAINT PRIMARY KEY (player, achievement),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);

# Player Statistics
CREATE TABLE IF NOT EXISTS PlayerStatistics
(
  player CHAR(36) NOT NULL,
  statistic TINYTEXT NOT NULL,
  value INT NOT NULL,
  CONSTRAINT PRIMARY KEY (player, statistic),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);

# Log
CREATE TABLE IF NOT EXISTS Log
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  winner CHAR(36) NOT NULL,
  startDate DATE NOT NULL,
  endDate DATE NOT NULL,
  CONSTRAINT fk_player FOREIGN KEY (winner) REFERENCES Player(uuid)
);

# Chat Log
CREATE TABLE IF NOT EXISTS ChatLog
(
  session INT NOT NULL,
  message TINYTEXT NOT NULL,
  sentTime TIMESTAMP NOT NULL,
  CONSTRAINT PRIMARY KEY (session, message, sentTime),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES Log(id)
);

# Events Log
CREATE TABLE IF NOT EXISTS EventsLog
(
  session INT NOT NULL,
  event TINYTEXT NOT NULL,
  eventTime TIMESTAMP NOT NULL,
  CONSTRAINT PRIMARY KEY (session, event, eventTime),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES Log(id)
);

# Player Games
CREATE TABLE IF NOT EXISTS PlayerGames
(
  session INT NOT NULL,
  player INT NOT NULL,
  CONSTRAINT PRIMARY KEY (session, player),
  CONSTRAINT fk_session FOREIGN KEY (session) REFERENCES Log(id),
  CONSTRAINT fk_player FOREIGN KEY (player) REFERENCES Player(uuid)
);