package dslayer.draxy.events.passives;

import dslayer.draxy.DSMain;
import dslayer.draxy.commands.DatabaseUtils;
import dslayer.draxy.utils.PassivasMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.ScriptException;



public class RegenConSpeed implements IPassives{
    DatabaseUtils databaseUtils = new DatabaseUtils();
    @Override
    public void activePassive(Player player) throws ScriptException {
        PassivasMethods passivasMethods = new PassivasMethods();
        double repeater = passivasMethods.multiplierFirst(player, "Skill2.actiontime", "skill2") / DSMain.getInstance().getConfig().getInt("Passivas.Skill2.repeater");
        String amp = DSMain.getInstance().getConfig().getString("Passivas.Skill2.speedboost").replace("[level]", String.valueOf(databaseUtils.getLevel(player, "skill2")));
        int amplifier = Integer.parseInt(amp);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) passivasMethods.multiplierFirst(player, "Skill2.actiontime", "skill2") * 20, amplifier - 1));
        new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                if(index >= repeater) {
                    player.sendMessage(getEntry("Passivas.Skill2.offmsg"));
                    cancel(); return;
                }
                try {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmcheal stamina " + passivasMethods.multiplierFirst(player, "Skill2.magnitude", "skill2") + " " + player.getName());
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                index++;
            }
        }.runTaskTimerAsynchronously(DSMain.getInstance(), 0 ,20*DSMain.getInstance().getConfig().getInt("Passivas.Skill2.repeater"));
    }
}
