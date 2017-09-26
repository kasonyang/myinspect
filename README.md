# What's Myinspect?

Myinspect is a sql inspector for projects using mybatis.

# How to use?

gradle:

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
      compileOnly 'site.kason:myinspect:XXX'
    }
