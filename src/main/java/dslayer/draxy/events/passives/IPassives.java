package dslayer.draxy.events.passives;

import dslayer.draxy.DSMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.script.ScriptException;

public interface IPassives {

    void activePassive(Player player) throws ScriptException;
    default String getEntry(String key) {
        return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
    }
}
