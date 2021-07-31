package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class Divina implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        for (Entity entity : player.getNearbyEntities(10, 2, 10)) {
            if (entity.equals(player)) continue;
            if (entity instanceof LivingEntity) {
                Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
                    double radius = 2;
                    Location location = entity.getLocation();
                    for (double y1 = 30; y1 >= 15; y1 -= 0.05) {
                        double x1 = (radius * Math.cos(y1));
                        double z1 = (radius * Math.sin(y1));
                        location.add(x1, y1/4, z1);
                        radius -= 0.005;
                        player.getWorld().spigot().playEffect(location, Effect.TILE_BREAK, 95, 6, 0, 0, 0, 0, 10, 10);
                        location.subtract(x1, y1/4, z1);
                        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> entity.setVelocity(new Vector(0, 0, 0)));
                        try {
                            TimeUnit.MILLISECONDS.sleep((long) 1.5);
                        } catch (InterruptedException x) {
                            x.printStackTrace();
                        }
                    }
                    for (double y1 = 15; y1 >= 0; y1 -= 0.05) {
                        double x1 = (radius * Math.cos(y1));
                        double z1 = (radius * Math.sin(y1));
                        location.add(x1, y1/4, z1);
                        radius += 0.005;
                        player.getWorld().spigot().playEffect(location, Effect.SPELL, 0, 0, 0, 0, 0, 0, 1, 5);
                        location.subtract(x1, y1/4, z1);
                        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> entity.setVelocity(new Vector(0, 0, 0)));
                        try {
                            TimeUnit.MILLISECONDS.sleep((long) 1.5);
                        } catch (InterruptedException x) {
                            x.printStackTrace();
                        }
                    }

                });
                Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> ((Damageable) entity).damage(damage), 20L);
            }

        }


    }

}
