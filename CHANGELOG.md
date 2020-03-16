# ChangeLog

### Release_1.3.0_202003016_build_A

#### 功能构建

- 建立 RecordControlHandler 并实现记录服务的上下线。
- 将数据记录模块集成至dcti。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_202003014_build_A

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
