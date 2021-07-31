package dslayer.draxy.events.moves;

import dslayer.draxy.DSMain;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FlyingFart implements IMoves{
    @Override
    public void setupMove(Player player, Effect effect) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count >= 45) cancel();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(new Vector(0.5, 1, 0.5)));
                Location location = player.getLocation();
                player.getWorld().spigot().playEffect(location, effect, 0, 0, 0.1F, 0.1F, 0.1F, 0, 5, 20);
            }
        }.runTaskTimerAsynchronously(DSMain.getInstance(), 0, 1);
    }
}
