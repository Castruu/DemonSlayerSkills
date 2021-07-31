package dslayer.draxy.utils;

import dslayer.draxy.DSMain;
import dslayer.draxy.commands.DatabaseUtils;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PassivasMethods {
    public double multiplierFirst(Player player, String path, String pathe) throws ScriptException {
        DatabaseUtils databaseUtils = new DatabaseUtils();
        String multiplicador = DSMain.getInstance().getConfig().getString("Passivas." + path).replace("level", String.valueOf(databaseUtils.getLevel(player, pathe)));
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        Object eval = engine.eval(multiplicador);
        return (double) eval;

  }



}
