package dslayer.draxy.database;

import dslayer.draxy.DSMain;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstQuery implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
            try {
                PreparedStatement stm = Connection.con.prepareStatement("INSERT INTO level (uuid, nivel, skill1, skill2, skill3, points, active) VALUES ('" + e.getPlayer().getUniqueId().toString() + "', 0, 0, 0, 0, 0, 0)");
                stm.execute();
                stm.close();
         } catch (SQLException x) {
                x.printStackTrace();
            }
    }

}
