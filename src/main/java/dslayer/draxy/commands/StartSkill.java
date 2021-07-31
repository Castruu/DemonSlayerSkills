package dslayer.draxy.commands;

import dslayer.draxy.events.KekkiJutsus;
import dslayer.draxy.events.SwordSkills;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartSkill implements CommandExecutor {

    SwordSkills skills = new SwordSkills();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        List<String> list = new ArrayList<>();
        KekkiJutsus.getInstance().setupList(player, list);
        if(command.getName().equalsIgnoreCase("startskill")){
            if(player.hasPermission("espadachim.use"))
                skills.spawnSkill(player);
            else if(player.hasPermission("oni.use"))
                KekkiJutsus.getInstance().spawnSkill(player, list);

        } else if (command.getName().equalsIgnoreCase("changeskill")){
            if(player.hasPermission("espadachim.use"))
                skills.changeSkill(player);
            else if(player.hasPermission("oni.use"))
            KekkiJutsus.getInstance().changeKekki(player, list);
        }

        return false;
    }
}
