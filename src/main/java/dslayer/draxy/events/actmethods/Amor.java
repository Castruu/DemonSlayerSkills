package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class Amor implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        World world = player.getWorld();
        player.getNearbyEntities(5, 0, 5).forEach(e -> {
            if (e instanceof LivingEntity) {
            Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
                Location location = e.getLocation();
                for (int t = 0; t <= 30; t++) {
                    location.add(0, 1 ,0);
                    player.getWorld().spigot().playEffect(location, Effect.HEART, 0, 0, 1, 1, 1, 0, 10, 40);

                }
            });
                    ((Damageable) e).damage(damage);
                    e.setFireTicks(15 * 20);
                }

        });
    }
}
