package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.commands.DatabaseUtils;
import dslayer.draxy.utils.MenuBuyKekki;
import dslayer.draxy.utils.MenuSkills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuPoints implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        DatabaseUtils databaseUtils = new DatabaseUtils();
        if (ChatColor.translateAlternateColorCodes('&', e.getInventory().getTitle()).equals(ChatColor.AQUA + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(player, "points"))) {
            switch (e.getSlot()) {
                case 11:
                    doChange("skill1", databaseUtils, player);
                    break;
                case 13:
                    doChange("skill2", databaseUtils, player);
                    break;
                case 15:
                    doChange("skill3", databaseUtils, player);
                    break;
                default:
                    break;

            }
            e.setCancelled(true);
        } else if(ChatColor.translateAlternateColorCodes('&', e.getInventory().getTitle()).equals(ChatColor.DARK_PURPLE + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(player, "points"))) {
            for(String key : DSMain.getInstance().getConfig().getConfigurationSection("KekkiJutsus").getKeys(false)) {
                if(!DSMain.getInstance().getConfig().getBoolean("KekkiJutsus." + key + ".Paid")) continue;
                if(e.getSlot() == DSMain.getInstance().getConfig().getInt("KekkiJutsus." + key + ".Slot")) {
                    buyKekki(DSMain.getInstance().getConfig().getString("KekkiJutsus." + key + ".Perm"), key , databaseUtils, player);
                }
            };
            e.setCancelled(true);
        }
    }
    private void doChange(String path, DatabaseUtils databaseUtils, Player player) {
        MenuSkills menuSkills = new MenuSkills();
        if(databaseUtils.getLevel(player, "points") > 0) {
            databaseUtils.setLevel(player, -1 ,"points");
            databaseUtils.setLevel(player, 1, path);
            Bukkit.getScheduler().runTaskLaterAsynchronously(DSMain.getInstance(), () -> {
                player.closeInventory();
                Inventory gui2 = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(player, "points"));
                menuSkills.menuConstructor(gui2, player);
                player.openInventory(gui2);
            }, 10);
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Sem pontos suficientes para fazer essa melhoria!");
        }
    }
    private void buyKekki(String path, String key , DatabaseUtils databaseUtils, Player player) {
        MenuBuyKekki menuSkills = new MenuBuyKekki();
        if(databaseUtils.getLevel(player, "points") > 0) {
            databaseUtils.setLevel(player, -DSMain.getInstance().getConfig().getInt("KekkiJutsus." + key + ".Cost"), "points");
            Bukkit.getScheduler().runTaskLaterAsynchronously(DSMain.getInstance(), () -> {
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp " + player.getName() + " " + path);
                Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(player, "points"));
                menuSkills.menuConstructor(gui, player);
                player.openInventory(gui);
            }, 10);
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Sem pontos suficientes para fazer essa melhoria!");
        }
    }
}
