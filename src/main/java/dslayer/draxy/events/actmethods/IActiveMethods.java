package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface IActiveMethods {
    void spawnAttack(Player player, double damage);
    default void applyDamage(Player player, double radius, double damage) {
        Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> {
            for(Entity entity : player.getNearbyEntities(radius, 2, radius)) {
                if (entity instanceof LivingEntity) {
                    ((Damageable) entity).damage(damage, player);
                }
            }
        });
    }
}
