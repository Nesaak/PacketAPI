package com.nesaak.packetapi;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChannelInjector {

    private static final String MINECRAFT_KEY = "packet_handler";
    private static final String API_KEY = "packetAPI";

    public void inject(Player player) {
        Bukkit.getLogger().info("Injecting Packet Handling into " + player.getName());
        getPlayerChannel(player).pipeline().addBefore(MINECRAFT_KEY, API_KEY, new PacketHandler(player));
    }

    public void remove(Player player) {
        Bukkit.getLogger().info("Removing Packet Handling from " + player.getName());
        Channel channel = getPlayerChannel(player);
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(API_KEY);
        });
    }

    public void reloaded() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            inject(player);
        }
    }

    public Channel getPlayerChannel(Player player) {
        try {
            Object entityPlayer = Reflection.CraftPlayer.getMethod("getHandle").invoke(player);
            Object playerConnection = Reflection.EntityPlayer.getField("playerConnection").get(entityPlayer);
            Object networkManager = Reflection.PlayerConnection.getField("networkManager").get(playerConnection);
            return (Channel) Reflection.NetworkManager.getField("channel").get(networkManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
