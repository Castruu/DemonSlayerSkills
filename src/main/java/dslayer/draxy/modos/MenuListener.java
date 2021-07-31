package dslayer.draxy.modos;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class MenuListener implements Listener {

    @EventHandler
    public static void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.translateAlternateColorCodes('&', e.getInventory().getTitle()).equals(ChatColor.AQUA + "Menu de Modos")) {
            if(ConfigurationUpdate.modeItemBySlot.containsKey(e.getSlot())) {
                e.setCancelled(true);
                player.closeInventory();
                player.performCommand(ConfigurationUpdate.modeByCommand.get(ConfigurationUpdate.modeItemBySlot.get(e.getSlot())));
                return;
            }
            e.setCancelled(true);
        }
    }


}
