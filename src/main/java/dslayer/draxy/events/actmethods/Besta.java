package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.concurrent.TimeUnit;

public class Besta implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {

            Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> player.getNearbyEntities(5, 0, 5).forEach(e -> {
                if (e.equals(player)) return;
                if (!(e instanceof LivingEntity)) return;
                Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> player.getWorld().spigot().playEffect(e.getLocation().add(0, 1, 0),
                        Effect.MAGIC_CRIT, 0, 0, 5, 1, 5, 0, 12, 20));
                    player.setVelocity(e.getLocation().subtract(player.getLocation()).toVector());
                Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> ((Damageable) e).damage(damage), 10L);
                player.getWorld().playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 10, 10);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }));

    }
    

}
