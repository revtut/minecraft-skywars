package net.RevTut.Skywars.arena;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by João on 31/10/2014.
 */
public class ArenaDat {

    /* Game Info */
    private String winner; // This string is in fact the UUID
    private Date startDate;
    private Date stopDate;
    private List<String> initialPlayers = new ArrayList<String>(); // This string is in fact the UUID of the players with which the game started
    private List<String> gameChat = new ArrayList<String>(); // Player's Chat
    private List<String> gameEvents = new ArrayList<String>(); // Game Events

    /* Get's */
    public String getWinner() {
        return winner;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public List<String> getInitialPlayers() {
        return initialPlayers;
    }

    public List<String> getGameChat() {
        return gameChat;
    }

    public List<String> getGameEvents() {
        return gameEvents;
    }

    /* Set's */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public void setInitialPlayers(List<String> initialPlayers) {
        this.initialPlayers = initialPlayers;
    }

    public void setGameChat(List<String> gameChat) {
        this.gameChat = gameChat;
    }

    public void setGameEvents(List<String> gameEvents) {
        this.gameEvents = gameEvents;
    }

    /* Add's */
    public void addInitialPlayer(String player) {
        this.initialPlayers.add(player);
    }

    public void addGameChat(String message) {
        this.gameChat.add(message);
    }

    public void addGameEvent(String event) {
        this.gameEvents.add(event);
    }
}
