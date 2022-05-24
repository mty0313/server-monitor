package top.mty;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.mty.executor.PlayerJoinExecutor;
import top.mty.listener.PlayerJoinListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Override
    public void onDisable() {
        getLogger().info("ServerMonitor Disabled");
    }

    public FileConfiguration getGeneralConfig() {
        return this.generalConfig;
    }

    public void createCustomConfig(String fileName) throws IOException {
        Path customConfigFilePath = Paths.get(getDataFolder().getAbsolutePath(), fileName);
        Path parentPath = customConfigFilePath.getParent();

        if (!Files.exists(parentPath)) {
            Files.createDirectory(parentPath);
        }
        if (!Files.exists(customConfigFilePath)) {
            Files.createFile(customConfigFilePath);
        }

        generalConfig = new YamlConfiguration();
        // TODO 已经throw是否还需要try-catch
        try {
            generalConfig.load(customConfigFilePath.toString());
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning(String.format("尝试读取创建的配置文件失败: %s, %s", customConfigFilePath,
                    e.getMessage()));
        }
    }
}
