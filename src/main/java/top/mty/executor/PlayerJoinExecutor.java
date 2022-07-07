package top.mty.executor;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;
import top.mty.ServerMonitor;
import top.mty.converter.Player2MyPlayer;
import top.mty.entity.MyPlayer;
import top.mty.utils.Assert;
import top.mty.utils.DateUtils;

import java.util.logging.Logger;

public class PlayerJoinExecutor implements EventExecutor {

  public static final String PLAYER_PUSH_URL_BARK = "player.pushUrl.bark";

  public static final String LOG_SERVER_URL = "activityLog.serverUrl";

  @Override
  public void execute(Listener listener, Event event) {
    ServerMonitor instance = ServerMonitor.getInstance();
    Logger logger = instance.getLogger();
    PlayerJoinEvent joinEvent = (PlayerJoinEvent) event;
    Player joinedPlayer = joinEvent.getPlayer();
    initPlayer(joinedPlayer);
    MyPlayer myPlayer = Player2MyPlayer.getInstance().convert(joinedPlayer);
    try {
      Assert.notNull(myPlayer);
    } catch (Exception e) {
      logger.warning("player不存在");
      return;
    }
    Configuration generalConfig = instance.getGeneralConfig();
    String[] barkPushUrls = generalConfig.getString(PLAYER_PUSH_URL_BARK).split(",");
    doBarkPush(myPlayer, barkPushUrls, logger);
    String logActionUrl = String.format("%s/mc/save", generalConfig.getString(LOG_SERVER_URL));
    logLoginAction(myPlayer, logActionUrl, logger);
  }

  /**
   * 初始化player相关属性
   */
  private void initPlayer(Player player) {
    // 所有玩家进入服务器之后都设定睡觉状态忽略, 即不需要所有玩家睡觉就能跳过黑夜
//        player.setSleepingIgnored(true);
  }

  private void doBarkPush(MyPlayer myPlayer, String[] barkPushUrls, Logger logger) {
    String description = String.format("%s加入游戏,管理员:%s,模式:%s,新玩家:%s@%s",
        myPlayer.getName(),
        myPlayer.getIsOp().getDesc(),
        myPlayer.getGameMode().name(),
        myPlayer.getIsNewPlayer().getDesc(),
        DateUtils.now());
    try {
      for (String barkUrl : barkPushUrls) {
        String command = "curl " + barkUrl + String.format("/%s/%s", "玩家登录提醒",
            description);
        Runtime.getRuntime().exec(command);
        logger.info(String.format("执行了bark推送: %s", command));
      }
    } catch (Exception e) {
      logger.warning("bark推送命令执行失败: " + e.getMessage());
    }
  }

  private void logLoginAction(MyPlayer myPlayer, String logActionUrl, Logger logger) {
    String user = myPlayer.getName();
    String action = "login";
    logActionUrl += "?user=" + user + "&action=" + action;
    String command = String.format("curl -X POST %s", logActionUrl);
    try {
      Runtime.getRuntime().exec(command);
      logger.info(String.format("log玩家登录事件: %s", user));
    } catch (Exception e) {
      logger.warning("命令执行失败: " + command + ": " + e.getMessage());
    }
  }
}
