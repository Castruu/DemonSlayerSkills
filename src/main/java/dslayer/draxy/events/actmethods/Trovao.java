package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Trovao implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage){
        DSMain.getInstance().getActivebreath().put(player.getUniqueId(), Effect.TILE_BREAK);
        for(Entity e : player.getNearbyEntities(10, 2, 10)) {
            if (e.equals(player)) continue;
            if (e instanceof LivingEntity) {
                Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> player.getWorld().strikeLightning(e.getLocation()));
                ((Damageable) e).damage(damage);
            }
        }
    }
}
