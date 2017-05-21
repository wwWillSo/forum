# FORUM
后台框架：spring, spring boot, springMVC, spring-data-jpa
前端：jquery， bootstrap(css库)
前端模板引擎：thymeleaf
数据库：mysql
运行环境：jdk1.8, windows

程序包（可直接运行）：
1、程序包在doc文件夹中，名为forum-deployment.zip，将此zip解压出来
2、在config里面包含两个配置文件 web.properties内可配置端口，这里已经写了8080。application.properties内可配置mysql连接，项目移植的时候切记要修改数据库连接配置（数据库用户名与密码）
3、配置确定无误之后，双击run.bat即可运行
4、项目运行之后在浏览器输入localhost:8080为用户登录页，localhost:8080/admin为管理员登陆页（端口号可在web.properties自行配置）

项目导入步骤：
1、进入eclipse之类的IDE（我自己的是Spring tool suit）
2、在左侧workspace中右键import-->maven-->existing maven projects-->选择此项目文件夹内的pom.xml-->确定导入
3、项目导入到workspace中之后右键项目-->maven-->update project-->点击ok之后eclipse将会自动下载所需的jar包
4、数据库sql文件在sql文件夹中，是mysql数据库的

项目运行注意事项：
1、此项目使用spring boot实现，内置了一个简单的tomcat，所以无需特意去配置一个服务器
2、右键项目-->run as java application即可运行（像非web项目一样运行main方法就可以了，main方法在com.forum.ApplicationLauncher）
3、项目内的config里面包含两个配置文件 web.properties内可配置端口，这里已经写了8080。application.properties内可配置mysql连接，项目移植的时候切记要修改数据库连接配置（数据库用户名与密码）
4、项目运行之后在浏览器输入localhost:8080为用户登录页，localhost:8080/admin为管理员登陆页（端口号可在web.properties自行配置）
