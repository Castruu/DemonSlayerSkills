package dslayer.draxy.events.kekkijutsus;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RuiKekki implements IKekkiMethod {

    @Override
    public void spawnKekki(Player player, double damage) {
        Location location = player.getEyeLocation();
        new BukkitRunnable() {
            Location loc = player.getLocation().add(0, 1, 0);
            Vector move = loc.getDirection().normalize().multiply(2);
            double pitch = loc.getPitch();
            double yaw = loc.getYaw();
            double radius = 0.5;
            double increment = (2 * Math.PI) / 20;
            @Override
            public void run() {
                loc.add(move);
                for (double t = 0; t <= Math.PI * 10; t+= Math.PI * 4/30) {
                    double x = radius * Math.cos(t), z = radius * Math.sin(t);
                    Vector vector = new Vector(x, 0, z);
                    rotateAroundAxisX(vector, pitch + 90);
                    rotateAroundAxisY(vector, -yaw);
                    loc.add(vector);
                    loc.getWorld().spigot().playEffect(loc, Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0, 10, 10);
                    loc.subtract(vector);
                    radius += 0.075;
                }
                Bukkit.getScheduler().runTaskLaterAsynchronously(DSMain.getInstance(), () -> {
                    for (double i = 2.2; i < 20; i++) {
                        Vector move = location.getDirection().normalize().multiply(i);
                        location.add(move);
                        location.getWorld().spigot().playEffect(location.clone(), Effect.FIREWORKS_SPARK, 0, 0, 0.3F, 0.3F, 0.3F, 0, 10, 10);
                        location.subtract(move);
                        for(LivingEntity entity : location.getWorld().getLivingEntities()) {
                            if(entity.equals(player)) continue;
                            if(entity.getLocation().distance(location.clone().add(move)) <= 2) {
                               Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> (entity).damage(damage, player));
                            }
                        }
                    }
                }, 5);
            }
        }.runTaskAsynchronously(DSMain.getInstance());
    }
    private Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
    private HashMap<Location, Material> locationMaterialHashMap = new HashMap<>();
}
