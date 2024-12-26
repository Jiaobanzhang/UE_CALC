# 黑马旅游思路:

## 1. 环境配置:

### (1) mysql 环境:

首先要根据 黑马 给的 sql 文件配置好 tb_hotel 数据库

根据自己的 sql 地址, 将 SpringBoot 上的 yml 上的配置文件的相关配置进行修改

### (2) RabbitMQ 环境:

自己本来尝试在虚拟机上运行 rabbitMQ 来着, 但是速度太慢

我就在本机运行的 RabbitMQ, 然后根据自己本地的 rabbitMQ IP 地址和端口, 将 SpringBoot 上的 yml 上的配置文件的相关配置进行修改

<img src="https://p.ipic.vip/hybck1.png" alt="截屏2024-12-26 22.44.08" style="zoom:50%;" />

### (3) ElasticsSearch 环境:

ElasticsSearch 的作用就是创建一个索引库, 将 sql 中的数据统计到索引库上, 加快查询速度

自己在 虚拟机上运行了 ElasticsSearch 容器 (索引库, 相当于 sql ) 和 Kibana 容器(将索引库可视化, 相当于 navicat)

然后在代码中修改自己的 es 索引库地址:

<img src="https://p.ipic.vip/h59jn5.png" alt="截屏2024-12-26 22.45.39" style="zoom:50%;" />

## 2. 一些测试文件:

一共有三个测试文件

分别是:

HotelDocumentTest: 用来测试创建 文档的, 相当于给 表中添加删除查询数据, 注意这里的查询只能用 id 查询 

HotelIndexTest: 用来测试创建索引库的, 相当于创建 sql 表

HotelSearchTest: 用来查询数据的, 相当于 在sql 表中根据队列名查询数据

<img src="https://p.ipic.vip/duo4pz.png" alt="截屏2024-12-25 12.12.51" style="zoom:67%;" />

## 3. 黑马旅游案例: hotel_demo

### (1) 搜索分页功能:

<img src="https://p.ipic.vip/dm82yw.png" alt="截屏2024-12-25 23.05.35" style="zoom:50%;" />

这个业务的要求是实现 

1. 搜索功能, 能够根据关键字去 es 中搜索出相应的文档, 
2. 第二个功能是实现返回的文档根据分页要求进行分页



### (2) 条件过滤:

<img src="https://p.ipic.vip/lr1nm3.png" alt="截屏2024-12-25 23.17.13" style="zoom:50%;" />

这里使用 BooleanQuery 进行复合查询, 另外使用 must 和 filter  分别表示对查询条件算分或是不算分





### (3) 我附近的酒店:

实现两个功能:

1. 搜索到的酒店能够根据距离我的位置进行排序(添加距离排序的逻辑)
2. 能够显示距离我的距离 (返回 hotelDoc 的时候返回distance属性)

![截屏2024-12-25 23.32.40](https://p.ipic.vip/jbta7w.png)

<img src="https://p.ipic.vip/nxrj4g.png" alt="截屏2024-12-25 23.34.06" style="zoom:50%;" />

### (4) 广告置顶:

<img src="https://p.ipic.vip/kk9nfk.png" alt="截屏2024-12-26 20.03.50" style="zoom:50%;" />

<img src="https://p.ipic.vip/b8eqy0.png" alt="截屏2024-12-26 20.17.17" style="zoom:50%;" />

<img src="https://p.ipic.vip/iltta3.png" alt="截屏2024-12-26 20.19.14" style="zoom: 33%;" />



## 4. 黑马数据同步 hotel_admin

### (1) 声明队列和交换机:

是在 hotel_demo 微服务中, 也就是 es 微服务中声明的 队列和交换机, 

<img src="https://p.ipic.vip/gte2w8.png" alt="截屏2024-12-26 22.25.42" style="zoom: 33%;" />

### (2) 发送 mq 消息

HotelController:

<img src="https://p.ipic.vip/x7wyhy.png" alt="截屏2024-12-26 22.23.22" style="zoom:50%;" />

### (3) 监听 mq 消息:

<img src="https://p.ipic.vip/e1o2vw.png" alt="截屏2024-12-26 22.23.59" style="zoom: 33%;" />

<img src="https://p.ipic.vip/2pcs06.png" alt="截屏2024-12-26 22.32.39" style="zoom: 33%;" />

## 5. 进行测试:

- Queue 创建成功:

![截屏2024-12-26 22.33.38](https://p.ipic.vip/ensskt.png)

- Exchange 交换机创建成功:

 <img src="https://p.ipic.vip/ua2rhg.png" alt="截屏2024-12-26 22.34.19" style="zoom:50%;" />

- 绑定关系成功:

<img src="https://p.ipic.vip/bqjs9p.png" alt="截屏2024-12-26 22.34.33" style="zoom:50%;" />

- 在 Hotel_admin 对sql数据进行更改: 将这个酒店的价格从 2688 改成 2600, sql 数据库中的数据会发生改变

<img src="https://p.ipic.vip/sgwcs6.png" alt="截屏2024-12-26 22.39.36" style="zoom:50%;" />

- 去 RabbitMQ 中查看是否有数据变化: (这里有一个三角, 说明确实有接收到数据)

<img src="https://p.ipic.vip/9q1cgz.png" alt="截屏2024-12-26 22.36.35" style="zoom:50%;" />

- 去 前端页面索引查询一下, 看索引库是否发生变化: (索引库确实发生了变化, 说明 索引库可以监听到 RabbitMQ 中的事件, 根据 sql 的变化进行更新索引库数据)

<img src="https://p.ipic.vip/f63d6s.png" alt="截屏2024-12-26 22.36.55" style="zoom:50%;" />



