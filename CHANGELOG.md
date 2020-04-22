# ChangeLog

### Release_1.6.4_20200421_build_A

#### 功能构建

- 优化com.dwarfeng.fdr.impl.handler.consumer.RealtimeEventConsumer的异常处理流程。
- 优化com.dwarfeng.fdr.impl.handler.consumer.RealtimeValueConsumer的异常处理流程。
- 更新README.md说明文件。
- 调整部分实体维护服务的CrudOperation，对齐方法get和batchGet。
   - com.dwarfeng.fdr.impl.service.operation.FilteredValueCrudOperation
   - com.dwarfeng.fdr.impl.service.operation.PersistenceValueCrudOperation
   - com.dwarfeng.fdr.impl.service.operation.TriggeredValueCrudOperation

#### Bug修复

- 修改拼写错误的包名。

#### 功能移除

- (无)

---

### Release_1.6.3_20200417_build_C

#### 功能构建

- (无)

#### Bug修复

- 修复几处pom.xml的问题。

#### 功能移除

- (无)

---

### Release_1.6.3_20200415_build_B

#### 功能构建

- (无)

#### Bug修复

- 修复RegexFilterMaker.RegexFilter过滤器的返回字段错误。

#### 功能移除

- (无)

---

### Release_1.6.3_20200414_build_A

#### 功能构建

- 添加 PersistenceValueMaintainService.BETWEEN 预设查询。
- 添加 FilteredValueMaintainService.BETWEEN 预设查询。
- 添加 TriggeredValueMaintainService.BETWEEN 预设查询。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.2_20200413_build_B

#### 功能构建

- (无)

#### Bug修复

- 修复WebInputPersistenceValue.toStackBean方法不是静态方法的bug。

#### 功能移除

- (无)

---

### Release_1.6.2_20200413_build_A

#### 功能构建

- RealtimeValueMaintainService实现全部实体查询。
- PersistenceValueMaintainService实现全部实体查询。
- FilteredValueMaintainService实现全部实体查询。
- TriggerValueMaintainService实现全部实体查询。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.1_20200412_build_A

#### 功能构建

- 优化GroovyFilterSupporter.provideExampleContent方法的实现方式。
- 优化GroovyTriggerSupporter.provideExampleContent方法的实现方式。
- 实现com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperMaker映射器构造器。

#### Bug修复

- 修复部分模块dubbo组件未暴露MapperSupportMaintainService服务的问题。
  - node-all
  - node-maintain

#### 功能移除

- RealtimeValueMaintainService实现全部实体查询。

---

### Release_1.6.0_20200407_build_A

#### 功能构建

- 将可放宽装配的组件列表设置为 @Autowired(required = false)
  - com.dwarfeng.fdr.impl.handler.FilterHandlerImpl
  - com.dwarfeng.fdr.impl.handler.MapperHandlerImpl
  - com.dwarfeng.fdr.impl.handler.PushHandlerImpl
  - com.dwarfeng.fdr.impl.handler.TriggerHandlerImpl
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl
- Filter,Trigger相关结构实现功能分离。
  - com.dwarfeng.fdr.impl.handler.FilterSupporter
  - com.dwarfeng.fdr.impl.handler.TriggerSupporter
- MapperSupport实体及其服务实现。
- 更改优化node模块的程序结构。
  - node-all
  - node-maintain
  - node-record
- 添加node-inspect模块。

#### Bug修复

- (无)

#### 功能移除

- ~~删除api模块以及解除judge依赖~~

---

### Release_1.5.3_20200406_build_A

#### 功能构建

- 实现com.dwarfeng.fdr.impl.handler.mapper.MaxMapperMaker。
- 实现com.dwarfeng.fdr.impl.handler.mapper.MinMapperMaker。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200404_build_C

#### 功能构建

- 删除GroovyFilter和GroovyTrigger中无用的字段。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200404_build_B

#### 功能构建

- 优化GroovyFilter和GroovyTrigger工作时的异常抛出方式。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200403_build_A

#### 功能构建

- 实现Groovy过滤器以及Groovy触发器。
  - com.dwarfeng.fdr.impl.handler.filter.GroovyFilterMaker
  - com.dwarfeng.fdr.impl.handler.trigger.GroovyTriggerMaker

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.1_20200403_build_A

#### 功能构建

- 新建fdr-api模块。
- 升级dcti依赖至1.1.0.a。
- 删除项目自身TimedValue，使用dcti的TimedValue对象。
- 实现com.dwarfeng.fdr.api.integration.judge.FdrDubboRepository。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.0_20200402_build_A

#### 功能构建

- 优化数据查询架构。
- 修改assembly.xml，将LICENSE文件装配至工程目录。
- 更改持久值、被过滤值、被触发值在数据库中的定义为TEXT。
- 实现DctiKafkaPusher，原有的KafkaPusher更名为NativeKafkaPusher。
- 原有的KafkaSource更名为DctiKafkaSource，并优化配置。

#### Bug修复

- 修正启动器对象错误的日志文本。

#### 功能移除

- ~~删除 FilteredLookupService 服务。~~
- ~~删除 TriggeredLookupService 服务。~~

---

### Release_1.4.2_20200330_build_D

#### 功能构建

- 使用 @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 代替 @Scope("prototype")。
- 优化RecordQosServiceImpl的stopRecord方法。
- 调整com.dwarfeng.fdr.node.all.launcher.Launcher部分注释语序。

#### Bug修复

- 修正ConsumeHandlerImpl中错误的日志文本。
- 修正ConsumeHandlerImpl.ConsumeBuffer中的部分get方法未线程同步的bug。
- 修正ConsumeHandlerImpl.ConsumeBuffer.setBufferParameters潜在的取值问题bug。

#### 功能移除

- (无)

---

### Release_1.4.2_20200329_build_C

#### 功能构建

- 优化部分properties文件中的注释。

#### Bug修复

- 修正MultiPusher.SUPPORT_TYPE值不正确的bug。
- 修正KafkaPusher类的不正确的注解。

#### 功能移除

- (无)

---

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

- 修正node-all模块中pom.xml错误的配置。

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
