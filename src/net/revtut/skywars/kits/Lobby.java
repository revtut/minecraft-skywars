package net.revtut.skywars.kits;

import net.revtut.skywars.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Lobby Kit.
 *
 * <P>Kit Lobby with useful items while waiting.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class Lobby {

    /**
     * How to Play Book
     */
    private final ItemStack howToPlay = new ItemStack(Material.WRITTEN_BOOK);

    /**
     * Book Credits
     */
    private final String bookCredits = "    §b§lRevTut Network\n" +
            "\n" +
            "        §aWebsite\n" +
            "      §0www.revtut.net\n" +
            "\n" +
            "           §6Store\n" +
            "     §0store.revtut.net\n" +
            "\n" +
            "     §bSocial Networks\n" +
            "         §0/RevTut\n" +
            "\n" +
            "§d§oYour team,";

    /**
     * Exit to Hub
     */
    private final ItemStack exitToHub = new ItemStack(Material.SLIME_BALL);

    /**
     * Give kit lobby to a player
     *
     * @param player player to give the kit
     */
    public void kitLobby(Player player) {
        // Change display name - Exit to Hub
        String displayName = Message.getMessage(Message.EXIT_TO_HUB, player);
        ItemMeta healingPotionMeta = exitToHub.getItemMeta();
        healingPotionMeta.setDisplayName(displayName);
        exitToHub.setItemMeta(healingPotionMeta);

        // Change text of book - How To Play
        ItemStack book = howToPlay.clone();
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setTitle("§6How to Play");
        bookMeta.setAuthor("§aRevTut Network");
        String[] howToPlayDescription = Message.getMessage(Message.HOW_TO_PLAY, player).split("/x");
        for(int i = 0; i < howToPlayDescription.length; i++) {
            bookMeta.addPage(howToPlayDescription[i]);
        }
        bookMeta.addPage(bookCredits);
        book.setItemMeta(bookMeta);

        // Add the items
        player.getInventory().setItem(0, book);
        player.getInventory().setItem(8, exitToHub);
    }

    /**
     * Check if player is trying to connect to hub.
     *
     * @param player    player who used the item
     * @param itemStack item stack used by the player
     * @param action type of action
     */
    public boolean connectToHub(Player player, ItemStack itemStack, Action action) { // MAKES USE OF PLAYER INTERACT EVENT
        if(!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)))
            return false;
        if (itemStack == null)
            return false;
        if (itemStack.getType() == null)
            return false;
        if (itemStack.getType() != Material.SLIME_BALL)
            return false;
        if (!itemStack.hasItemMeta())
            return false;
        if (!itemStack.getItemMeta().hasDisplayName())
            return false;
        String displayName = Message.getMessage(Message.EXIT_TO_HUB, player);
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(displayName))
            return false;

        return true;
    }
}