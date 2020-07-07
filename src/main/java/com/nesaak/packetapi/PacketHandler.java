package com.nesaak.packetapi;

import com.nesaak.packetapi.event.PacketReceiveEvent;
import com.nesaak.packetapi.event.PacketSendEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public class PacketHandler extends ChannelDuplexHandler {

    private WeakReference<Player> player;

    public PacketHandler(Player player) {
        this.player = new WeakReference<>(player);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketSendEvent event = new PacketSendEvent(msg, player.get());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketReceiveEvent event = new PacketReceiveEvent(msg, player.get());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        super.channelRead(ctx, msg);
    }
}