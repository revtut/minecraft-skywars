package net.RevTut.skywar.kits;

import org.bukkit.Material;

/**
 * Kits Enum.
 *
 * <P>All the kits available in the minigame.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public enum Kit {

    /**
     * Engineer
     */
    ENGINEER("§6Engineer", Material.IRON_BLOCK, 900),
    /**
     * Hacker
     */
    HACKER("§6Hacker", Material.GOLD_NUGGET, 1200),
    /**
     * Ninja
     */
    NINJA("§6Ninja", Material.FISHING_ROD, 400),
    /**
     * Tatical
     */
    TATICAL("§6Tatical", Material.ENDER_PEARL, 700),
    /**
     * Guardian
     */
    GUARDIAN("§6Guardian", Material.IRON_BOOTS, 500),
    /**
     * Lifestealer
     */
    LIFESTEALER("§6Lifestealer", Material.REDSTONE, 1500);
    /**
     * Screamer
     */
    //SCREAMER("§6Screamer", Material.BONE, 1200);

    /**
     * Displayname of the kit
     */
    private final String displayName;

    /**
     * Material that represents the kit
     */
    private final Material material;

    /**
     * Cost of the kit
     */
    private final int cost;

    /**
     * Constructor of Kits
     *
     * @param displayName display name of the kit
     * @param material representative material of the kit
     * @param cost cost of the kit
     */
    Kit(String displayName, Material material, int cost) {
        this.displayName = displayName;
        this.material = material;
        this.cost = cost;
    }

    /**
     * Returns the display name of the kit
     *
     * @return the display name of that kit
     */
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the representative material of the kit
     *
     * @return the material of that kit
     */
    public final Material getMaterial() {
        return material;
    }

    /**
     * Returns the cost of the kit
     *
     * @return the cost of that kit
     */
    public final int getCost() {
        return cost;
    }
}
