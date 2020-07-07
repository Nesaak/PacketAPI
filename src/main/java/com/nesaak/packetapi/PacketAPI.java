package com.nesaak.packetapi;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.ref.WeakReference;

public class PacketAPI {

    private static WeakReference<Plugin> plugin;
    private static ChannelInjector injector = new ChannelInjector();

    private static RegisteredListener playerJoin = new RegisteredListener(null, (listener, event) -> injector.inject(((PlayerEvent) event).getPlayer()), EventPriority.HIGHEST, getPlugin(), false);
    private static RegisteredListener playerQuit = new RegisteredListener(null, (listener, event) -> injector.remove(((PlayerEvent) event).getPlayer()), EventPriority.HIGHEST, getPlugin(), false);
    private static RegisteredListener serverLoad = new RegisteredListener(null, (listener, event) -> injector.reloaded(), EventPriority.HIGHEST, getPlugin(), false);

    public static void enable() {
        PlayerJoinEvent.getHandlerList().register(playerJoin);
        PlayerQuitEvent.getHandlerList().register(playerQuit);
        ServerLoadEvent.getHandlerList().register(serverLoad);
    }

    public static Plugin getPlugin() {
        if (plugin == null) plugin = new WeakReference(JavaPlugin.getProvidingPlugin(PacketAPI.class));
        return plugin.get();
    }

}
