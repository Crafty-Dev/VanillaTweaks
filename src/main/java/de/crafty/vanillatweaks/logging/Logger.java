package de.crafty.vanillatweaks.logging;


import org.bukkit.Bukkit;

import static de.crafty.vanillatweaks.VanillaTweaks.PREFIX;
public class Logger {



    public static void info(String info){
        Bukkit.getConsoleSender().sendMessage(PREFIX + "\u00a77[\u00a77INFO\u00a77] " + "\u00a77" + info);
    }

    public static void important(String info){
        Bukkit.getConsoleSender().sendMessage(PREFIX + "\u00a7a[\u00a77INFO\u00a7a] " + "\u00a7a" + info);
    }

    public static void warn(String warn){
        Bukkit.getConsoleSender().sendMessage(PREFIX + "\u00a7c[\u00a77WARN\u00a7c] " + "\u00a7c" + warn);
    }

    public static void error(String error){
        Bukkit.getConsoleSender().sendMessage(PREFIX + "\u00a74[\u00a77ERROR\u00a74] " + "\u00a74" + error);
    }

}
