package com.nesaak.packetapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PacketSendEvent extends PacketEvent {

    private static HandlerList handlerList = new HandlerList();

    public PacketSendEvent(Object packet, Player player) {
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