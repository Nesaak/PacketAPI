package com.nesaak.packetapi;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class PacketAPI {

    private static final String VER = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    private static boolean enabled;

    private static void enable() {
        if (enabled) return;
        enabled = true;
    }

    public static void listenOutgoing(Player player, Consumer<Object> packet) {
        enable();
        getPlayerChannel(player).pipeline().addBefore("packet_handler", "listener", new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                Bukkit.broadcastMessage(msg.getClass().getSimpleName());
                // msg is our packet
                super.write(ctx, msg, promise);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Bukkit.broadcastMessage(msg.getClass().getSimpleName());
                // msg is our packet
                super.channelRead(ctx, msg);
            }
        });

    }

    public static Channel getPlayerChannel(Player player) {
        try {
            Object entityPlayer = getCraftBukkit("CraftPlayer").getMethod("getHandle").invoke(player);
            Object playerConnection = getNMS("EntityPlayer").getField("playerConnection").get(entityPlayer);
            Object networkManager = getNMS("PlayerConnection").getField("networkManager").get(playerConnection);
            return (Channel) getNMS("NetworkManager").getField("channel").get(networkManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void listenIncoming(Player player, Consumer<Object> packet) {
        enable();

    }

    public static Class<?> getNMS(String name) {
        try {
            return Class.forName("net.minecraft.server." + VER + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getCraftBukkit(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VER + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
