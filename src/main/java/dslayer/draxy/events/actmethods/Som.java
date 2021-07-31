package dslayer.draxy.events.actmethods;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Som implements IActiveMethods{

    @Override
    public void spawnAttack(Player player, double damage){
        World world = player.getWorld();
        Location location = player.getLocation();
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {

        for(int i = 0; i <2; i++) {
            double radius = 6; float speed = 0;
            Location location3 = new Location(world, 0, 0, 0);
        for(double y = 0; y <= 5; y+=0.5) {
            for(double t = 0; t <= radius*Math.PI*2; t+=radius*Math.PI*2/40) {
                double x = Math.cos(t) * radius;
                double z = Math.sin(t) * radius;
                location3.setX(location.getX() + x);
                location3.setY(location.getY() + y);
                location3.setZ(location.getZ() + z);
                speed++;
                world.spigot().playEffect(location3, Effect.NOTE, 0, 0, 0, 0, 0, speed, 5, 20);
            }
            radius-=0.5;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.getScheduler().runTask(DSMain.getInstance(), () ->  player.getNearbyEntities(10, 2, 10).forEach(e -> {
                if(e.equals(player)) return;
                location3.setY(location.getY());
                if(e.getLocation().distance(location3) < 6){
                    if(e instanceof LivingEntity) {
                        ((Damageable) e).damage(damage);
                    }
                }
            }));
        }
        }});

    }


}
