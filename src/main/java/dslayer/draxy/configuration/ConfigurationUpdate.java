package dslayer.draxy.configuration;

import dslayer.draxy.DSMain;
import dslayer.draxy.modos.ActivateMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ConfigurationUpdate {

    public static long ultCooldown;
    public static double swordSkillsDamage;
    public static double kekkiJutsuDamage;
    public static final HashMap<String, Boolean> isBreathOrNo = new HashMap<>();
    public static final HashMap<String, String> modePermission = new HashMap<>();
    public static final HashMap<String, String> modeCommand = new HashMap<>();
    public static final HashMap<String, String> modeByCommand = new HashMap<>();
    public static final HashMap<String, String> modeItemNames = new HashMap<>();
    public static final HashMap<String, Integer> modeItemSlot = new HashMap<>();
    public static final HashMap<Integer, String> modeItemBySlot = new HashMap<>();
    public static final HashMap<String, Integer> modeItemID = new HashMap<>();
    public static final HashMap<String, String[]> modeItemLore = new HashMap<>();
    public static final  HashMap<Integer, String> tradeSwordSkillMessages = new HashMap<>();
    public static final HashMap<String, Integer> swordSkillsCooldown = new HashMap<>(),
     kekkiJutsusCooldown = new HashMap<>(),
     modeCooldown = new HashMap<>(),
     modeActiveTime = new HashMap<>(),
     modeStats = new HashMap<>();
    public static void updateConfig() {
        updateCommands();
        updateCooldowns();
        updateMessages();
        updateStats();
        updateBoolean();
        updateModePermission();
        updateItemInformation();
        ActivateMode.setLink();
        swordSkillsDamage = DSMain.getInstance().getConfig().getInt("SwordSkills.Multiplicador");
        kekkiJutsuDamage = DSMain.getInstance().getConfig().getInt("KekkiJutsus.Multiplicador");
    }

    private static void updateCooldowns() {
        ultCooldown = DSMain.getInstance().getConfig().getLong("CooldownUlts");
        int i = -1;
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("KekkiJutsus").getKeys(false)) {
            kekkiJutsusCooldown.put(key, DSMain.getInstance().getConfig().getInt("KekkiJutsus." + key + ".Cooldown"));
        }
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("SwordSkills").getKeys(false)) {
            if(i == -1) {
                i++;
                continue;
            }
            swordSkillsCooldown.put(key, DSMain.getInstance().getConfig().getInt("SwordSkills." + key + ".Cooldown"));
            i++;
        }
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            modeCooldown.put(key, DSMain.getInstance().getConfig().getInt("Modos." + key + ".Cooldown") + DSMain.getInstance().getConfig().getInt("Modos." + key + ".TempoAtivo"));
            modeActiveTime.put(key, DSMain.getInstance().getConfig().getInt("Modos." + key + ".TempoAtivo"));
        }
    }

    private static void updateCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
                String command = DSMain.getInstance().getConfig().getString("Modos." + key + ".Comando");
                modeCommand.put(command, key);
                modeByCommand.put(key, command);
                commandMap.register(command, new ActivateMode(command));
            }
            bukkitCommandMap.setAccessible(false);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getPluginManager().disablePlugin(DSMain.getInstance());
            Bukkit.getLogger().warning("Plugin disabled due to failure while updating commands!");
            e.printStackTrace();
        }
    }

    private static void updateMessages() {
        int i = 0;
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("SwordSkills").getKeys(false)) {
            if(key.equalsIgnoreCase("Multiplicador")) continue;
            tradeSwordSkillMessages.put(i, getEntry("SwordSkills." + key + ".msg"));
            i++;
        }
    }

    private static void updateStats() {
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            modeStats.put(key, DSMain.getInstance().getConfig().getInt("Modos." + key + ".Stats"));
        }
    }

    private static void updateBoolean() {
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            isBreathOrNo.put(key, DSMain.getInstance().getConfig().getBoolean("Modos." + key + ".Breath"));
        }
    }

    private static void updateModePermission() {
        for(String key : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            modePermission.put(key, DSMain.getInstance().getConfig().getString("Modos." + key + ".Perm"));
        }
    }

    private static void updateItemInformation() {
        for(String string : DSMain.getInstance().getConfig().getConfigurationSection("Modos").getKeys(false)) {
            modeItemNames.put(string, getEntry("Modos." + string + ".ItemName"));
            modeItemLore.put(string, getEntryList("Modos." + string + ".ItemLore"));
            modeItemSlot.put(string, DSMain.getInstance().getConfig().getInt("Modos." + string + ".Slot"));
            modeItemBySlot.put(DSMain.getInstance().getConfig().getInt("Modos." + string + ".Slot"), string);
            modeItemID.put(string, DSMain.getInstance().getConfig().getInt("Modos." + string + ".Item"));

        }
    }


    private static String getEntry(String key) {
        return ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getString(key));
    }

    private static String[] getEntryList(String key) {
        String [] lore = DSMain.getInstance().getConfig().getStringList(key).toArray(new String[0]);
        for (int i = 0; i <= DSMain.getInstance().getConfig().getStringList(key).size() - 1; i++){
            lore[i] = ChatColor.translateAlternateColorCodes('&', DSMain.getInstance().getConfig().getStringList(key).get(i));
        }
        return lore;

    }
}
