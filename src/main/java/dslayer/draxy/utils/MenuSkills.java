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


public class MenuSkills implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) return false;
        DatabaseUtils databaseUtils = new DatabaseUtils();
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            Player player = Bukkit.getPlayer(args[0]);
            Inventory gui = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Pontos disponíveis: " + ChatColor.GOLD + databaseUtils.getLevel(player, "points"));
            if(cmd.getName().equals("respmenu")){
                menuConstructor(gui, player);
                player.openInventory(gui);
            }
        });
        return false;
    }

    public void menuConstructor(Inventory gui, Player player) { //11, 13, 15
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).name("").build();
        ItemStack sk1 = new ItemBuilder(Material.BLAZE_ROD).name(getEntry("Passivas.Skill1.ItemName")).lore(getEntryList("Passivas.Skill1.ItemLore", player, "skill1")).build();
        ItemStack sk2 = new ItemBuilder(Material.GOLD_NUGGET).name(getEntry("Passivas.Skill2.ItemName")).lore(getEntryList("Passivas.Skill2.ItemLore", player, "skill2")).build();
        ItemStack sk3 = new ItemBuilder(Material.NETHER_STAR).name(getEntry("Passivas.Skill3.ItemName")).lore(getEntryList("Passivas.Skill3.ItemLore", player, "skill3")).build();
        for(int i = 0; i < gui.getSize(); i++){
            if(i == 11) gui.setItem(i, sk1);
            else if(i == 13) gui.setItem(i, sk2);
            else if(i == 15) gui.setItem(i, sk3);
            else gui.setItem(i, glass);

        }
    }

    public String getEntry(String key) {
            return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
    }

    public String[] getEntryList(String key, Player player, String path) {
        DatabaseUtils databaseUtils = new DatabaseUtils();
            String [] lore = DSMain.getInstance().getConfig().getStringList(key).toArray(new String[0]);
            for (int i = 0; i <= DSMain.getInstance().getConfig().getStringList(key).size() - 1; i++){
                lore[i] = ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getStringList(key).get(i).replace("[level]", String.valueOf(databaseUtils.getLevel(player, path))));
            }
        return lore;

    }


}
