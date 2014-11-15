package net.RevTut.Skywars.arena;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ArenaDat Object.
 *
 * <P>A complement to Arena Object, which saves all the information related to a game.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaDat {

    /**
     * Game Number
     */
    private String gameNumber;

    /**
     * Winner of the arena (UUID)
     */
    private String winner = "NULL";

    /**
     * Start date of the game
     */
    private Date startDate;

    /**
     * End date of the game
     */
    private Date endDate;

    /**
     * List with all the initial players (in fact a list with their UUIDs)
     */
    private List<String> initialPlayers = new ArrayList<String>();

    /**
     * List with the game chat history
     */
    private List<String> gameChat = new ArrayList<String>();

    /**
     * List with the game events history
     */
    private List<String> gameEvents = new ArrayList<String>(); // Game Events

    /**
     * Returns the game number
     *
     * @return game number
     */
    public String getGameNumber() {
        return gameNumber;
    }

    /**
     * Returns the winner of the game
     *
     * @return winner of the game
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Returns the start date of the game
     *
     * @return the start date of the game
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date of the game
     *
     * @return the end date of the game
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Returns the initial players of the arena
     *
     * @return the players which were in the arena
     */
    public List<String> getInitialPlayers() {
        return initialPlayers;
    }

    /**
     * Returns a list with the game chat history
     *
     * @return game chat history
     */
    public List<String> getGameChat() {
        return gameChat;
    }

    /**
     * Returns a list with all the game events history
     *
     * @return game events history
     */
    public List<String> getGameEvents() {
        return gameEvents;
    }

    /**
     * Sets the game number
     *
     * @param gameNumber the nem game number
     */
    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }

    /**
     * Sets the winner of the game
     *
     * @param winner winner of the game
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Sets the start date of the game
     *
     * @param startDate start date of the game
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the game
     *
     * @param endDate stop date of the game
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Clears the initial players of the game
     */
    public void clearInitialPlayers() {
        this.initialPlayers.clear();
    }

    /**
     * Clears the game chat history
     */
    public void clearGameChat() {
        this.gameChat.clear();
    }

    /**
     * Clears the game events history
     */
    public void clearGameEvents() {
        this.gameEvents.clear();
    }

    /**
     * Adds a new initial player to the game
     *
     * @param player player to be added
     */
    public void addInitialPlayer(String player) {
        this.initialPlayers.add(player);
    }

    /**
     * Adds a new chat message to the game log
     *
     * @param message message to be added
     */
    public void addGameChat(String message) {
        this.gameChat.add(currentDate() + " " + message);
    }

    /**
     * Adds a new event to the game log
     *
     * @param event event message to be added
     */
    public void addGameEvent(String event) {
        this.gameEvents.add(currentDate() + " " + event);
    }

    /**
     * Gets the current date formated
     *
     * return formated current date
     */
    private String currentDate(){
        Date currentDate = new Date();
        return currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds();
    }
}
