package net.revtut.skywars.utils;

import net.revtut.skywars.player.PlayerDat;

import java.util.Random;

/**
 * Reward Enum.
 *
 * <P>All the rewards that a player may have.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public enum Reward {

    /**
     * Participation Reward.
     * Points earned per game played. This is not the final amount as it depends on a luck factor
     * and depends either on the amount of players killed.
     * PE = points earned
     * PG = points per game
     * K = kills
     * N = number of players
     * F = random percentage (0 - 25%)
     * Formula: PE = PG + PG * ( K / N ) + PG * F
     */
    PARTICIPATION(500),

    /**
     * Kill Reward.
     * Points earned per kill. This is not the final amount as it depends on a luck factor
     * and depends either on the amount of players already killed.
     * PE = points earned
     * PK = points per kill
     * K = kills
     * N = number of players
     * F = random percentage (0 - 10%)
     * Formula: PE = PK + PK * (K / N) + PK * F
     */
    KILL(100),

    /**
     * Winning Reward.
     * Points earned per win. This is not the final amount as it depends on a luck factor
     * and depends either on the amount of players killed.
     * PE = points earned
     * PW = points per win
     * K = kills
     * N = number of players
     * F = random percentage (between 0 - 50%)
     * Formula: PE = PW + PW * ( K / N ) + PW * F
     */
    WIN(1500);

    /**
     * Random Class
     */
    public final Random rand = new Random();

    /**
     * Base points of the reward
     */
    private final int points;

    /**
     * Constructor of Reward
     *
     * @param points base points of the reward
     */
    Reward(int points) {
        this.points = points;
    }

    /**
     * Returns the points of the reward
     *
     * @return the default base reward points
     */
    private final int getPoints() {
        return points;
    }

    /**
     * Calculate the reward points
     *
     * @param playerDat player dat to calculate the points
     * @return the reward points
     */
    public final int calculatePoints(PlayerDat playerDat) {
        int points = 0;
        switch (this) {
            case PARTICIPATION:
                points += this.getPoints();
                points += this.getPoints() * ((float) playerDat.getGameKills() / 24);
                points += this.getPoints() * ((float) rand.nextInt(11) / 100);
                break;
            case KILL:
                points += this.getPoints();
                points += this.getPoints() * ((float) playerDat.getGameKills() / 24);
                points += this.getPoints() * ((float) rand.nextInt(26) / 100);
                break;
            case WIN:
                points += this.getPoints();
                points += this.getPoints() * ((float) playerDat.getGameKills() / 24);
                points += this.getPoints() * ((float) rand.nextInt(51) / 100);
                break;
        }

        return points;
    }
}
