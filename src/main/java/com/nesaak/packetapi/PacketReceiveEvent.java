package com.nesaak.packetapi;

import org.bukkit.event.HandlerList;

public class PacketReceiveEvent extends PacketEvent {

    private static HandlerList handlerList = new HandlerList();

    public PacketReceiveEvent(Object packet) {
        super(packet);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
