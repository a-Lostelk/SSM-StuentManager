### 项目介绍

一个基于SSM的学生信息管理系统，由于本人尚在大二，实现功能有限，代码简陋，所幸基本的业务功能和前后端交互没有存在bug，有待深入继续学习

Go！Go！Go！


 
#### 使用技术和工具

- 系统环境：Windows
- 开发工具: ID1.8EA、Maven、Git
- JDK版本：jdk1.8
- 服务器：Tomcat 9.0
- 数据库：MySQL 5.7
- 使用技术：Spring + Mybatis + SpringMVC后台框架 面向接口接口编程 前端easyUI框架 



#### 学习笔记和记录

学生信息管理系统笔记



#### 项目截图

（基本页面功能都实现了，截取部分项目截图）
- 登录页面（学生和管理员）

  ![](https://github.com/a-Lostelk/SSM-StuentManager/blob/master/Images/QQ%E6%88%AA%E5%9B%BE20190903085604.png)
  
- 主页面

 ![](https://github.com/a-Lostelk/SSM-StuentManager/blob/master/Images/QQ%E6%88%AA%E5%9B%BE20190903085801.png)
 
- 学生管理界面
  分类查询
 ![](https://github.com/a-Lostelk/SSM-StuentManager/blob/master/Images/QQ%E6%88%AA%E5%9B%BE20190910161543.png)



***登录时选择学生或者管理员身份登录，登录成功权限也不一样，管理员拥有全部权限，学生只拥有修改和查询的权限***

*（实际上，学生用户登录后的页面和管理员登录后页面是一致的，使用JSTL标签从session中获得登录用户的type判断是学生还是管理员，然后将某些操作的页面元素隐藏，学生用户不可视）*

- 学生登录
- 管理员登录



#### 大致结构
