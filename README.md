# Slit
开源爬虫框架Slit

Slit的特点

1，较好的扩展性，不同模块之间解耦合，非常适合二次开发自己的爬虫框架。

2，使用上配置简单，容易上手，可配置是否带浏览器模拟真实抓取，项目非常轻。

3，可配置多线程，定义爬取深度，自动过滤重复url。

4，相比较传统爬虫，Slit能够解决动态页面(包含ajax局部页面)，cookie信息，登录等问题。





Slit项目一共有3个主要模块：

1，Fetcher模块，主要负责从url等待队列头取url，然后去下载页面，把整个页面结果放入已经下载页面队列。

2，Analyzer模块，主要负责从已经下载页面，提取想要的结果，放入抓取结果队列，另外再找出本页面上的其他连接，放入url等待队列。

3，Store模块，主要负责从抓取的结果队列中取出结果，然后根据用户自定义来怎么处理这些结果。




Slit的使用

1，定义一个类来继承AbstractNodeFilter，需要实现方法accept(),此方法用来提取页面上的节点。不同的用户可能想提取的结果都不同，这里的提取内容开放给用户自己定义。

2，定义一个类来继承AbstractFileStore，需要实现方法processResultNode(),此方法就是对抓取结果的处理。

3，启动Slit项目，

Config config = new Config();

config.setStartUrlList(Arrays.asList(“https://git.oschina.net/explore/recommend”));    //  需要爬取的url列表

config.setNodeFilterClass(MyNodeFilter.class);                                         //  步骤一中自定义的类

config.setFileStoreClass(MyFileStore.class);                                           //  步骤二中自定义的类

InitSlit slit = new InitSlit(config);

slit.start();                                                       //  启动, 可以设置不同模块线程数，详见类config






Slit的使用(带浏览器爬)

1，定义一个类来继承AbstractNodeFilter，需要实现方法accept(),此方法用来提取页面上的节点。不同的用户可能想提取的结果都不同，这里的提取内容开放给用户自己定义。

2，定义一个类来继承AbstractFileStore，需要实现方法processResultNode(),此方法就是对抓取结果的处理。

3，定义一个类来继承AbstractBrowserAction，需要实现方法userBrowserAction(),此方法是定义浏览器爬取行为的。

4，使用chrome浏览器，下载chrome对应系统的webDriver驱动文件。(下载地址：http://chromedriver.storage.googleapis.com/index.html)

5， 启动Slit项目，
Config config = new Config();

config.setNodeFilterClass(MyBrowserNodeFilter.class);         // 步骤一中定义的类

config.setFileStoreClass(MyBrowserFileStore.class);           // 步骤二中定义的类

config.setInBrowser(true);                                                      // 设置浏览器为true

config.setBrowserAction(MyBrowserAction.class);               // 步骤三中定义的类

config.setBrowserDriverPath(“/”);                 //  chrome对应系统的webDriver文件的路径

InitSlit init = new InitSlit(config);

init.start();





Slit使用到的技术

1，Slit主要依赖两个jar包，“org.htmlparser:htmlparser:2.1“ 和”org.seleniumhq.selenium:selenium-java:2.45.0“ ，其他还包含一些log日志包等。

2，不同模块之间通过线程安全非阻塞队列协同工作。



Slit使用到的jar包列表，在本项目的src/test/lib下都已经包含了这些jar包 ：
compile "commons-lang:commons-lang:2.6"
compile "org.htmlparser:htmlparser:2.1"
compile "org.seleniumhq.selenium:selenium-java:2.45.0"
compile "ch.qos.logback:logback-classic:1.1.3"
compile "org.codehaus.groovy:groovy:2.4.3"
compile "org.slf4j:slf4j-api:1.7.12"
compile "org.slf4j:jcl-over-slf4j:1.7.12"


