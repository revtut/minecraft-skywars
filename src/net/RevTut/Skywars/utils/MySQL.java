package net.RevTut.Skywars.utils;

import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by João on 24/10/2014.
 */
public class MySQL {

    /* Initializers */
    private final String hostname;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    /* Databases */
    private final String DBCore = "Core";
    /* Connection */
    public Connection connection;

    /* Constructor */
    public MySQL(String hostname, String port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /* Open Connection */
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

    /* Check Connection */
    public boolean isClosed() {
        try {
            return this.connection.isClosed();
        } catch (final SQLException e) {
            System.out.println("Error while trying to check the connection status. Reason: " + e.getMessage());
        }
        return true;
    }

    /* Close Connection */
    public boolean closeConnection() {
        try {
            this.connection.close();
            return true;
        } catch (final SQLException e) {
            System.out.println("Error while trying to close the connection. Reason: " + e.getMessage());
        }
        return false;
    }

    /* Get's */
    public Connection getConnection() {
        return this.connection;
    }

    /* Create MySQL */
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
        } catch (final SQLException e) {
            System.out.println("Error while trying to create the MySQL. Reason: " + e.getMessage());
        }
    }

    /* Create PlayerDat */
    public boolean createPlayerDat(UUID uuid) {
        try {
            final String coreStatement = "SELECT * FROM " + DBCore + " WHERE Player = '" + uuid + "';";
            final ResultSet result = this.connection.createStatement().executeQuery(coreStatement);
            if (result.next()) {
                PlayerDat.addPlayerDat(new PlayerDat(uuid, new Date(), result.getLong("PlayTime"), result.getBoolean("PlayersVisible"), result.getBoolean("Chat"), result.getInt("Points"), result.getInt("NumberBans"), result.getInt("NumberJoins"), result.getInt("NumberKicks"), result.getInt("NumberReports")));
            } else {
                PlayerDat.addPlayerDat(new PlayerDat(uuid, new Date(), 0, true, true, 0, 0, 1, 0, 0));
            }
        } catch (final SQLException e) {
            System.out.println("Error while trying to create PlayerDat! Reason: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Update MySQL PlayerDat */
    public boolean updateMySQLPlayerDat(PlayerDat playerDat) {
        try {
            final String coreStatement = "SELECT * FROM " + DBCore + " WHERE Player = '" + playerDat.getUUID() + "'";
            final ResultSet result = this.connection.createStatement().executeQuery(coreStatement);
            if (result.next()) {
                final String coreUpdate = "UPDATE " + DBCore + " SET PlayTime = " + playerDat.getPlayTime() + ", PlayersVisible = " + playerDat.hasPlayersVisible() + ", Chat = " + playerDat.hasChat() + ", Points = " + playerDat.getPoints() + ", NumberBans = " + playerDat.getNumberBans() + ", NumberJoins = " + playerDat.getNumberJoins() + ", NumberKicks = " + playerDat.getNumberKicks() + ", NumberReports = " + playerDat.getNumberReports() + " WHERE Player = '" + playerDat.getUUID() + "';";
                this.connection.createStatement().executeUpdate(coreUpdate);
            } else {
                final String coreUpdate = "INSERT INTO " + DBCore + " (Player, PlayTime, PlayersVisible, Chat, Points, NumberBans, NumberJoins, NumberKicks, NumberReports) VALUES ('" + playerDat.getUUID() + "', " + playerDat.getPlayTime() + ", " + playerDat.hasPlayersVisible() + ", " + playerDat.hasChat() + ", " + playerDat.getPoints() + ", " + playerDat.getNumberBans() + ", " + playerDat.getNumberJoins() + ", " + playerDat.getNumberKicks() + ", " + playerDat.getNumberReports() + ");";
                this.connection.createStatement().executeUpdate(coreUpdate);
            }
        } catch (final SQLException e) {
            System.out.println("Error while trying to update the MySQL! Reason: " + e.getMessage());
            return false;
        }
        return true;
    }
}
