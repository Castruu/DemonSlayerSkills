package dslayer.draxy.events.swskill;

import dslayer.draxy.DSMain;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class XCross implements ISwordSkills {
    @Override
    public void spawnSkill(Player player, double damage, Effect effect, int id, int data) {
        if(player.isPermissionSet("blackflame")) {
            ineParticle(player, damage, id, data);
            return;
        }
        new BukkitRunnable() {
            double degree = (player.getLocation().getYaw() % 360) + 180;
            final Vector direction = player.getLocation().getDirection().normalize();
            double y = 2.5;
            @Override
            public void run() {
                for (double i = 0; i < Math.PI*2; i+=0.25) {
                    Location loc = player.getLocation();
                    degree -= 5;
                    double radian1 = Math.toRadians(degree) ;
                    double x = radian1 + Math.toRadians(direction.getX());
                    double z = radian1 + Math.toRadians(direction.getZ());
                    loc.add(Math.cos(x)*2,y,Math.sin(z)*2);
                    if(effect == Effect.COLOURED_DUST) {
                        player.getWorld().spigot().playEffect(loc, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                     } else
                        player.getWorld().spigot().playEffect(loc, effect, id, data, 0, 0, 0, 0, 10, 10);
                    loc.subtract(Math.cos(x)*2,y,Math.sin(z)*2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y-=0.075;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskAsynchronously(DSMain.getInstance());
        new BukkitRunnable() {
            double degree = (player.getLocation().getYaw() % 360);
            final Vector direction = player.getLocation().getDirection().normalize();
            double y = 2.5;
            @Override
            public void run() {
                for (double i = 0; i < Math.PI*2; i+=0.25) {
                    Location loc = player.getLocation();
                    degree += 5;
                    double radian1 = Math.toRadians(degree) ;
                    double x = radian1 + Math.toRadians(direction.getX());
                    double z = radian1 + Math.toRadians(direction.getZ());
                    loc.add(Math.cos(x)*2,y,Math.sin(z)*2);
                    if(effect == Effect.COLOURED_DUST) {
                        player.getWorld().spigot().playEffect(loc, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                    } else
                    player.getWorld().spigot().playEffect(loc, effect, id, data, 0, 0, 0, 0, 10, 10);
                loc.subtract(Math.cos(x)*2,y,Math.sin(z)*2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y-=0.075;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskLaterAsynchronously(DSMain.getInstance(), 10);


    }

    private void ineParticle(Player player, double damage, int id, int data) {
        new BukkitRunnable() {
            double degree = (player.getLocation().getYaw() % 360) + 180;
            final Vector direction = player.getLocation().getDirection().normalize();
            double y = 2.5;
            @Override
            public void run() {
                for (double i = 0; i < Math.PI*2; i+=0.25) {
                    Location loc = player.getLocation();
                    degree -= 5;
                    double radian1 = Math.toRadians(degree) ;
                    double x = radian1 + Math.toRadians(direction.getX());
                    double z = radian1 + Math.toRadians(direction.getZ());
                    loc.add(Math.cos(x)*2,y,Math.sin(z)*2);
                        player.getWorld().spigot().playEffect(loc.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                        player.getWorld().spigot().playEffect(loc, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                     loc.subtract(Math.cos(x)*2,y,Math.sin(z)*2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y-=0.075;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskAsynchronously(DSMain.getInstance());
        new BukkitRunnable() {
            double degree = (player.getLocation().getYaw() % 360);
            final Vector direction = player.getLocation().getDirection().normalize();
            double y = 2.5;
            @Override
            public void run() {
                for (double i = 0; i < Math.PI*2; i+=0.25) {
                    Location loc = player.getLocation();
                    degree += 5;
                    double radian1 = Math.toRadians(degree) ;
                    double x = radian1 + Math.toRadians(direction.getX());
                    double z = radian1 + Math.toRadians(direction.getZ());
                    loc.add(Math.cos(x)*2,y,Math.sin(z)*2);
                        player.getWorld().spigot().playEffect(loc.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                        player.getWorld().spigot().playEffect(loc, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                     loc.subtract(Math.cos(x)*2,y,Math.sin(z)*2);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y-=0.075;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskLaterAsynchronously(DSMain.getInstance(), 10);
    }
}
