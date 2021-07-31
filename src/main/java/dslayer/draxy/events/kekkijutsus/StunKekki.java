package dslayer.draxy.events.kekkijutsus;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class StunKekki implements IKekkiMethod {

    @Override
    public void spawnKekki(Player player, double damage) {
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            Location loc = player.getLocation().add(0, 1, 0);
            for (int i = 0; i < 20; i++) {
                Vector vector = loc.getDirection().normalize().multiply(i);
                loc.add(vector);
                player.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 0, 0, 0, 0, 0, 10, 10);
                loc.subtract(vector);
                Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> {
                    for (LivingEntity entity : player.getWorld().getLivingEntities()) {
                        if(entity.equals(player)) continue;;
                        if (entity.getLocation().distance(loc.clone().add(vector)) < 2) {
                            Location location = entity.getLocation();
                            new BukkitRunnable() {
                                int count = 0;
                                @Override
                                public void run() {
                                    count++;
                                    entity.teleport(location);
                                    if(count >= 50) cancel();
                                }
                            }.runTaskTimer(DSMain.getInstance(), 0, 1);
                            if(entity instanceof Player) {
                                Player target = (Player) entity;
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers setSkin "  + target.getName() + " 6 \"treefeets\"");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers setSkin "  + target.getName() + " 6 \"treechest\"");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers setSkin "  + target.getName() + " 6 \"treelegs\"");
                                Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers clearSkins " + target.getName()), 100);
                            }
                        }
                        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> ((Damageable) entity).damage(damage, player));
                    }
                });

            }
        });
    }
}
