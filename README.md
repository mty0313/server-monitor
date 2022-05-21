# server-monitor
服务端用监控插件

# 功能
1. 实现了PlayerJoinEvent的处理, 会推送一条消息给配置的bark终端设备

# 使用
mvn package 后将制品部署到你的服务端plugins中, 启动实例后在 [server-root]/plugins/server-monitor/general.yml中修改配置:

```yaml
player:
  pushUrl:
    # 多个bark设备地址用英文,分隔
    bark: xxxx123,fffff345
```
