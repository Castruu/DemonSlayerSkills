package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class Serpente implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        new BukkitRunnable() {


            @Override
            public void run() {
                player.getNearbyEntities(10, 2, 10).forEach(e -> {
                    if (e instanceof LivingEntity) {
                    Location location = e.getLocation();
                    final double radius = 1.5;
                        for (double y = 0; y < 3 * Math.PI; y += Math.PI * 3 / 30) {
                            double x = Math.cos(y) * radius;
                            double z = Math.sin(y) * radius;
                            location.add(x, y, z);
                            e.getWorld().spigot().playEffect(location, Effect.INSTANT_SPELL, 0, 0, 0, 0, 0, 0, 5, 5);
                            location.subtract(x, y, z);
                        }
                        if (e instanceof Player)
                            ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15, 5));
                        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> ((Damageable) e).damage(damage));
                };
            });
            }
        }.runTaskAsynchronously(DSMain.getInstance());
    }

}
