package dslayer.draxy.events.moves;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class DashFoward implements IMoves{
    @Override
    public void setupMove(Player player, Effect effect) {
        Location location = player.getLocation();
        Vector vector = location.getDirection().multiply(3);
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            player.setVelocity(player.getEyeLocation().getDirection().multiply(new Vector(5, 0, 5)));
            location.setY(location.getY() + 1);
            for (int i = 0; i <= 2; i++) {
                double x = vector.getX() * i, z = vector.getZ() * i;
                location.add(-x, 0, -z);
                player.getWorld().spigot().playEffect(location, effect, 0, 0, 0.5F, 0.5F, 0.5F, 0, 5, 20);
                location.subtract(-x, 0, -z);
            }
        });
    }
}
