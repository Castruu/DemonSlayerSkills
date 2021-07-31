package dslayer.draxy.commands;

import dslayer.draxy.DSMain;
import dslayer.draxy.database.Connection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public int getLevel(Player target, String path){
        PreparedStatement stm = null;
        try  {
            stm = Connection.con.prepareStatement("SELECT " + path + " FROM level WHERE uuid = ?");
            stm.setString(1, target.getUniqueId().toString());
            ResultSet result = stm.executeQuery();
            int nivel = result.getInt(path);
            stm.close();
            result.close();
            return nivel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void setLevel(Player target, int level, String path){
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            PreparedStatement stm = null;
            int soma = getLevel(target, path) + level;
            try {
                stm = Connection.con.prepareStatement("UPDATE level SET " + path + " = " + soma + " WHERE uuid = ?");
                stm.setString(1, target.getUniqueId().toString());
                stm.executeUpdate();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Adição impossível!");
            }
        });

    }


}
