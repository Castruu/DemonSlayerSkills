package dslayer.draxy.events.kekkijutsus;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class CircleKekki implements IKekkiMethod {

    @Override
    public void spawnKekki(Player player, double damage) {
        new BukkitRunnable() {
            int count = 0;
            double radius = 2.5;
            @Override
            public void run() {
                count ++;
                player.getNearbyEntities(5, 0, 5).forEach(entity -> {
                    Location loc = entity.getLocation();

                    if (entity instanceof LivingEntity) {
                        for (double t = 0; t <= 2.5 * 2 * Math.PI; t += (2.5 * 2 * Math.PI) / 40) {
                            double y = 0;
                            double x = (radius * Math.cos(t)), z = (radius * Math.sin(t));
                            loc.add(x, y, z);
                            player.getWorld().spigot().playEffect(loc, Effect.TILE_BREAK, 35, 14, 0, 0, 0, 5, 2, 20);
                            loc.subtract(x, y, z);
                            y = 1;
                            loc.add(x, y, z);
                            player.getWorld().spigot().playEffect(loc, Effect.TILE_BREAK, 35, 14, 0, 0, 0, 5, 2, 20);
                            entity.setVelocity(new Vector(0, 0, 0));
                            loc.subtract(x, y, z);
                        }

                        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> ((Damageable) entity).damage(damage, player));
                        radius -= 0.5;
                    }
                });
                if (count > 4) cancel();
            }
        }.runTaskTimer(DSMain.getInstance(), 0, 2);

    }
}
