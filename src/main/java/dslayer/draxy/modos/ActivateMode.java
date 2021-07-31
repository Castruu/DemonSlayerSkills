package dslayer.draxy.modos;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.UUID;

public class ActivateMode extends BukkitCommand {

    public ActivateMode(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String command = s.toLowerCase();
            if(ConfigurationUpdate.modeCommand.containsKey(command)) {
                final String key = ConfigurationUpdate.modeCommand.get(command);
                setupCooldownGeral(link.get(key), player, key);
            }
        }
        return false;
    }

    public void setupCooldownGeral(HashMap<UUID, Long> cooldownlocal, Player player, String path) {
        boolean isBreath = ConfigurationUpdate.isBreathOrNo.get(path);
        if(player.hasPermission(ConfigurationUpdate.modePermission.get(path))) {
            if (cooldownlocal.containsKey(player.getUniqueId()) && cooldownlocal.get(player.getUniqueId()) > System.currentTimeMillis()) {
                long timeRemainingResp = cooldownlocal.get(player.getUniqueId())
                        - System.currentTimeMillis();
                int timeCooldownResp = (int) (timeRemainingResp / 1000);
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                        + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                        + ChatColor.DARK_GRAY + " para usar essa skill novamente");
            } else if (!(player.hasPermission("multibreath")) && isBreath  && DSMain.getInstance().getCooldownGeral().containsKey(player.getUniqueId()) && DSMain.getInstance().getCooldownGeral().get(player.getUniqueId()) > System.currentTimeMillis()) {
                long timeRemainingResp = DSMain.getInstance().getCooldownGeral().get(player.getUniqueId())
                        - System.currentTimeMillis();
                int timeCooldownResp = (int) (timeRemainingResp / 1000);
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                        + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                        + ChatColor.DARK_GRAY + " para usar essa skill novamente");
            } else {
                if(isBreath) {
                    DSMain.getInstance().getCooldownGeral().put(player.getUniqueId(), getCooldownGeral(path));
                }
                cooldownlocal.put(player.getUniqueId(), getCooldown(path));
                IncreaseStats.increaseStatus(path, player);
                for (String aws : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getConfigurationSection(path).getStringList("AWs")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "armourers setSkin " + player.getName() + " 8 \"" + aws + "\"");
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Você não tem esse modo!");
        }
    }

    public static HashMap<String, HashMap<UUID, Long>> getLink() {
        return link;
    }

    private static final HashMap<String, HashMap<UUID, Long>> link = new HashMap<>();
    public static void setLink() {
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            link.put(key, new HashMap<>());
        }
    }

    private long getCooldown(String path) {
        return System.currentTimeMillis() + ConfigurationUpdate.modeCooldown.get(path) * 1000;
    }
    private long getCooldownGeral(String path) {
        return System.currentTimeMillis() + ConfigurationUpdate.modeActiveTime.get(path) * 1000;
    }

}

