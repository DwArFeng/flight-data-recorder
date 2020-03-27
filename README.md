# flight-data-recorder

一款开箱即用的数据收集的解决方案。

提供数据点的定义，数据的记录、过滤、触发、事件推送等于数据收集有关的丰富的功能。

*Variety of data source, to one data repository, with kinds of event pusher, that is FDR.*

---

## 项目的使用

### 数据点定义

1. 数据点是一切的基本
  
   数据点是该项目中的基本单位，您可以创建一个数据点，并且指定该数据点的存储策略、过滤器设置、触发器设置。
   
   数据点一旦被建立，最好不要删除，同时取消数据点的持久数据记录和实时数据记录便可在功能上禁用一个数据点。
   
2. 存储策略

   您可以单独指定数据点是否记录实时数据或者是否记录持久数据。
   
   同时取消数据点的持久数据记录和实时数据记录便可在功能上完全禁用一个数据点，这可以替代数据点的删除。
   
3. 过滤器

   预制的过滤器可以判断一个数据点是否合法，并且将不合法的数据排除在外，并且生成数据被过滤的事件。
   
   过滤器一旦建立，最好不要删除，可以禁用某个过滤器以代替删除效果。
   
4. 触发器

   预制的触发器可以判断一个数据点中的值是否符合某个条件，并且在符合条件的时候生成触发事件，并且额外的将数据点记录在被触发数据中。

   触发器一旦建立，最好不要删除，可以禁用某个触发器以代替删除效果。

### 数据记录

1. 实时数据
   
   实时数据可以反映某个数据点瞬时状态的最新数据。
   
   可以选择开启某一个数据点的实时记录。当数据点的实时数据记录被开启时，每次收到更新的数据信息后，该项目会
   更新数据点的实时数据库，并且向当前的事件处理器中推送实时数据更新事件。
   
2. 持久数据

   持久数据用于保存某个数据点采集的所有数据。

   可以选择开启某一个数据点的持久记录。当数据点的持久数据记录被开启时，每次收到更新的数据信息后，该项目会
   更新数据点的持久数据库，并且向当前的事件处理器中推送持久数据记录事件。

3. 过滤数据

   过滤器用于阻拦不合法的数据，保证持久数据以及触发数据的格式正确性。

   可以为某一个数据点指定过滤器。当数据点拥有过滤器时，每次收到更新的数据信息后，则会根据过滤器信息对该点进行过滤，如果数据
   满足过滤条件，则本条数据信息不会被记录到持久数据中，而是记录到被触发数据中，并且向当前的事件处理器推送数据被过滤事件。

4. 触发数据

   触发器用监视某一个数据点，当某个点达到某些状态时及时触发事件，起到报警作用。

   可以为某一个数据点指定触发器。当数据点拥有触发器时，每次收到更新的数据信息后，则会根据触发器信息对该点进行触发，如果数据
   满足触发条件，则本条数据信息会额外的被记录在触发数据中，并且向当前的事件处理器推送数据被触发事件。

### 数据查询

1. 通过维护接口

   维护接口提供了一定的数据查询功能，包括主键查询和样板查询。
   
   样板查询说明
   
   * `PersistenceValueMaintainService`
   
      |样板名称|样板说明|传输参数|
      |---|---|---|
      |child_for_point|属于指定数据点的所有持久数据|数据点的longIdKey|
      |child_for_point_between|属于指定数据点，并且在指定时间区间内的所有持久数据|数据点的longIdKey, 起始时间, 结束时间

   * `FilteredValueMaintainService`
   
      |样板名称|样板说明|传输参数|
      |---|---|---|
      |child_for_point|属于指定数据点的所有被过滤数据|数据点的longIdKey|
      |child_for_point_between|属于指定数据点，并且在指定时间区间内的所有被过滤数据|数据点的longIdKey, 起始时间, 结束时间
      |child_for_filter|属于指定过滤器的所有被过滤数据|过滤器的longIdKey|
      |child_for_filter_between|属于指定过滤器，并且在指定时间区间内的所有被过滤数据|过滤器的longIdKey, 起始时间, 结束时间
      |child_for_set|属于指定过滤器集合的所有被过滤数据|过滤器的longIdKey组成的List|

   * `TriggeredValueMaintainService`
   
      |样板名称|样板说明|传输参数|
      |---|---|---|
      |child_for_point|属于指定数据点的所有被触发数据|数据点的longIdKey|
      |child_for_point_between|属于指定数据点，并且在指定时间区间内的所有被触发数据|数据点的longIdKey, 起始时间, 结束时间
      |child_for_trigger|属于指定触发器的所有被触发数据|触发器的longIdKey|
      |child_for_trigger_between|属于指定触发器，并且在指定时间区间内的所有被触发数据|触发器的longIdKey, 起始时间, 结束时间
      |child_for_set|属于指定触发器集合的所有被触发数据|触发器的longIdKey组成的List|
      
