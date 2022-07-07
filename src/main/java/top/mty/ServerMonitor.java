package top.mty;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.mty.executor.PlayerJoinExecutor;
import top.mty.executor.PlayerQuitExecutor;
import top.mty.listener.ConfigChangeListener;
import top.mty.listener.PlayerListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerMonitor extends JavaPlugin {

  private static ServerMonitor instance;
  private FileConfiguration generalConfig;

  private Path configFilePath;

  public static ServerMonitor getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    getLogger().info("ServerMonitor Enabled");
    instance = this;
    // register event
    getServer().getPluginManager().registerEvent(PlayerJoinEvent.class, new PlayerListener(),
        EventPriority.NORMAL, new PlayerJoinExecutor(), this);
    getServer().getPluginManager().registerEvent(PlayerQuitEvent.class, new PlayerListener(),
        EventPriority.NORMAL, new PlayerQuitExecutor(), this);
    configFilePath = Paths.get(getDataFolder().getAbsolutePath(), "general.yml");
    try {
      createCustomConfig();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // register config file change event
    new ConfigChangeListener().addListener();

  }

  @Override
  public void onDisable() {
    getLogger().info("ServerMonitor Disabled");
  }

  public FileConfiguration getGeneralConfig() {
    return this.generalConfig;
  }

  public Path getConfigFilePath() {
    return this.configFilePath;
  }

  public void createCustomConfig() throws IOException {
    Path parentPath = configFilePath.getParent();

    if (!Files.exists(parentPath)) {
      Files.createDirectory(parentPath);
    }
    if (!Files.exists(configFilePath)) {
      Files.createFile(configFilePath);
    }

    generalConfig = new YamlConfiguration();
    // TODO 已经throw是否还需要try-catch
    try {
      generalConfig.load(configFilePath.toString());
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().warning(String.format("尝试读取创建的配置文件失败: %s, %s", configFilePath,
          e.getMessage()));
    }
  }
}
