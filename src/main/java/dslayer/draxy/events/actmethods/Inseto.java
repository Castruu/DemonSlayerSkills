package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Inseto implements IActiveMethods{
    List<Integer> list = new ArrayList<>();
    List<Entity> liste = new ArrayList<>();
    @Override
    public void spawnAttack(Player player, double damage) {
            spawnButterfly(player);
        new BukkitRunnable() {
            Location location = player.getLocation();
            Vector direction = location.getDirection();
            int angle = 180;
            int count = 0;
            double y = 1;
            @Override
            public void run() {
                count++;
                angle += 90;
                for (int i = 0; i < 10; i++) {
                    angle -= 5;
                    double radian1 = Math.toRadians(angle);
                    double x = radian1 + Math.toRadians(direction.getX());
                    double z = radian1 + Math.toRadians(direction.getZ());
                    location.add(Math.cos(x)*2, y,Math.sin(z)*2);
                    player.getWorld().spigot().playEffect(location, Effect.PORTAL, 0, 0 , 0 ,0,0,0,10, 10);
                    for(Entity entity : player.getNearbyEntities(15, 2, 15)) {
                        if(!(entity instanceof LivingEntity) || liste.contains(entity)) continue;
                        if(entity.getLocation().distance(location.clone().add(location.clone().getDirection().multiply(i))) < 2) {
                            ((Damageable) entity).damage(damage);
                            liste.add(entity);
                        }
                    }
                    player.getWorld().spigot().playEffect(location.clone().add(location.getDirection().multiply(i)), Effect.PORTAL, 0, 0 , 0 ,0,0,0,10, 10);
                    location.subtract(Math.cos(x)*2,y,Math.sin(z)*2);
                }
                angle += 50;
                if(count == 4) {
                    y++;
                }
                if (count > 7) {
                    Bukkit.getScheduler().cancelTask(list.get(0));
                    cancel();
                }
            }
        }.runTaskTimer(DSMain.getInstance(), 0, 5);
        }

        private void spawnButterfly(Player player) {
            new BukkitRunnable() {
                int counter = 0;
                public void run() {
                    list.add(0, this.getTaskId());
                    // condition for me to end it, I didn't want to copy your ArrayList (MAKE THAT A SET!) stuff
                    if (counter++ > 60) {
                        cancel();
                        // prevent last iteration
                        return;
                    }
                    // This will iterate a full circle (360 degree / 2*PI in radian), in steps of PI/54.
                    // A finer step will draw more points
                    for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 54) {
                        // this will return a CLONE of the player location
                        Location location = player.getLocation();
                        Vector vector = location.getDirection().normalize().multiply(-0.2);
                        location = location.add(vector);
                        // this part is the same for both x and y, only compute it once
                        double bracket = (
                                Math.exp(
                                        Math.cos(theta)
                                ) - 2 * Math.cos(4 * theta) - Math.pow(
                                        Math.sin(theta / 12), 5
                                )
                        );
                        double x = Math.sin(theta) * bracket * 1.3;
                        double y = Math.cos(theta) * bracket * 1.3 + 1; // make it slightly bigger and a bit higher

                        // we do not need to remove it again, as you obtain a new location "location" anyways.
                        location.add(rotateAroundAxisY(new Vector(x, y, 0), -location.getYaw()));
                        // The spigot way of spawning particles, 1.9+
                        player.getWorld().spigot().playEffect(location, Effect.PORTAL, 0, 0, 0, 0, 0, 0, 1, 100);
                    }
                }
            }.runTaskTimer(DSMain.getInstance(), 0, 5);
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
}
