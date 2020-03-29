# ChangeLog

### Release_1.4.2_20200329_build_B

#### 功能构建

- 优化部分properties文件中的注释。

#### Bug修复

- 修复部分实体的CriteriaMaker中的错误。
- 修复部分模块中pom.xml的错误。

#### 功能移除

- (无)

---

### Release_1.4.2_20200327_build_A

#### 功能构建

- 更新snowflake依赖至1.2.3.b。
- 添加fdr-node-all模块。

#### Bug修复

- 修复fdr-node-record打包文件mainClass不正确的bug。
- 修复部分spring的配置错误。

#### 功能移除

- (无)

---

### Release_1.4.1_20200326_build_B

#### 功能构建

- 更新subgrade依赖至beta-0.3.2.b。
- 更新dcti依赖至1.0.0.c。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.1_20200326_build_A

#### 功能构建

- Mock数据源实现，将压力测试工具以数据源的形式整合到项目中。
- 数据查询映射接口实现。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.0_20200323_build_A

#### 功能构建

- 删除 com.dwarfeng.fdr.stack.handler.Handler，所有的处理器均改为继承 subgrade 的 Handler。
- 更改项目结构，将不同的功能分布在多个节点上。建立node-maintain和node-record两个节点。
- 将kafka数据源整合到项目当中，放弃dubbo数据源。
- 增加多种pusher，事件的推送由之前的仅kafka增加为可自由选择的多种数据源。
- 建立assembly打包文件，项目package后将直接输出可供linux平台运行的.tar.gz文件。

#### Bug修复

- (无)

#### 功能移除

- ~~放弃dubbo数据源。~~

---

### Release_1.3.1_20200319_build_A

#### 功能构建

- 优化AbstractConsumeHandler启动和停止时的代码。
- 构建 fdr-plugin 模块。
- 新建 kafka source 插件。

#### Bug修复

- 开启 redis 事务。
- 修复AbstractConsumeHandler停止后后台任务可能未执行完的bug。

#### 功能移除

- (无)

---

### Release_1.3.0_20200316_build_A

#### 功能构建

- 建立 RecordControlHandler 并实现记录服务的上下线。
- 将数据记录模块集成至dcti。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20200314_build_A

#### 功能构建

- 优化数据记录的效率。
- 实现 Qos 服务。
- 更改启动器为 spring-terminator 风格。
- 更新依赖 dutil 至版本 beta-0.2.1.a。

#### Bug修复

- (无)

#### 功能移除

- ~~去除数据点与数据值质检的外键约束。~~

---

### Release_1.1.0_20200308_build_A

#### 功能构建

- 工程名称更改为fdr。
- 添加 FastJsonDataInfo 和 JSFixedFastJsonDataInfo。
- 改造项目结构。
- 将RealtimeValue存储方式改为Redis数据库。
- 增加Point实体的预设查询。
- 升级subgrade版本至beta-0.3.1.c。
- 升级subgrade版本至1.2.3.a。
- 完善过滤器和触发器的工作方式。

#### Bug修复

- 解决依赖冲突。
- 修正装配文件错误的位置。
- 将//noinspection替换为@SupressWarning。

#### 功能移除

- ~~删除Category~~

---

### Release_1.0.0_20200213_build_A

#### 功能构建

- 实现了基于数据库的数据维护服务节点。
- 实现了基于数据库的数据记录服务节点。

#### Bug修复

- (无)

#### 功能移除

- (无)
