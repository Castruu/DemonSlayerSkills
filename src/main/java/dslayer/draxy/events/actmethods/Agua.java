package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Agua implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            for(double y = 0; y <= 5; y++){
                Location location = player.getLocation();
            for (double radius = 0; radius <= 7; radius++) {
                for (double t = 0; t < 2 * Math.PI * radius; t += (2 * Math.PI * radius) / 20) {
                double x = Math.cos(t) * radius, z = Math.sin(t) * radius;
                    location.add(x, y, z);
                    player.getWorld().spigot().playEffect(location, Effect.WATERDRIP, 0, 0, 0, 0, 0, 0, 5, 10);
                    location.subtract(x, y, z);
                    Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> applyDamage(player, 7, damage));
                }
            }
        }
        });

    }

}
