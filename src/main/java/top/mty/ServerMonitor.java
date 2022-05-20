package top.mty;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ServerMonitor extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "ServerMonitor Enabled");
    }

    public void onDisable() {
        getLogger().log(Level.INFO, "ServerMonitor Disabled");
    }
}
