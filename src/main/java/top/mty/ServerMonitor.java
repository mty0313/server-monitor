package top.mty;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.mty.executor.PlayerJoinExecutor;
import top.mty.listener.PlayerJoinListener;

public class ServerMonitor extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ServerMonitor Enabled");

        getServer().getPluginManager().registerEvent(PlayerJoinEvent.class, new PlayerJoinListener(),
                EventPriority.NORMAL, new PlayerJoinExecutor(), this);
    }

    public void onDisable() {
        getLogger().info("ServerMonitor Disabled");
    }
}
