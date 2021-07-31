package dslayer.draxy.events.swskill;

import dslayer.draxy.DSMain;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class UpperCircle implements ISwordSkills {
    @Override
    public void spawnSkill(Player player, double damage, Effect effect, int id, int data) {
        if(player.isPermissionSet("blackflame")) {
            ineParticle(player, damage, id,data);
            return;
        }
        new BukkitRunnable() {
            double angle = 90;
            final Location loc = player.getLocation().add(0, 1, 0);
            @Override
            public void run() {
                for (double i = 0; i < 10; i+=0.5) {
                    Vector offset = loc.getDirection().clone().multiply(Math.cos(Math.toRadians(angle)) * 2);
                    offset.setY(Math.sin(Math.toRadians(angle)) * 2);
                    loc.add(offset);
                    if(effect == Effect.COLOURED_DUST) {
                        player.getWorld().spigot().playEffect(loc, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                   } else
                        player.getWorld().spigot().playEffect(loc, effect, id, data, 0, 0, 0, 0, 10, 10);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loc.subtract(offset);
                    angle-=4.5;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskAsynchronously(DSMain.getInstance());
    }

    public void ineParticle(Player player, double damage, int id, int data) {
        new BukkitRunnable() {
            double angle = 90;
            Location loc = player.getLocation().add(0, 1, 0);
            @Override
            public void run() {
                for (double i = 0; i < 10; i+=0.5) {
                    Vector offset = loc.getDirection().clone().multiply(Math.cos(Math.toRadians(angle)) * 2);
                    offset.setY(Math.sin(Math.toRadians(angle)) * 2);
                    loc.add(offset);
                    player.getWorld().spigot().playEffect(loc.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                    player.getWorld().spigot().playEffect(loc, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loc.subtract(offset);
                    angle-=4.5;
                }
                applyDamage(player, 2, damage);
            }
        }.runTaskAsynchronously(DSMain.getInstance());
    }
}
