package com.nesaak.packetapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PacketReceiveEvent extends PacketEvent {

    private static HandlerList handlerList = new HandlerList();

    public PacketReceiveEvent(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
