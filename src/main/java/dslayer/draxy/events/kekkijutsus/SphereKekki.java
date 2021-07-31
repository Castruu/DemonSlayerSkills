package dslayer.draxy.events.kekkijutsus;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class SphereKekki implements IKekkiMethod {

    @Override
    public void spawnKekki(Player player, double damage) {
            for (int f = 0; f < 3; f++) {
                new BukkitRunnable() {
                    double i = 0;
                    Effect effect = Effect.FIREWORKS_SPARK;
                    @Override
                    public void run() {
                        i += Math.PI / 50;
                        double random = Math.random();
                        if(random > .5) effect = Effect.WITCH_MAGIC;
                        Location location = player.getLocation();
                        double radius = Math.sin(i) * 3;
                        double y = Math.cos(i) * 5;
                        for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            location.add(x, y, z);
                            player.getWorld().spigot().playEffect(location, effect, 0, 0, 0, 0, 0, 0, 10, 10);
                            location.subtract(x, y, z);
                        }
                        if (i > Math.PI) {
                            this.cancel();
                        }
                    }
                }.runTaskTimerAsynchronously(DSMain.getInstance(), 0, 1);
                if(f != 1) continue;
                Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> player.getNearbyEntities(6, 2, 6).forEach(entity -> {
                    if (entity instanceof LivingEntity) {
                        ((Damageable) entity).damage(damage, player);
                    }
                }));
            }

        }
}
