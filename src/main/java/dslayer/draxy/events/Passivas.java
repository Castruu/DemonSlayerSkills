package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.events.passives.IPassives;
import dslayer.draxy.events.passives.IncreaseDMG;
import dslayer.draxy.events.passives.RegenConSpeed;
import dslayer.draxy.events.passives.RegenHealth;
import dslayer.draxy.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.UUID;

public class Passivas implements Listener {
    IPassives passives;
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID puuid = player.getUniqueId();
        DSMain.getInstance().getPassivas().put(puuid, 0);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws ScriptException {
        ItemStack passivas = new ItemBuilder(Material.EMERALD)
                .name(ChatColor.LIGHT_PURPLE + "Habilidades Passivas")
                .build();
        Player player = e.getPlayer();
        UUID puuid = player.getUniqueId();
        Action action = e.getAction();
        String msg1 = getEntry("Passivas.Skill1.msg"), msg2 = getEntry("Passivas.Skill2.msg"), msg3 = getEntry("Passivas.Skill3.msg");
         if (player.getItemInHand().isSimilar(passivas)) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (DSMain.getInstance().getPassivas().containsKey(puuid)) {
                    if (getSkill(puuid) == 0) {
                        DSMain.getInstance().getPassivas().put(puuid, 1);
                        player.sendMessage(msg2);
                    }
                    else if (getSkill(puuid) == 1) {
                        DSMain.getInstance().getPassivas().put(puuid, 2);
                        player.sendMessage(msg3);
                    }
                    else if (getSkill(puuid) == 2) {
                        DSMain.getInstance().getPassivas().put(puuid, 0);
                        player.sendMessage(msg1);
                    }
                }
            } else {
                if (DSMain.getInstance().getPassivas().containsKey(puuid)) {
                    if (getSkill(puuid) == 0) {
                        this.passives = new RegenHealth();
                        setupSkill(passives, skill1, "Skill1", player);
                    }
                    else if (getSkill(puuid) == 1) {
                        this.passives = new RegenConSpeed();
                        setupSkill(passives, skill2, "Skill2", player);
                    }
                    else if (getSkill(puuid) == 2) {
                        this.passives = new IncreaseDMG();
                        setupSkill(passives, skill3, "Skill3", player);
                    }
                }
            }
        }
    }

    public static String getEntry(String key) {
        return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
    }

    public static Integer getSkill(UUID puuid) {
        return DSMain.getInstance().getPassivas().get(puuid);
    }



    private void setupSkill(IPassives skill, HashMap<UUID, Long> hashmap, String path, Player player) throws ScriptException {
        if (hashmap.containsKey(player.getUniqueId())
                && hashmap.get(player.getUniqueId()) > System.currentTimeMillis()) {
            long timeRemainingResp = hashmap.get(player.getUniqueId())
                    - System.currentTimeMillis();
            int timeCooldownResp = (int) (timeRemainingResp / 1000);
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                    + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                    + ChatColor.DARK_GRAY + " para usar essa skill novamente");
        } else {
            skill.activePassive(player);
            hashmap.put(player.getUniqueId(), System.currentTimeMillis() + (DSMain.getInstance().getConfig().getInt("Passivas." + path + ".Cooldown") * 1000));
        }
    }

    HashMap<UUID, Long> skill1 = new HashMap<>(), skill2 = new HashMap<>(), skill3 = new HashMap<>();


}