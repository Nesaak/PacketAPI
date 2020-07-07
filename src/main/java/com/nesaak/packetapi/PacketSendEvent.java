package com.nesaak.packetapi;

import org.bukkit.event.HandlerList;

public class PacketSendEvent extends PacketEvent {

    private static HandlerList handlerList = new HandlerList();

    public PacketSendEvent(Object packet) {
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
