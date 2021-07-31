package dslayer.draxy;

import dslayer.draxy.commands.*;
import dslayer.draxy.configuration.ConfigurationUpdate;
import dslayer.draxy.configuration.DSReload;
import dslayer.draxy.database.*;
import dslayer.draxy.events.*;
import dslayer.draxy.modos.ActivateMode;
import dslayer.draxy.modos.MenuListener;
import dslayer.draxy.modos.ModosMenu;
import dslayer.draxy.modos.OnExitJoin;
import dslayer.draxy.utils.MenuBuyKekki;
import dslayer.draxy.utils.MenuSkills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

public class DSMain extends JavaPlugin {
	KekkiJutsus kekkiJutsus = new KekkiJutsus();
	@Override
	public void onEnable() {
		saveDefaultConfig();
		setupServer();
		ConfigurationUpdate.updateConfig();
	}

	@Override
	public void onDisable(){
		for(String key : ActivateMode.getLink().keySet()) {
			for(UUID uuid : ActivateMode.getLink().get(key).keySet()) {
				Player player = Bukkit.getPlayer(uuid);
				int stats = ConfigurationUpdate.modeStats.get(key);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmca3 -" + stats + " " + player.getName());
			}
		}
	Bukkit.getScheduler().cancelAllTasks();
	Connection.close();
	}

	public static DSMain getInstance() {
		return getPlugin(DSMain.class);
	}

	private final HashMap<UUID, Integer> skill = new HashMap<>();
	public HashMap<UUID, Integer> getSkill() { return skill; }

	private final HashMap<UUID, Integer> mvskill = new HashMap<>();
	public HashMap<UUID, Integer> getMvSkill() { return mvskill; }

	private final HashMap<UUID, Integer> passivas = new HashMap<>();
	public HashMap<UUID, Integer> getPassivas() { return passivas; }

	public HashMap<String, Effect> getParticles() {
		return particles;
	}
	private final HashMap<String, Effect> particles = new HashMap<>();
	public void loadParticles() {
		particles.put(getPermission("Permissions.Agua"), Effect.WATERDRIP);
		particles.put(getPermission("Permissions.Chama"), Effect.FLAME);
		particles.put(getPermission("Permissions.Pedra"), Effect.PARTICLE_SMOKE);
		particles.put(getPermission("Permissions.Serpente"), Effect.FIREWORKS_SPARK);
		particles.put(getPermission("Permissions.Trovao"), Effect.COLOURED_DUST);
		particles.put(getPermission("Permissions.Nevoa"), Effect.SPELL);
		particles.put(getPermission("Permissions.Vento"), Effect.HAPPY_VILLAGER);
		particles.put(getPermission("Permissions.Besta"), Effect.MAGIC_CRIT);
		particles.put(getPermission("Permissions.Amor"), Effect.HEART);
		particles.put(getPermission("Permissions.Som"), Effect.NOTE);
		particles.put(getPermission("Permissions.Inseto"), Effect.PORTAL);
	}
	public String getPermission(String path){ return (getConfig().getString(path)); }

	public List<UUID> getNivel() { return nivel; }

	private final List<UUID> nivel = new ArrayList<>();

	private final HashMap<UUID, Effect> activebreath = new HashMap<>();
	public HashMap<UUID, Effect> getActivebreath() { return activebreath; }

	public static String getEntry(String key) {
		return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
	}


	public HashMap<UUID, String> getHasActiveBreath() {
		return hasActiveBreath;
	}

	private final HashMap<UUID, String> hasActiveBreath = new HashMap<>();

	public HashMap<UUID, Long> getCooldownGeral() {
		return cooldownGeral;
	}

	private final HashMap<UUID, Long> cooldownGeral = new HashMap<>();

	public HashMap<String, Integer> getKekkiperms() {
		return kekkiperms;
	}

	public HashMap<Integer, String> getKekkisentence() {
		return kekkisentence;
	}

	public List<String> getPermlist() {
		return permlist;
	}

	private List<String> permlist = new ArrayList<>();
	private HashMap<String, Integer> kekkiperms = new HashMap<>();
	private HashMap<Integer, String> kekkisentence = new HashMap<>();
	private void setupPhrase () {
		kekkisentence.put(1, ChatColor.DARK_PURPLE + "Kekki Jutsu da Esfera!");
		kekkisentence.put(2, ChatColor.RED + "Esprema seus oponentes!");
		kekkisentence.put(3, ChatColor.BLUE + "Prenda seu rival!");
		kekkisentence.put(4, ChatColor.GRAY + "Teias de Aranha!");
		kekkisentence.put(5, ChatColor.RED + "Exploda tudo!");
	}
	private void setupPerms() {
		kekkiperms.put("kekkisphere.use", 1);
		kekkiperms.put("kekkicircle.use", 2);
		kekkiperms.put("kekkitree.use", 3);
		kekkiperms.put("kekkispider.use", 4);
		kekkiperms.put("kekkinezuko.use", 5);
	}
	private void setupPermList() {
		permlist.add("kekkisphere.use");
		permlist.add("kekkicircle.use");
		permlist.add("kekkitree.use");
		permlist.add("kekkispider.use");
		permlist.add("kekkinezuko.use");
	}

	public void setupServer() {
		Bukkit.getPluginManager().registerEvents(new SwordSkills(), this);
		Bukkit.getPluginManager().registerEvents(new Moves(), this);
		Bukkit.getPluginManager().registerEvents(new FirstQuery(), this);
		Bukkit.getPluginManager().registerEvents(new MenuPoints(), this);
		Bukkit.getPluginManager().registerEvents(new Passivas(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		Bukkit.getPluginManager().registerEvents(new KekkiJutsus(), this);
		Bukkit.getPluginManager().registerEvents(new OnExitJoin(), this);
		getCommand("dsreload").setExecutor(new DSReload());
		getCommand("moveskill").setExecutor(new ItensGive());
		getCommand("passivas").setExecutor(new ItensGive());
		getCommand("ativas").setExecutor(new ItensGive());
		getCommand("dsskills").setExecutor(new ChangeLevels());
		getCommand("respmenu").setExecutor(new MenuSkills());
		getCommand("kekkimenu").setExecutor(new MenuBuyKekki());
		getCommand("kekkimenu").setExecutor(new MenuBuyKekki());
		getCommand("activateskill").setExecutor(new ActiveListener());
		getCommand("startskill").setExecutor(new StartSkill());
		getCommand("changeskill").setExecutor(new StartSkill());
		DSMain.getInstance().getCommand("modos").setExecutor(new ModosMenu());
		addModeCommands();
		loadParticles();
		setupPerms();
		setupPhrase();
		setupPermList();
		Connection.openConnectionSQLite();
	}

	private void addModeCommands() {
			try {
				final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				bukkitCommandMap.setAccessible(true);
				CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
				for(String key : getConfig().getConfigurationSection("Modos").getKeys(false)) {
					String command = getConfig().getString("Modos." + key + ".Comando");
					commandMap.register(command, new ActivateMode(command));
				}
				bukkitCommandMap.setAccessible(false);
			} catch(NoSuchFieldException | IllegalAccessException e) {
				Bukkit.getPluginManager().disablePlugin(this);
				e.printStackTrace();
			}

	}

}
	
