package dslayer.draxy.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class ChangeLevels implements CommandExecutor, TabCompleter {
    DatabaseUtils databaseUtils = new DatabaseUtils();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) return false;
        if(strings.length == 0) {
            commandSender.sendMessage(ChatColor.GOLD + "-----------------------------------\n" +
                    ChatColor.BLUE + "DSDarkSkills - \n" + ChatColor.RED + "/dsskills active (player) (level) -> Use para alterar o nivel do jogador na respiração ativa!\n\n" +
                    "/dskills skill (player) (level) (skill1, skill2, ou skill3) -> Use para alterar o nível de uma skill específica do jogador!\n\n" +
                    "/dsskills passivas (player) (level) -> Use para alterar o nível do jogador!\n\n" +
                    "/dsskills points (player) (level) -> Use para alterar os pontos do jogador!\n\n" +
                    "/dsskills kekkijutsu (player) -> Libera, aleatoriamente, um dos Kekkijutsus");
            return false;
        }
        if(strings.length < 5) {
            if (strings[0].equals("active")) {
                int level = Integer.parseInt(strings[2]);
                if (command.getName().equals("activelvl")) {
                    Player target = commandSender.getServer().getPlayer(strings[1]);
                    databaseUtils.setLevel(target, level, "active");
                }
            }else
            if (strings[0].equals("skill")) {
                int level = Integer.parseInt(strings[2]);
                Player target = commandSender.getServer().getPlayer(strings[1]);
                try {
                    databaseUtils.setLevel(target, level, strings[3]);
                } catch (Exception e) {
                    e.printStackTrace();
                    commandSender.sendMessage("Insira um path válido!");
                }
            }
            else  if (strings[0].equals("passivas")) {
                int level = Integer.parseInt(strings[2]);
                Player target = commandSender.getServer().getPlayer(strings[1]);
                databaseUtils.setLevel(target, level, "nivel");
                databaseUtils.setLevel(target, level, "points");
            }
            else if (strings[0].equals("points")) {
                int level = Integer.parseInt(strings[2]);
                Player target = commandSender.getServer().getPlayer(strings[1]);
                databaseUtils.setLevel(target, level, "points");
            }
            else if(strings[0].equals("kekkijutsu")) {
                Random rand = new Random();
                setPermlist();
                Player target = commandSender.getServer().getPlayer(strings[1]);
                String randomElement = permlist.get(rand.nextInt(permlist.size()));
                commandSender.sendMessage(randomElement);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp " + target.getName() + " " + randomElement);
            }
            return false;
        }

        return  false;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] strings1 = new String[] {"passivas", "skill", "active", "points", "kekkijutsu"};

        if(strings.length == 1) {
            List<String> stringList = new ArrayList<>();
            if (!strings[0].equals("")) {
                for (String string : strings1) {
                    if (string.toLowerCase().startsWith(strings[0].toLowerCase())) {
                        stringList.add(string);
                    }
                }
            } else {
                stringList.addAll(Arrays.asList(strings1));
            }
            Collections.sort(stringList);
            return stringList;
        }
        else {
            if(strings[0].equals("skill")) {
                if(strings.length == 4) {
                    List<String> stringList = new ArrayList<>();
                    stringList.add("skill1");
                    stringList.add("skill2");
                    stringList.add("skill3");
                    Collections.sort(stringList);
                    return stringList;
                }
            }
        }
        return null;
    }

    List<String> permlist = new ArrayList<>();
    public void setPermlist() {
        permlist.add("kekkicircle.use");
        permlist.add("kekkisphere.use");
    }
}
