package dslayer.draxy.modos;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnExitJoin implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        for(String key : ActivateMode.getLink().keySet()) {
            if(ActivateMode.getLink().get(key).containsKey(e.getPlayer().getUniqueId())) {
                int stats = ConfigurationUpdate.modeStats.get(key);
                System.out.println(stats);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmca3 " + -stats  + " " + e.getPlayer().getName());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for(String key : ActivateMode.getLink().keySet()) {
            if(ActivateMode.getLink().get(key).containsKey(e.getPlayer().getUniqueId())) {
                int stats = ConfigurationUpdate.modeStats.get(key);
                System.out.println(stats);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmca3 " + stats  + " " + e.getPlayer().getName());
            }
        }
    }
}
