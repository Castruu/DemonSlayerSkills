package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Pedra implements IActiveMethods {

    @Override
    public void spawnAttack(Player player, double damage) {
        Location location = player.getLocation();
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            float x = 0, z = 0;
            while (x <= 10) {
                x++; z++;
                if (x == 10){
                    for (int i = 0; i <= 5; i++) {
                        location.add(0, i, 0);
                        player.getWorld().spigot().playEffect(location.add(0, i, 0), Effect.TILE_BREAK, 2, 0, x+2, 10, z+2, 0, 10, 50 );
                    }

                } else {
                    player.getWorld().spigot().playEffect(location, Effect.TILE_BREAK, 2, 0, x + 2, 0, z + 2, 0, 50, 50);
                }
                    try {
                    TimeUnit.MILLISECONDS.sleep(100);} catch (Exception e) {e.printStackTrace();}
            }
            Bukkit.getScheduler().runTaskLater(DSMain.getInstance(), () -> applyDamage(player, 10, damage), 5);


        });




    }
}
