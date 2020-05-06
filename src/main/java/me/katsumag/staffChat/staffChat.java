package me.katsumag.staffChat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class staffChat extends JavaPlugin implements Listener {

    private Set<Player> players = new HashSet<>();

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMessage(final AsyncPlayerChatEvent e){
        if (! e.getMessage().startsWith("# ")) return;
        if (! e.getPlayer().hasPermission("staffchat.use")) return;

        e.setCancelled(true);
        String message = ChatColor.RED + e.getPlayer().getName() + ": " + ChatColor.AQUA + e.getMessage().substring(2);
        players.forEach(player -> player.sendMessage(message));
    }

    @EventHandler
    public void onJoin(final PlayerLoginEvent e){
        if (! e.getPlayer().hasPermission("staffchat.use")) return;
        players.add(e.getPlayer());
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent e){
        if (players.contains(e.getPlayer())) players.remove(e.getPlayer());
    }

}
