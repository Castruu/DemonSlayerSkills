package dslayer.draxy.events.passives;

import dslayer.draxy.DSMain;
import dslayer.draxy.utils.PassivasMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.ScriptException;



public class RegenHealth implements IPassives{
    @Override
    public void activePassive(Player player) throws ScriptException {
        PassivasMethods passivasMethods = new PassivasMethods();
        double repeater = passivasMethods.multiplierFirst(player, "Skill1.actiontime", "skill1") / DSMain.getInstance().getConfig().getInt("Passivas.Skill1.repeater");
        new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                if(index >= repeater) {
                    player.sendMessage(getEntry("Passivas.Skill1.offmsg"));
                    cancel();
                    return;
                }
                try {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmcheal body " + passivasMethods.multiplierFirst(player, "Skill1.magnitude", "skill1") + " " + player.getName());
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                index++;
            }
        }.runTaskTimerAsynchronously( DSMain.getInstance(), 0 ,20*DSMain.getInstance().getConfig().getInt("Passivas.Skill1.repeater"));

    }
}
