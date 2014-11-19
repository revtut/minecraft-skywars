package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.ArenaDat;
import net.RevTut.Skywars.libraries.converters.ConvertersAPI;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

/**
 * MySQL Object.
 *
 * <P>Create and manage a MySQL connection.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class MySQL {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Hostname of the MySQL Database
     */
    private final String hostname;

    /**
     * Port of the MySQL Database
     */
    private final String port;

    /**
     * Database of the MySQL
     */
    private final String database;

    /**
     * Username of the MySQL Database
     */
    private final String username;

    /**
     * Password of the MySQL Database
     */
    private final String password;

    /**
     * Core Database
     */
    private final String DBCore = "Core";

    /**
     * SkyWars Core Database
     */
    private final String DBGameCore = "SkyWarsCore";

    /**
     * SkyWars Info Database
     */
    private final String DBGameInfo = "SkyWarsInfo";

    /**
     * MySQL Database connection
     */
    private Connection connection;

    /**
     * Constructor of MySQL
     *
     * @param plugin   main class
     * @param hostname hostname of the MySQL
     * @param port     port of the MySQL
     * @param database database of the MySQL
     * @param username username of the MySQL
     * @param password password of the MySQL
     */
    public MySQL(Main plugin, String hostname, String port, String database, String username, String password) {
        this.plugin = plugin;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Open the connection to a MySQL database
     *
     * @return true if successfull opened connection
     */
    public boolean openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.username, this.password);
            System.out.println("Successfully established the connection with MySQL!");
            return true;
        } catch (final SQLException e) {
            System.out.println("Error while trying to connect with MySQL! Reason: " + e.getMessage());
        } catch (final ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
        }
        System.out.println("Error while trying to connect with MySQL!");
        Bukkit.shutdown();
        return false;
    }

    /**
     * Check the connection to a MySQL database
     *
     * @return true if closed
     */
    public boolean isClosed() {
        try {
            return this.connection.isClosed();
        } catch (final SQLException e) {
            System.out.println("Error while trying to check the connection status. Reason: " + e.getMessage());
        }
        return true;
    }

    /**
     * Close the connection to a MySQL database
     *
     * @return true if successfull closed connection
     */
    public boolean closeConnection() {
        try {
            this.connection.close();
            return true;
        } catch (final SQLException e) {
            System.out.println("Error while trying to close the connection. Reason: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the connection to a MySQL
     *
     * @return connection to MySQL
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Create initial tables in MySQL
     */
    public void createMySQL() {
        try {
            Statement statementCreation = connection.createStatement();

            /* Core - Player, PlayTime, PlayersVisible, Chat, Points, NumberBans, NumberJoins, NumberKicks, NumberReports */
            final ResultSet resultadoCore = connection.getMetaData().getTables(null, null, DBCore, null);
            if (resultadoCore.next()) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).bold() + DBCore + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Encontrada!");
            } else {
                statementCreation.executeUpdate("CREATE TABLE IF NOT EXISTS " + DBCore + " (Player VARCHAR(100), PlayTime int(20), PlayersVisible TINYINT(1), Chat TINYINT(1), Points int(20), NumberBans int(3), NumberJoins int(20), NumberKicks int(3), NumberReports int(10));");
                System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).bold() + DBCore + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Criada!");
            }

            /* GameCore - Player, PlayTime, Wins, Losses, Kills, Deaths */
            final ResultSet resultadoGameCore = connection.getMetaData().getTables(null, null, DBGameCore, null);
            if (resultadoGameCore.next()) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).bold() + DBGameCore + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Encontrada!");
            } else {
                statementCreation.executeUpdate("CREATE TABLE IF NOT EXISTS " + DBGameCore + " (Player VARCHAR(100), PlayTime int(20), Wins int(20), Losses int(3), Kills int(20), Deaths int(3));");
                System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).bold() + DBGameCore + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Criada!");
            }

            /* GameCore - GameNumber, Winner, StartDate, EndDate, InitialPlayers, GameChat, GameEvents */
            final ResultSet resultadoGameInfo = connection.getMetaData().getTables(null, null, DBGameInfo, null);
            if (resultadoGameInfo.next()) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).bold() + DBGameInfo + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Encontrada!");
            } else {
                statementCreation.executeUpdate("CREATE TABLE IF NOT EXISTS " + DBGameInfo + " (GameNumber VARCHAR(100), Winner VARCHAR(100), StartDate VARCHAR(100), EndDate VARCHAR(100), InitialPlayers VARCHAR(250), GameChat MEDIUMTEXT, GameEvents MEDIUMTEXT);");
                System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).bold() + DBGameInfo + ":" + Ansi.ansi().fg(Ansi.Color.WHITE).boldOff() + " Criada!");
            }
        } catch (final SQLException e) {
            System.out.println("Error while trying to create the MySQL. Reason: " + e.getMessage());
        }
    }

    /**
     * Create the player dat of a given player using UUID.
     *
     * @param uuid uuid of the player
     * @return true if successfull
     */
    public boolean createPlayerDat(UUID uuid) {
        try {
            final String coreStatement = "SELECT * FROM " + DBCore + " WHERE Player = '" + uuid + "';";
            final String gameStatement = "SELECT * FROM " + DBGameCore + " WHERE Player = '" + uuid + "';";
            final ResultSet resultCore = this.connection.createStatement().executeQuery(coreStatement);
            final ResultSet resultGame = this.connection.createStatement().executeQuery(gameStatement);
            if (resultGame.next()) {
                if (resultCore.next()) {
                    return plugin.playerManager.addPlayerDat(new PlayerDat(uuid, new Date(), resultGame.getLong("PlayTime"), resultCore.getInt("Points"), resultGame.getInt("Wins"), resultGame.getInt("Losses"), resultGame.getInt("Kills"), resultGame.getInt("Deaths")));
                } else {
                    return plugin.playerManager.addPlayerDat(new PlayerDat(uuid, new Date(), resultGame.getLong("PlayTime"), 0, resultGame.getInt("Wins"), resultGame.getInt("Losses"), resultGame.getInt("Kills"), resultGame.getInt("Deaths")));
                }
            } else {
                return plugin.playerManager.addPlayerDat(new PlayerDat(uuid, new Date(), 0, 0, 0, 0, 0, 0));
            }
        } catch (final SQLException e) {
            System.out.println("Error while trying to create PlayerDat! Reason: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update the information on MySQL of a player
     *
     * @param playerDat player dat to be updated on MySQL
     * @return true if successfull
     * @see PlayerDat
     */
    public boolean updateMySQLPlayerDat(PlayerDat playerDat) {
        try {
            // Update Core
            final String coreStatement = "SELECT * FROM " + DBCore + " WHERE Player = '" + playerDat.getUUID() + "';";
            final ResultSet resultCore = this.connection.createStatement().executeQuery(coreStatement);
            if (resultCore.next()) {
                final String coreUpdate = "UPDATE " + DBCore + " SET Points = " + playerDat.getPoints() + " WHERE Player = '" + playerDat.getUUID() + "';";
                this.connection.createStatement().executeUpdate(coreUpdate);
            } else {
                final String coreUpdate = "INSERT INTO " + DBCore + " (Player, PlayTime, PlayersVisible, Chat, Points, NumberBans, NumberJoins, NumberKicks, NumberReports) VALUES ('" + playerDat.getUUID() + "', " + playerDat.getPlayTime() + ", 0, 0, " + playerDat.getPoints() + ", 0, 1, 0, 0);";
                this.connection.createStatement().executeUpdate(coreUpdate);
            }
            // Update Game
            final String gameStatement = "SELECT * FROM " + DBGameCore + " WHERE Player = '" + playerDat.getUUID() + "'";
            final ResultSet resultGame = this.connection.createStatement().executeQuery(gameStatement);
            if (resultGame.next()) {
                final String gameUpdate = "UPDATE " + DBGameCore + " SET PlayTime = " + playerDat.getPlayTime() + ", Wins = " + playerDat.getWins() + ", Losses = " + playerDat.getLosses() + ", Kills = " + playerDat.getKills() + ", Deaths = " + playerDat.getDeaths() + " WHERE Player = '" + playerDat.getUUID() + "';";
                this.connection.createStatement().executeUpdate(gameUpdate);
            } else {
                final String gameUpdate = "INSERT INTO " + DBGameCore + " (Player, PlayTime, Wins, Losses, Kills, Deaths) VALUES ('" + playerDat.getUUID() + "', " + playerDat.getPlayTime() + ", " + playerDat.getWins() + ", " + playerDat.getLosses() + ", " + playerDat.getKills() + ", " + playerDat.getDeaths() + ");";
                this.connection.createStatement().executeUpdate(gameUpdate);
            }
        } catch (final SQLException e) {
            System.out.println("Error while trying to update the MySQL! Reason: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Update the information on MySQL regarding a game
     *
     * @param arenaDat arena dat to be inserted on MySQL
     * @return true if successfull
     * @see ArenaDat
     */
    public boolean updateMySQLArenaDat(ArenaDat arenaDat) {
        try {
            final String infoCreate = "INSERT INTO " + DBGameInfo + " (GameNumber, Winner, StartDate, EndDate, InitialPlayers, GameChat, GameEvents) VALUES ('" + arenaDat.getGameNumber() + "', '" + arenaDat.getWinner() + "', '" + arenaDat.getStartDate() + "', '" + arenaDat.getEndDate() + "', '" + ConvertersAPI.convertListToString(arenaDat.getInitialPlayers(), ",") + "', '" + ConvertersAPI.convertListToString(arenaDat.getGameChat(), "\r\n") + "', '" + ConvertersAPI.convertListToString(arenaDat.getGameEvents(), "\r\n") + "');";
            this.connection.createStatement().executeUpdate(infoCreate);
        } catch (final SQLException e) {
            System.out.println("Error while trying to update the MySQL! Reason: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Get the last game number of the server
     *
     * @return last played game number
     */
    public String lastGameNumber() {
        try {
            String lastGame = "";
            final String lastGameStatement = "SELECT GameNumber FROM " + DBGameInfo + ";";
            final ResultSet resultLastGame = this.connection.createStatement().executeQuery(lastGameStatement);
            while (resultLastGame.next()) {
                String gameNumber = resultLastGame.getString("GameNumber");
                if (lastGame.length() == gameNumber.length()) {
                    int compResult = lastGame.compareTo(gameNumber);
                    if (compResult < 0)
                        lastGame = gameNumber;
                } else if (lastGame.length() < gameNumber.length()) {
                    lastGame = gameNumber;
                }
            }
            return lastGame;
        } catch (final SQLException e) {
            System.out.println("Error while trying to get the last game number from the MySQL! Reason: " + e.getMessage());
            return null;
        }
    }
}
