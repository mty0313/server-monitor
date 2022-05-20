package top.mty;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.mty.executor.PlayerJoinExecutor;
import top.mty.listener.PlayerJoinListener;

public class ServerMonitor extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ServerMonitor Enabled");
        // register event
        getServer().getPluginManager().registerEvent(PlayerJoinEvent.class, new PlayerJoinListener(),
                EventPriority.NORMAL, new PlayerJoinExecutor(), this);
        FileConfiguration config = getConfig();
        System.out.println("config = " + config);
    }

    public void onDisable() {
        getLogger().info("ServerMonitor Disabled");
    }
}
