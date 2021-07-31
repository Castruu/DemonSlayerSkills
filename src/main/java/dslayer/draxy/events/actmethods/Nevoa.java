package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Nevoa implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage) {
        Chunk chunk = player.getLocation().getChunk();
        Location location = player.getLocation();
        location.add(0, 1 ,0);
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            float x = 0, z = 0;
            while (x <= 10) {
                x++; z++;

                player.getWorld().spigot().playEffect(location, Effect.CLOUD, 0,0, x + 2, 0, z + 2, 0, 30, 50 );


            try {TimeUnit.MILLISECONDS.sleep(100);} catch (Exception e) {e.printStackTrace();}
        }
            applyDamage(player, 10, damage);

        });




    }

    }
