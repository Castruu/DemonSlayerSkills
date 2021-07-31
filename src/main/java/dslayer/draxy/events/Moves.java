package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.events.moves.DashBackwards;
import dslayer.draxy.events.moves.DashFoward;
import dslayer.draxy.events.moves.FlyingFart;
import dslayer.draxy.events.moves.IMoves;
import dslayer.draxy.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Moves implements Listener {
    IMoves moves;
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID puuid = player.getUniqueId();
        DSMain.getInstance().getMvSkill().put(puuid, 0);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        ItemStack moveskill = new ItemBuilder(Material.FEATHER)
                .name(ChatColor.AQUA + "MoveSet")
                .build();
        Player player = e.getPlayer();
        Effect effect = Effect.FIREWORKS_SPARK;
        UUID puuid = player.getUniqueId();

        String msg = DSMain.getEntry("MoveSkills.DashFoward.msg"),
        msg2 = DSMain.getEntry("MoveSkills.DashBackward.msg"),
        msg1 = DSMain.getEntry("MoveSkills.Levitation.msg");
        if (player.getItemInHand().isSimilar(moveskill))  {
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if (DSMain.getInstance().getMvSkill().containsKey(e.getPlayer().getUniqueId())) {
                    if (DSMain.getInstance().getMvSkill().get(e.getPlayer().getUniqueId()).equals(0)) {
                        DSMain.getInstance().getMvSkill().put(puuid, 1);
                        player.sendMessage(msg2);
                    } else if (DSMain.getInstance().getMvSkill().get(e.getPlayer().getUniqueId()).equals(1)) {
                        DSMain.getInstance().getMvSkill().put(puuid, 2);
                        player.sendMessage(msg1);
                    } else if (DSMain.getInstance().getMvSkill().get(e.getPlayer().getUniqueId()).equals(2)) {
                        DSMain.getInstance().getMvSkill().put(puuid, 0);
                        player.sendMessage(msg);
                    }
                }
            }
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.PHYSICAL)) {
                if(DSMain.getInstance().getMvSkill().get(puuid).equals(0)){
                    moves = new DashFoward();
                    spawnMove(moves, mvskill1, player, effect, "DashFoward");
                }
                if(DSMain.getInstance().getMvSkill().get(puuid).equals(1)){
                    moves = new DashBackwards();
                    spawnMove(moves, mvskill2, player, effect, "DashBackward");
                }
                if(DSMain.getInstance().getMvSkill().get(puuid).equals(2)){
                    moves = new FlyingFart();
                    spawnMove(moves, mvskill3, player, effect, "Levitation");
                }

            }
        }
    }

    private void spawnMove(IMoves moves, HashMap<UUID, Long> skills ,Player player, Effect effect, String path) {
        if (skills.containsKey(player.getUniqueId())
                && skills.get(player.getUniqueId()) > System.currentTimeMillis()) {
            long timeRemainingResp = skills.get(player.getUniqueId())
                    - System.currentTimeMillis();
            int timeCooldownResp = (int) (timeRemainingResp / 1000);
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                    + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                    + ChatColor.DARK_GRAY + " para usar essa skill novamente");
        } else {
            moves.setupMove(player, effect);
            skills.put(player.getUniqueId(), System.currentTimeMillis() + (DSMain.getInstance().getConfig().getInt("MoveSkills." + path + ".Cooldown") * 1000));
        }
    }


    private final HashMap<UUID, Long> mvskill1 = new HashMap<>();
    private final HashMap<UUID, Long> mvskill2 = new HashMap<>();
    private final HashMap<UUID, Long> mvskill3 = new HashMap<>();


}
