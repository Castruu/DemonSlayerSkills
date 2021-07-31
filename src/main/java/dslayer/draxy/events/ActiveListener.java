package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.commands.DatabaseUtils;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.events.actmethods.*;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;


public class ActiveListener implements CommandExecutor {

    @EventHandler
    public static void onInteract(PlayerInteractEvent e) {

    }

    private static HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        IActiveMethods activeMethods = null;
        DatabaseUtils activeLevel = new DatabaseUtils();
        double damage = (NBTManager.nbtManager.readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcWilI") * (1 + (activeLevel.getLevel(player, "active") / 10F)) * ConfigurationUpdate.swordSkillsDamage);
            if(DSMain.getInstance().getHasActiveBreath().containsKey(player.getUniqueId())) {
                if(!(cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis())) {
                    switch (DSMain.getInstance().getHasActiveBreath().get(player.getUniqueId())) {
                        case "Modos.Nevoa":
                            activeMethods = new Nevoa();
                            break;
                        case "Modos.Trovao":
                            activeMethods = new Trovao();
                            break;
                        case "Modos.Vento":
                            activeMethods = new Vento();
                            break;
                        case "Modos.Besta":
                            activeMethods = new Besta();
                            break;
                        case "Modos.Pedra":
                            activeMethods = new Pedra();
                            break;
                        case "Modos.Divina":
                            activeMethods = new Divina();
                            break;
                        case "Modos.Som":
                            activeMethods = new Som();
                            break;
                        case "Modos.Lua":
                            activeMethods = new Lua();
                            break;
                        case "Modos.Chama":
                            activeMethods = new Chama();
                            break;
                        case "Modos.Serpente":
                            activeMethods = new Serpente();
                            break;
                        case "Modos.Amor":
                            activeMethods = new Amor();
                            break;
                        case "Modos.Agua":
                            activeMethods = new Agua();
                            break;
                        case "Modos.Inseto":
                            activeMethods = new Inseto();
                            break;
                    }
                    if(activeMethods != null) {
                        activeMethods.spawnAttack(player, damage);
                        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + ConfigurationUpdate.ultCooldown * 1000);
                    }
                } else {
                    long timeRemainingResp = cooldown.get(player.getUniqueId())
                            - System.currentTimeMillis();
                    int timeCooldownResp = (int) (timeRemainingResp / 1000);
                    player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
                            + "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
                            + ChatColor.DARK_GRAY + " para usar essa skill novamente");
                }

            } else player.sendMessage(ChatColor.RED + "Sem nenhuma respiração ativa!");
        return false;
    }
}
