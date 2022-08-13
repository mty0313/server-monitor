package top.mty.executor;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import top.mty.ServerMonitor;
import top.mty.converter.Player2MyPlayer;
import top.mty.entity.MyPlayer;
import top.mty.utils.Assert;

import java.util.logging.Logger;

import static top.mty.executor.PlayerJoinExecutor.LOG_SERVER_URL;
import static top.mty.executor.PlayerJoinExecutor.PLAYER_PUSH_URL_BARK;

public class PlayerQuitExecutor implements EventExecutor {
  @Override
  public void execute(Listener listener, Event event) {
    ServerMonitor instance = ServerMonitor.getInstance();
    Logger logger = instance.getLogger();
    PlayerQuitEvent quitEvent = (PlayerQuitEvent) event;
    Player quitedPlayer = quitEvent.getPlayer();
    MyPlayer myPlayer = Player2MyPlayer.getInstance().convert(quitedPlayer);
    try {
      Assert.notNull(myPlayer);
    } catch (Exception e) {
      logger.warning("player不存在");
      return;
    }
    Configuration generalConfig = instance.getGeneralConfig();
//    String[] barkPushUrls = generalConfig.getString(PLAYER_PUSH_URL_BARK).split(",");
    String logActionUrl = String.format("%s/mc/save", generalConfig.getString(LOG_SERVER_URL));
    logQuitAction(myPlayer, logActionUrl, logger);
  }


  private void logQuitAction(MyPlayer myPlayer, String logActionUrl, Logger logger) {
    String user = myPlayer.getName();
    String action = "logout";
    logActionUrl += "?user=" + user + "&action=" + action;
    String command = String.format("curl -X POST %s", logActionUrl);
    try {
      Runtime.getRuntime().exec(command);
      logger.info(String.format("log玩家退出事件: %s", user));
    } catch (Exception e) {
      logger.warning("命令执行失败: " + command + ": " + e.getMessage());
    }
  }
}
