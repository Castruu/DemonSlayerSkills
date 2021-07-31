package dslayer.draxy.utils;

import dslayer.draxy.DSMain;
import dslayer.draxy.commands.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuBuyKekki implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if(sender instanceof Player) return false;
        DatabaseUtils databaseUtils = new DatabaseUtils();
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            Player target = Bukkit.getServer().getPlayer(strings[0]);
            Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(target, "points"));
            if(cmd.getName().equals("kekkimenu")){
                menuConstructor(gui, target);
                target.openInventory(gui);
            }
        });
        return false;
    }


    public void menuConstructor(Inventory gui, Player player) {
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).name("").build();
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("KekkiJutsus").getKeys(false)) {
            if(!DSMain.getInstance().getConfig().getBoolean("KekkiJutsus." + key + ".Paid")) continue;
            if(!(player.hasPermission(DSMain.getInstance().getConfig().getString("KekkiJutsus." + key + ".Perm")))) {
                ItemStack item = new ItemBuilder(DSMain.getInstance().getConfig().getInt("KekkiJutsus." + key + ".Item")).name(getEntry("KekkiJutsus." + key + ".ItemName")).lore(getEntryList("KekkiJutsus." + key + ".ItemLore", "KekkiJutsus." + key + ".Cost")).build();
                gui.setItem(DSMain.getInstance().getConfig().getInt("KekkiJutsus." + key + ".Slot"), item);
            }
        }
        for(int i = 0; i < gui.getContents().length; i++) {
            if(gui.getContents()[i] == null) {
                gui.setItem(i, glass);
            }
        }
    }

    public String getEntry(String key) {
        return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
    }

    public String[] getEntryList(String key, String path) {
        String [] lore = DSMain.getInstance().getConfig().getStringList(key).toArray(new String[0]);
        for (int i = 0; i <= DSMain.getInstance().getConfig().getStringList(key).size() - 1; i++){
            lore[i] = ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getStringList(key).get(i).replace("%points%", String.valueOf(DSMain.getInstance().getConfig().getInt(path))));
        }
        return lore;

    }

}
