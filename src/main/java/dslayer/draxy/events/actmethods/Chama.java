package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Chama implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        new BukkitRunnable() {
            final Location loc = player.getLocation().add(0, 1.7, 0);
            double t = 0;
            double n = 0;
            public void run() {
                for(double i = 0; i <= 1.5*Math.PI*2; i = i + 1.5*Math.PI/120) {
                    t = t + Math.PI/25;
                    n = n + Math.PI/25;
                    double x = 2*0.3*t+0.5;
                    double y = Math.cos(t+i);
                    double z = Math.sin(t+i);
                    Vector vec = new Vector(x, y, z);
                    rotateAroundAxisZ(vec, -loc.getPitch());
                    rotateAroundAxisY(vec, -loc.getYaw() - 90);
                    loc.add(vec);
                    player.getWorld().spigot().playEffect(loc, Effect.FLAME, 0, 0, 0, 0, 0, 0, 10, 10);
                    loc.subtract(vec);
                    t+=2;
                    Vector vector = new Vector(2*0.3*t, 0, 0);
                    rotateAroundAxisZ(vector, -loc.getPitch());
                    rotateAroundAxisY(vector, -loc.getYaw() - 90);
                    loc.add(vector);
                    player.getWorld().spigot().playEffect(loc, Effect.FLAME, 0, 0, 0, 0, 0, 0, 10, 10);
                    loc.subtract(vector);
                    t-=2;
                    Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> {
                        for (LivingEntity livingEntity : player.getWorld().getLivingEntities()) {
                            if(livingEntity.getLocation().distance(loc.clone().add(vector)) <= 2.5) {
                                livingEntity.damage(damage);
                            }
                        }
                    });
                    try {
                        TimeUnit.MILLISECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.runTaskAsynchronously(DSMain.getInstance());


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

    private Vector rotateAroundAxisZ(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }
}




