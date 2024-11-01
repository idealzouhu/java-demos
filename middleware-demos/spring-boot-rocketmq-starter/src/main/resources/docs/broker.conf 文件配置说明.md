```
brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1 = 127.0.0.2 # 此处为本地ip, 如果部署服务器, 需要填写服务器外网ip
```





**在仅有本地客户端与 broker 容器通信的情况下，可以使用 localhost。**

位于宿主机上的客户端代码利用 rmqnamesrv-canal 获取 broker 的地址 127.0.0.1 ， 从而正常访问 docketr 虚拟网络中 broker 容器。









**在还有客户端、其他容器与 broker 容器通信的情况下，应该使用宿主机的 IP 地址。**

- 不能使用 localhost 、host.docker.internal 、127.0.0.1。如果使用 127.0.0.1 的话， canal 容器会利用 mqnamesrv   获取 mqbroker 的地址为 127.0.0.1，但是，canal 容器应该使用 mqbroker容器的  IP 地址来通信

- 不能使用 mqbroker 的 Docker 虚拟网络 IP 地址，否则的话，位于宿主机上的客户端代码无法直接使用这个 IP 地址



其他知识点：在默认的 `bridge` 网络模式下，Docker 为每个容器分配一个独立的 IP 地址，这与宿主机的 IP 地址不会重叠。



