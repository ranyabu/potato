### net module 简单说明
1 net 采用netty 传输框架; 对netty做了简单封装
2 codec 采用protostuff; 你可以通过实现IMsgCodec, 修改SPI配置来自定义
3 框架暴露事件:
    * 连接建立和断开 你可以通过实现IActive, 修改SPI配置来自定义 (比如IP白名单)
4 data处理; 你需要通过实现DataHandler, 修改SPI配置来定义; 所有net消息都会转发至DataHandler处理