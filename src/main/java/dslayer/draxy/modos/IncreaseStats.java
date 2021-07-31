package dslayer.draxy.modos;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IncreaseStats {

    public static void increaseStatus(String path, Player player){
        int stats = ConfigurationUpdate.modeStats.get(path);
        System.out.println(stats);
        boolean isBreath = ConfigurationUpdate.isBreathOrNo.get(path);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmca3 " + stats + " " + player.getName());
        if(isBreath) DSMain.getInstance().getHasActiveBreath().put(player.getUniqueId(), "Modos." + path);
        player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD + "] "
                + ChatColor.GREEN + "O modo foi ativado!");
        Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmca3 -" + stats + " " + player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers clearSkins " + player.getName());
                    player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD + "] "
                            + ChatColor.DARK_RED + "O modo foi desativado!");
                    if(isBreath) DSMain.getInstance().getHasActiveBreath().remove(player.getUniqueId());
                    ActivateMode.getLink().get(path).remove(player.getUniqueId());
                    },
                ConfigurationUpdate.modeActiveTime.get(path) * 20);
    }



}
