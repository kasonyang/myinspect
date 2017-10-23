title=Myinspect
date=2017-10-11
type=post
tags=
status=published
~~~~~~

# Overview

Myinspect is a sql inspector for projects using mybatis.

# Usage

NOTE:The inspector only works in projects building with gradle.

1.add one of the follow code to your `gradle.build` file:

Example1:

    //specify your database driver class
    System.setProperty("myinspect.db.driver","com.mysql.cj.jdbc.Driver");
    //specify your database configuration file.only support properties file.
    System.setProperty("myinspect.db.config.file","src/main/resources/application.properties")
    //specify your keys for url,user and password in your configuration file
    System.setProperty("myinspect.db.config.url-key","datasource.url")
    System.setProperty("myinspect.db.config.user-key","datasource.user")
    System.setProperty("myinspect.db.config.password-key","datasource.password")
    //add myinspect dependency
    dependencies{
      compileOnly 'site.kason:myinspect:VERSION'
    }

Example2:

    //specify your database driver class
    System.setProperty("myinspect.db.driver","com.mysql.cj.jdbc.Driver");
    //specify the data source,including url,user and database
    System.setProperty("myinspect.db.url","jdbc:mysql://localhost/YOURDATABASE");
    System.setProperty("myinspect.db.user","root");
    System.setProperty("myinspect.db.password","YOURPASSWORD");
    //add myinspect dependency
    dependencies{
      compileOnly 'site.kason:myinspect:VERSION'
    }

2.The inspector runs when you execute `gradle build` or any tasks which depends on task`compileJava`.

## output example

    > Task :jsm-business:compileJava
    myinspect:Using inspect Analyzers:[SyntaxAnalyzer]

    ERROR:You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'where  `out_trade`.`id`  =  0' at line 1
    at jsm.mapper.base.DefaultDeleteOutTradeTrait::deleteById(long)
     delete `out_trade`  where  `out_trade`.`id`  =  0

    ERROR:Table 'jsm.Admin' doesn't exist
    at jsm.mapper.base.DefaultUpdateAdminTrait::update(jsm.entity.Admin)
    update Admin set  `name`=0  ,  `password`=0    where id=0

    WARNING:delete/update table without where condition
    at app.mapper.AnnounceMapper::delete()
    delete from `Announce`
    ...
    ...
    myinspect:112 sql inspected. 20 diagnosis provided.

