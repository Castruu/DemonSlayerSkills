package dslayer.draxy.events;

import dslayer.draxy.DSMain;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.events.swskill.*;
import dslayer.draxy.utils.PassivasMethods;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.UUID;



public class SwordSkills implements Listener {
	PassivasMethods passivasMethods = new PassivasMethods();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID puuid = player.getUniqueId();
		if(player.hasPermission("espadachim.use"))
		DSMain.getInstance().getSkill().put(puuid, 0);

	}


	public void changeSkill(Player player) {
		if(!DSMain.getInstance().getSkill().containsKey(player.getUniqueId())) DSMain.getInstance().getSkill().put(player.getUniqueId(), 0);
		final UUID puuid = player.getUniqueId();
					Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
					if (DSMain.getInstance().getSkill().containsKey(player.getUniqueId())) {
						String msg = " ";
						if(DSMain.getInstance().getSkill().get(player.getUniqueId()) != 4)
						msg = ConfigurationUpdate.tradeSwordSkillMessages.get(DSMain.getInstance().getSkill().get(player.getUniqueId()) + 1);
						if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(0)) {
							DSMain.getInstance().getSkill().put(puuid, 1);
							player.sendMessage(msg);
						} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(1)) {
							DSMain.getInstance().getSkill().put(puuid, 2);
							player.sendMessage(msg);
						} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(2)) {
							DSMain.getInstance().getSkill().put(puuid, 3);
							player.sendMessage(msg);
						} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(3)) {
							DSMain.getInstance().getSkill().put(puuid, 4);
							player.sendMessage(msg);
						} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(4)) {
							msg = ConfigurationUpdate.tradeSwordSkillMessages.get(0);
							DSMain.getInstance().getSkill().put(puuid, 0);
							player.sendMessage(msg);
						}
					}
				});
	}

	public void spawnSkill(Player player) {
		ISwordSkills skill;
		double damage = (NBTManager.nbtManager.readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcWilI") * DSMain.getInstance().getConfig().getInt("SwordSkills.Multiplicador"));
		try {
			if (DSMain.getInstance().getNivel().contains(player.getUniqueId()))
				damage = damage * passivasMethods.multiplierFirst(player, "Skill3.magnitude", "skill3");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		int id = 0;
		int data = 0;
		Effect effect = Effect.CLOUD;
		for (String permission : DSMain.getInstance().getParticles().keySet()) {
			if (player.isPermissionSet(permission)) {
				effect = DSMain.getInstance().getParticles().get(permission);
			}
		}
		if(!DSMain.getInstance().getSkill().containsKey(player.getUniqueId())) DSMain.getInstance().getSkill().put(player.getUniqueId(), 0);
		if (DSMain.getInstance().getSkill().containsKey(player.getUniqueId())) {
			String msg = ChatColor.RED + "Habilidade Ativada!";
			if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(0)) {
				skill = new UpperCircle();
				setupSkill(skill, skill1, "Parabola", player, damage, effect, id, data, msg);
			} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(1)) {
				skill = new XCross();
				setupSkill(skill, skill2, "XCross", player, damage, effect, id, data, msg);
			} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(2)) {
				skill = new SemiCircle();
				setupSkill(skill, skill3, "SemiCirculo", player, damage, effect, id, data, msg);
			} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(3)) {
				skill = new Helix();
				setupSkill(skill, skill4, "Helix", player, damage, effect, id, data, msg);
			} else if (DSMain.getInstance().getSkill().get(player.getUniqueId()).equals(4)) {
				skill = new MultipleX();
				setupSkill(skill, skill5, "RandomCross", player, damage, effect, id, data, msg);
			}
		}
	}

	private final HashMap<UUID, Long> skill1 = new HashMap<>();
	private final HashMap<UUID, Long> skill2 = new HashMap<>();
	private final HashMap<UUID, Long> skill3 = new HashMap<>();
	private final HashMap<UUID, Long> skill4 = new HashMap<>();
	private final HashMap<UUID, Long> skill5 = new HashMap<>();

	private void setupSkill(ISwordSkills skill, HashMap<UUID, Long> hashmap, String path, Player player, double damage, Effect effect, int id, int data, String msg) {
		if (hashmap.containsKey(player.getUniqueId())
				&& hashmap.get(player.getUniqueId()) > System.currentTimeMillis()) {
			long timeRemainingResp = hashmap.get(player.getUniqueId())
					- System.currentTimeMillis();
			int timeCooldownResp = (int) (timeRemainingResp / 1000);
			player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Demon Slayer" + ChatColor.GOLD
					+ "] " + ChatColor.DARK_GRAY + "Espere " + ChatColor.DARK_RED + timeCooldownResp
					+ ChatColor.DARK_GRAY + " para usar essa skill novamente");
		} else {
			skill.spawnSkill(player, damage, effect, id, data);
			hashmap.put(player.getUniqueId(), System.currentTimeMillis() + ConfigurationUpdate.swordSkillsCooldown.get(path) * 1000);
			player.sendMessage(msg);
		}
	}
 	public static String getEntry(String key) {
		return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
	}
}