2. 数据查询接口

   数据查询接口提供数据的按照时间区间查询以及映射查询。
   其中，部分数据查询接口中的方法与维护接口中的样板查询方法作用是一致的，只不过是换种表达方式。数据查询接口中的映射查询是数据
   查询接口特有的方法。
   
   数据映射查询是指将查询出的数据按照某种规则进行预处理，并且返回已经预处理好的值。类似于返回一段时间数据点的中位数、平均数、
   单词统计都可以使用数据的映射查询实现。
   
   需要指出的是，映射查询只能满足少部分查询需求。更多复杂的数据处理应该更多的在其它项目中完成。

3. 加速

   FDR 针对部分查询提供了原生SQL加速的功能。
   
   在使用加速功能的情况下，数据接口的时间查询，以及对应的维护服务的样板查询将会直接通过jdbc使用sql语句进行查询，从而大大提高
   查询的效率。
   
   然而，sql语句意味着不通用，针对于不同的数据库，您可能需要针对不同的数据库编写相关的 `NSQLGenerator`, 目前该项目对于此接口
   提供了 `MySQL8NSQLGeneratorImpl` 实现，这意味着该项目本身支持 MySQL 的原生SQL加速。
   
   开启原生SQL加速，需要在 `conf/database/performance.properties` 中设置属性
   ```properties
   # 针对持久数据点、被过滤数据点、被触发数据点的批量操作使用原生的SQL语句以提高查询效率。
   # 一旦启用原生SQL，针对持久数据点、被过滤数据点、被触发数据点的任何批量查询以及数量查询
   # 都会由原生SQL以JDBC的形式直接查询，从而提高效率。
   # 提高效率固然是好事，但令人遗憾的是，原生SQL查询只支持很少的几种数据库。
   # 使用原生SQL时，会尝试根据 hibernate.dialect 属性寻找可用的原生SQL生成器。
   # 如果原生数据库SQL是不支持的，该程序将会继续使用基于hibernate的查询，并且会生成一条警告。
   # 欢迎大家持续贡献其它种类的数据库支持。
   hibernate.accelerate.using_native_sql=true
   ```
   同时注意，SQL原生查询的实效条件：当检测到不支持的原生SQL查询，或者当前的原生SQL查询出现异常时，原生SQL查询便会失效，这时
   程序会将查询方式转为一般查询。由于原生SQL查询的速度是一般查询的10倍以上，这将导致查询速度变得显而易见的缓慢。

---

## 项目的扩展

1. 数据源的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.Source` 并将实现类注入到spring的IoC容器中。

2. 触发器的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.FilterMaker` 并将实现类注入到spring的IoC容器中。
   
3. 过滤器的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.TriggerMaker` 并将实现类注入到spring的IoC容器中。

4. 推送器的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.TriggerMaker` 并将实现类注入到spring的IoC容器中。
   
   设定配置文件 `conf/fdr/push.properties`
   ```properties
   ###################################################
   #                     global                      #
   ###################################################
   # 当前的推送器类型。
   # 目前该项目支持的推送器类型有:
   #   drain: 简单的丢弃掉所有消息的推送器。
   #   partial_drain: 丢弃掉部分消息的推送器。
   #   multi: 同时将消息推送给所有代理的多重推送器。
   #   kafka: 基于Kafka消息队列的推送器。
   #
   # 对于一个具体的项目，很可能只用一个推送器。此时希望加载
   # 推送器时只加载需要的那个，其余的推送器不加载。这个需求
   # 可以通过编辑 application-context-scan.xml 实现。
   pusher.type=kafka
   ```
   将 pusher.type 改为扩展的 pusher。

5. 映射器的扩展

   实现接口 `com.dwarfeng.fdr.impl.handler.MapperMaker` 并将实现类注入到spring的IoC容器中。

6. 原生SQL生成器的扩展

   实现接口 `com.dwarfeng.fdr.impl.dao.NSQLGenerator` 并将实现类注入到spring的IoC容器中。
   
   设置数据库连接 `conf/database/connection.properties`
   ```properties
   jdbc.driver=com.mysql.cj.jdbc.Driver
   jdbc.url=jdbc:mysql://your-datapase-address/your-database-name
   jdbc.username=root
   jdbc.password=your-password-here
   hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```
   更改属性 hibernate.dialect 为您使用的数据库的方言，并且使得实现的 NSQLGenerator 中 
   ```java
   public boolean supportType(String type){
      // 此处type传入的便是 hibernate.dialect 的值，判断是否与您的实现支持的dialect相等。
      return Objects.equals("org.hibernate.dialect.MySQL8Dialect", type);
   }
   ```
