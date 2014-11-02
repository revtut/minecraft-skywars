package net.RevTut.Skywars.libraries.bypasses;

import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by waxcoder on 02-11-2014.
 */

public class RespawnBypass{

    public void RespawnScreen(PlayerDeathEvent e)
    {
//        final Player player = e.getEntity();
//
//        {
//            public void run()
//            {
//                if (player.isDead()) {
//                    try
//                    {
//                        Object nmsPlayer = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
//                        Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
//                        Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
//                        for (Object ob : enumClass.getEnumConstants()) {
//                            if (ob.toString().equals("PERFORM_RESPAWN")) {
//                                packet = packet.getClass().getConstructor(new Class[] { enumClass }).newInstance(new Object[] { ob });
//                            }
//                        }
//                        Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
//                        con.getClass().getMethod("a", new Class[] { packet.getClass() }).invoke(con, new Object[] { packet });
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

}
