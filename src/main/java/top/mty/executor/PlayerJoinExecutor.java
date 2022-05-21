package top.mty.executor;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;
import top.mty.ServerMonitor;
import top.mty.converter.Player2MyPlayer;
import top.mty.entity.MyPlayer;
import top.mty.utils.Assert;
import top.mty.utils.DateUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class PlayerJoinExecutor implements EventExecutor {

    public static final String PLAYER_PUSH_URL_BARK = "player.pushUrl.bark";

    @Override
    public void execute(Listener listener, Event event) {
        ServerMonitor instance = ServerMonitor.getInstance();
        Logger logger = instance.getLogger();
        PlayerJoinEvent joinEvent = (PlayerJoinEvent) event;
        MyPlayer myPlayer = Player2MyPlayer.getInstance().convert(joinEvent.getPlayer());
        try {
            Assert.notNull(myPlayer);
        } catch (Exception e) {
            logger.warning("player不存在");
            return;
        }
        String description = String.format("%s加入游戏,管理员:%s,模式:%s,新玩家:%s@%s",
                myPlayer.getName(),
                myPlayer.getIsOp().getDesc(),
                myPlayer.getGameMode().name(),
                myPlayer.getIsNewPlayer().getDesc(),
                DateUtils.now());
        Configuration playerConfig = instance.getGeneralConfig();
        String[] barkPushUrls = playerConfig.getString(PLAYER_PUSH_URL_BARK).split(",");
        try {
            for (String barkUrl: barkPushUrls) {
                String command = "curl " + barkUrl + String.format("/%s/%s", "玩家登录提醒",
                        description);
                Process p = Runtime.getRuntime().exec(command);
                int exit = p.waitFor();
                logger.info(String.format("执行了curl: %s", description));
            }
        } catch (IOException | InterruptedException e) {
            logger.warning("命令执行失败");
        }
    }
}
