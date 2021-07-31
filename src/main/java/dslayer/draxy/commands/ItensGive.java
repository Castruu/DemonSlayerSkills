package dslayer.draxy.commands;

import dslayer.draxy.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class ItensGive implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(arg3.length == 1) {
			if (arg1.getName().equals("moveskill")) {
				Player target = Bukkit.getServer().getPlayer(arg3[0]);
				ItemStack mvskill = new ItemBuilder(Material.FEATHER)
						.name(ChatColor.AQUA + "MoveSet")
						.build();
				target.getInventory().addItem(mvskill);
			}
			if (arg1.getName().equals("passivas")) {
				Player target = Bukkit.getServer().getPlayer(arg3[0]);
				ItemStack passivas = new ItemBuilder(Material.EMERALD)
						.name(ChatColor.LIGHT_PURPLE + "Habilidades Passivas")
						.build();
				target.getInventory().addItem(passivas);
			}
		}
		return false;
	}


}
