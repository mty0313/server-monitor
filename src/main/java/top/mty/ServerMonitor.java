package top.mty;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.mty.executor.PlayerJoinExecutor;
import top.mty.listener.PlayerJoinListener;

import java.io.File;
import java.io.IOException;

public class ServerMonitor extends JavaPlugin {

    private static ServerMonitor instance;
    private File customConfigFile;
    private FileConfiguration customConfig;

    public static ServerMonitor getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("ServerMonitor Enabled");
        instance = this;
        // register event
        getServer().getPluginManager().registerEvent(PlayerJoinEvent.class, new PlayerJoinListener(),
                EventPriority.NORMAL, new PlayerJoinExecutor(), this);
        try {
            createCustomConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDisable() {
        getLogger().info("ServerMonitor Disabled");
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() throws IOException {
        customConfigFile = new File(getDataFolder(), "custom.yaml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            customConfigFile.createNewFile();
            saveResource("custom.yaml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
