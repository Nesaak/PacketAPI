package com.nesaak.packetapi;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class PacketEvent extends Event implements Cancellable {

    private Object handle;
    private boolean cancelled = false;

    public PacketEvent(Object packet) {
        this.handle = packet;
    }

    public Object getPacket() {
        return handle;
    }

    public String getPacketName() {
        return handle.getClass().getSimpleName();
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
