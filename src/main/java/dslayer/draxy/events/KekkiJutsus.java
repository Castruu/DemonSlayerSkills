package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.events.kekkijutsus.*;
import dslayer.draxy.utils.ItemBuilder;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KekkiJutsus implements Listener {
    private final static KekkiJutsus kekkiJutsus = new KekkiJutsus();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(player.hasPermission("oni.use")) {
            for(String string : DSMain.getInstance().getPermlist()) {
                if(player.hasPermission(string)) {
                    kekkiactive.put(player.getUniqueId(), DSMain.getInstance().getKekkiperms().get(string));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getPlayer().hasPermission("oni.use")) return;
        Player player = e.getPlayer();
        ItemStack nezukoitem = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.RED + "KekkiJutsu Nezuko").build();
        double damage = (NBTManager.nbtManager.readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcWilI") * ConfigurationUpdate.kekkiJutsuDamage);
        if (e.getItem() != null && e.getItem().isSimilar(nezukoitem)) {
            if (getInstance().playersnezuko.contains(player.getUniqueId())) {
                Bukkit.getScheduler().runTask(DSMain.getInstance(), () -> {
                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        playerHasActive.put(player, true);
                        if (!playerListHashMap.containsKey(player)) {
                            playerListHashMap.put(player, new ArrayList<>());
                            playerListHashMap.get(player).add(player.getLocation());
                        } else if (playerListHashMap.get(player).contains(player.getLocation())) {
                            player.sendMessage(ChatColor.RED + "Lugar já marcado!");
                        } else if (playerListHashMap.get(player).size() == 5) {
                            Bukkit.getScheduler().cancelTask(locationIDHashMap.get(playerListHashMap.get(player).get(0)));
                            playerListHashMap.get(player).remove(0);
                            playerListHashMap.get(player).add(player.getLocation());
                        } else if (playerListHashMap.containsKey(player))
                            playerListHashMap.get(player).add(player.getLocation());
                        new BukkitRunnable() {
                            final Location loc = player.getLocation();
                            int count = 0;

                            @Override
                            public void run() {
                                locationIDHashMap.put(loc, getTaskId());
                                loc.add(0, 1, 0);
                                player.getWorld().spigot().playEffect(loc, Effect.TILE_BREAK, 35, 14, 0, 0, 0, 0, 10, 10);
                                loc.subtract(0, 1, 0);
                                if (!playerHasActive.get(player)) {
                                    playerListHashMap.get(player).clear();
                                    this.cancel();
                                }
                                if (count < 10)

                                    count++;
                            }
                        }.runTaskTimerAsynchronously(DSMain.getInstance(), 0, 4);
                    } else {
                        if (playerHasActive.containsKey(player)) {
                            if (playerHasActive.get(player)) {
                                playerHasActive.put(player, false);
                                for (Location locs : playerListHashMap.get(player)) {
                                    locs.getWorld().spigot().playEffect(locs, Effect.EXPLOSION_LARGE, 0, 0, 1, 1, 1, 0, 10, 10);
                                    locs.getWorld().getLivingEntities().forEach(entity -> {
                                        if (entity.getLocation().distance(locs) < 4) {
                                            if (!entity.equals(player))
                                                entity.damage(damage, player);
                                        }
                                    });
                                }
                            }
                            getInstance().playersnezuko.remove(player.getUniqueId());
                            player.getInventory().remove(nezukoitem);
                        }
                    }
                });
            } else player.getInventory().remove(player.getItemInHand());
        }
    }
    public void spawnSkill(Player player, List<String> stringList) {
        IKekkiMethod kekkijutsus;
        ItemStack nezukoitem = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.RED + "KekkiJutsu Nezuko").build();
        double damage = (NBTManager.nbtManager.readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcWilI") * ConfigurationUpdate.kekkiJutsuDamage);
        if (!kekkiactive.containsKey(player.getUniqueId())) {
            kekkiactive.put(player.getUniqueId(), DSMain.getInstance().getKekkiperms().get(stringList.get(0)));
        }
        if (kekkiactive.get(player.getUniqueId()) == 1) {
            kekkijutsus = new SphereKekki();
            spawnKekki(kekkijutsus, sphere, player, "Sphere", damage);
        } else if (kekkiactive.get(player.getUniqueId()) == 2) {
            kekkijutsus = new CircleKekki();
            spawnKekki(kekkijutsus, circle, player, "Circle", damage);
        } else if (kekkiactive.get(player.getUniqueId()) == 3) {
            kekkijutsus = new StunKekki();
            spawnKekki(kekkijutsus, tree, player, "Tree", damage);
        } else if (kekkiactive.get(player.getUniqueId()) == 4) {
            kekkijutsus = new RuiKekki();
            spawnKekki(kekkijutsus, spider, player, "Spider", damage);
        } else if (kekkiactive.get(player.getUniqueId()) == 5) {
            if(nezuko.containsKey(player.getUniqueId()) && nezuko.get(player.getUniqueId()) > System.currentTimeMillis()) {
                long timeRemainingResp = nezuko.get(player.getUniqueId())
                        - System.currentTimeMillis();
                int timeCooldownResp = (int) (timeRemainingResp / 1000);
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                        + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                        + ChatColor.DARK_GRAY + " para usar essa skill novamente");
            } else {
                getInstance().playersnezuko.add(player.getUniqueId());
                player.getInventory().addItem(nezukoitem);
                nezuko.put(player.getUniqueId(), System.currentTimeMillis() + ConfigurationUpdate.kekkiJutsusCooldown.get("Nezuko") * 1000);
            }
            }

        }

    public void changeKekki(Player player, List<String> stringList) {
        if(!stringList.isEmpty())
                if (!kekkiactive.containsKey(player.getUniqueId())) {
                    kekkiactive.put(player.getUniqueId(), DSMain.getInstance().getKekkiperms().get(stringList.get(0)));
                } else
                for (int i = 0; i < stringList.size(); i++) {
                    if (i == stringList.size() - 1) {
                        kekkiactive.put(player.getUniqueId(), DSMain.getInstance().getKekkiperms().get(stringList.get(0)));
                        player.sendMessage(DSMain.getInstance().getKekkisentence().get(DSMain.getInstance().getKekkiperms().get(stringList.get(0))));
                        return;
                    }
                    else if (kekkiactive.get(player.getUniqueId()).intValue() == DSMain.getInstance().getKekkiperms().get(stringList.get(i)).intValue()) {
                        kekkiactive.put(player.getUniqueId(), DSMain.getInstance().getKekkiperms().get(stringList.get(i + 1)));
                        player.sendMessage(DSMain.getInstance().getKekkisentence().get(DSMain.getInstance().getKekkiperms().get(stringList.get(i + 1))));
                        return;
                    }
                }
    }

    public void setupList(Player player, List<String> list) {
        for (String string : DSMain.getInstance().getPermlist()) {
            if(player.hasPermission(string)) {
                list.add(string);
            }
        }
    }

    private void spawnKekki(IKekkiMethod kekki, HashMap<UUID, Long> cooldown, Player player, String path, double damage) {
        if(cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                long timeRemainingResp = cooldown.get(player.getUniqueId())
                        - System.currentTimeMillis();
                int timeCooldownResp = (int) (timeRemainingResp / 1000);
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                        + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                        + ChatColor.DARK_GRAY + " para usar essa skill novamente");
        }
       else {
        kekki.spawnKekki(player, damage);
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + ConfigurationUpdate.kekkiJutsusCooldown.get(path) * 1000);
        }
    }

    private final HashMap<UUID, Long> circle = new HashMap<>(),
    sphere = new HashMap<>(),
    tree = new HashMap<>(),
    spider = new HashMap<>(),
    nezuko = new HashMap<>();
    private final HashMap<Location, Integer> locationIDHashMap = new HashMap<>();
    private final HashMap<Player, ArrayList<Location>> playerListHashMap = new HashMap<>();
    private final HashMap<Player, Boolean> playerHasActive = new HashMap<>();
    HashMap<UUID, Integer> kekkiactive = new HashMap<>();
    public static KekkiJutsus getInstance() {
        return kekkiJutsus;
    }
    public final List<UUID> playersnezuko = new ArrayList<>();
}
