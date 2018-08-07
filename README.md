# code
代码生成器
这个是使用spring boot写的面向spring boot 框架的代码生成工具，可以帮助团队快速的生成代码：dao(respository)+service+controller,也可以生成前端代码，由于现在前后端分类，前端代码比较老

不论是代码生成器还是生成的代码，都是基于（Spring Boot （JPA(hibernate)+Spring MVC））进行生成的，当然代码生成模板可以自定义

下载，运行主类即可。

记得配合gene进行使用，底层的复写的方法
## 使用总结
### No converter found for return value of type问题
Add the following dependency in pom.xml and issue will be resolved:
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.5.0</version>
  </dependency>
### 使用前提
需要project数据库
### 使用方法
配置好后，访问localhost:10001,只能是localhost，不能是192.168.42.xx
### 使用示例
-新建项目配置：
项目名称		trade_jd
GroupPackage		com.puyun.shopmanage
ModulePackage		trade
项目目录		e:/works/shopmanage/trade_jd
数据库		自动显示可用数据库，选择
代码模板		qnui模板
-选择数据库表，注释填入表名所指代的易读名称

