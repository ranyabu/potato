### net module 简单说明
1 net 采用netty 传输框架; 对netty做了简单封装
2 codec 采用protostuff; 你可以通过实现IMsgCodec, 修改SPI配置来自定义