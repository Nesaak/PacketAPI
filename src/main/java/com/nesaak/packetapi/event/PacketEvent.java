package com.nesaak.packetapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class PacketEvent extends Event implements Cancellable {

    private Object handle;
    private Player player;
    private boolean cancelled = false;

    public PacketEvent(Object packet, Player player) {
        super(true);
        this.player = player;
        this.handle = packet;
    }

    public void setPacket(Object packet) {
        this.handle = packet;
    }

    public Object getPacket() {
        return handle;
    }

    public String getPacketName() {
        return handle.getClass().getSimpleName();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
