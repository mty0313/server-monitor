package top.mty.listener;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import top.mty.ServerMonitor;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.logging.Logger;

public class ConfigChangeListener {

    private WatchService fileWatchService;

    private final Logger logger;

    private final Path configFilePath;

    private final FileConfiguration generalConfig;

    public ConfigChangeListener() {
        ServerMonitor instance = ServerMonitor.getInstance();
        this.logger = instance.getLogger();
        this.configFilePath = instance.getConfigFilePath();
        this.generalConfig = instance.getGeneralConfig();
    }

    public void addListener() {
        try {
            fileWatchService = FileSystems.getDefault().newWatchService();
            configFilePath
                    .getParent()
                    .register(
                            fileWatchService,
                            // TODO 是否要加上创建配置文件的状态
                            new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE},
                            SensitivityWatchEventModifier.LOW
                    );
        } catch (IOException e) {
            logger.warning("Create file watch fail: " + e.getMessage());
        }

        Bukkit.getServer().getScheduler().runTaskAsynchronously(ServerMonitor.getInstance(), () -> {
            try {
                while (true) {
                    WatchKey key = fileWatchService.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    boolean needPop = false;
                    for (WatchEvent<?> event : events) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            generalConfig.load(configFilePath.toString());
                            logger.info("Reload config file success: " + configFilePath);
                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            // TODO 删除如何处理
                            logger.warning("Delete server-monitor config file");
                            key.cancel();
                            fileWatchService.close();
                            needPop = true;
                            break;
                        }
                    }
                    if (needPop) break;
                    key.reset();
                }
            } catch (IOException | InvalidConfigurationException | InterruptedException e) {
                logger.warning("Reload config file failed: " + e.getMessage());
            }
        });
    }

}
