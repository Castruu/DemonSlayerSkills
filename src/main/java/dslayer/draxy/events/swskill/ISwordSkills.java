package dslayer.draxy.events.swskill;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface ISwordSkills {
    void spawnSkill(Player player, double damage, Effect effect, int id, int data);
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
