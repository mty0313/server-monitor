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
    private FileConfiguration generalConfig;

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
            createCustomConfig("general.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDisable() {
        getLogger().info("ServerMonitor Disabled");
    }

    public FileConfiguration getGeneralConfig() {
        return this.generalConfig;
    }

    public void createCustomConfig(String fileName) throws IOException {
        File customConfigFile = new File(getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            String fileSeparator = System.getProperty("file.separator");
            customConfigFile = new File(getDataFolder().getAbsolutePath() + fileSeparator + fileName);
            customConfigFile.createNewFile();
        }
        generalConfig = new YamlConfiguration();
        try {
            generalConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning(String.format("尝试读取创建的配置文件失败: %s, %s", customConfigFile.getAbsolutePath(),
                    e.getMessage()));
        }
    }
}
