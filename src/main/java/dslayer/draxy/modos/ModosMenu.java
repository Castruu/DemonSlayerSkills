package dslayer.draxy.modos;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModosMenu implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("modos")){
                ((Player) sender).openInventory(menuConstructor(((Player) sender)));
            }
        }
        return false;
    }

    public Inventory menuConstructor(Player player){
        final Inventory gui = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Menu de Modos");
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).name("").build();
        for(String string : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            if(player.hasPermission(ConfigurationUpdate.modePermission.get(string))) {
                ItemStack m = new ItemBuilder(ConfigurationUpdate.modeItemID.get(string)).name(ConfigurationUpdate.modeItemNames.get(string)).lore(ConfigurationUpdate.modeItemLore.get(string)).build();
                gui.setItem(ConfigurationUpdate.modeItemSlot.get(string), m);
            }
        }
        for(int i = 0; i < gui.getContents().length; i++) {
            if(gui.getContents()[i] == null) {
                gui.setItem(i, glass);
            }
        }
        return gui;
    }

}
