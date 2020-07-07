package com.nesaak.packetapi;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class PacketAPI {

    public static void listenOutgoing(Player player, Consumer<Object> packet) {
        getPlayerChannel(player).pipeline().addBefore("packet_handler", "outgoing", new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                packet.accept(msg);
                super.write(ctx, msg, promise);
            }
        });
    }

    public static void listenIncoming(Player player, Consumer<Object> packet) {
        getPlayerChannel(player).pipeline().addBefore("packet_handler", "incoming", new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                packet.accept(msg);
                super.channelRead(ctx, msg);
            }
        });
    }

    public static void enable() {
    }


    public static Channel getPlayerChannel(Player player) {
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
