package dslayer.draxy.events.passives;

import dslayer.draxy.DSMain;
import dslayer.draxy.utils.PassivasMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.ScriptException;


public class IncreaseDMG implements IPassives{
    @Override
    public void activePassive(Player player) throws ScriptException {
        PassivasMethods passivasMethods = new PassivasMethods();
        DSMain.getInstance().getNivel().add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> {
            DSMain.getInstance().getNivel().remove(player.getUniqueId());
            getEntry("Passivas.Skill3.offmsg");
        }, (long) passivasMethods.multiplierFirst(player, "Skill3.actiontime", "skill3"));
    }
}
