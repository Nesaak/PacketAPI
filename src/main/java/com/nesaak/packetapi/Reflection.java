package com.nesaak.packetapi;

import org.bukkit.Bukkit;

public class Reflection {

    private static final String VER = Bukkit.getVersion().getClass().getPackage().getName().split("\\.")[3];

    public static final Class CraftPlayer = getCraftBukkitClass("entity.CraftPlayer");
    public static final Class EntityPlayer = getNMSClass("EntityPlayer");
    public static final Class PlayerConnection = getNMSClass("PlayerConnection");
    public static final Class NetworkManager = getNMSClass("NetworkManager");

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VER + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getCraftBukkitClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VER + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
