# flight-data-recorder

一款开箱即用的数据收集的解决方案。

提供数据点的定义，数据的记录、过滤、触发、事件推送等于数据收集有关的丰富的功能。

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

1. 数据查询接口

TODO

2. 映射器

TODO

3. 加速

TODO

---

## 项目的扩展

   TODO