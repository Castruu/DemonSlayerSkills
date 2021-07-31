package dslayer.draxy.events.swskill;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Helix implements ISwordSkills {
    @Override
    public void spawnSkill(Player player, double damage, Effect effect, int id, int data) {
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            if(player.isPermissionSet("blackflame")) {
                ineParticle(player, damage, id, data);
                return;
            }
            Location loc = player.getLocation();
            double radius = 1;
            for (double y = 0; y <= Math.PI * 2 * radius * 4; y += 0.05) {
                double x = (radius * Math.cos(y));
                double z = (radius * Math.sin(y));
                loc.add(x, y/4, z);
                radius += 0.005;
                if(effect == Effect.COLOURED_DUST) {
                    player.getWorld().spigot().playEffect(loc, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                }
                else
                    player.getWorld().spigot().playEffect(loc, effect, id, data, 0, 0, 0, 0, 5, 15);
                try {
                    TimeUnit.MILLISECONDS.sleep((long) 1.5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loc.subtract(x, y/4, z);
            }
            applyDamage(player, 3, damage);
        });
    }

    private void ineParticle(Player player, double damage, int id, int data) {
        Location loc = player.getLocation();
        double radius = 1;
        for (double y = 0; y <= 15; y += 0.05) {
            double x = (radius * Math.cos(y));
            double z = (radius * Math.sin(y));
            loc.add(x, y/4, z);
            radius += 0.005;
            player.getWorld().spigot().playEffect(loc.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
            player.getWorld().spigot().playEffect(loc, Effect.FLAME, id, data, 0, 0, 0, 0, 10, 3);
            try {
                TimeUnit.MILLISECONDS.sleep((long) 1.5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loc.subtract(x, y/4, z);
        }
        applyDamage(player, 3, damage);
    }
}
