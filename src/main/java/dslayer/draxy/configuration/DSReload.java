package dslayer.draxy.configuration;

import dslayer.draxy.DSMain;
import dslayer.draxy.modos.ActivateMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import java.lang.reflect.Field;

public class DSReload implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0.isOp()) {
			if (arg1.getName().equalsIgnoreCase("dsreload")) {
				DSMain.getInstance().reloadConfig();
				arg0.sendMessage(ChatColor.GREEN + "DSlayer Skills reloaded!");
				ConfigurationUpdate.updateConfig();
			}
		}
		return false;
	}

}
