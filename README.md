[![GitHub tag](https://img.shields.io/github/tag/fear-the-dice/auth.svg)](https://github.com/fear-the-dice/auth/tags)
[![Coverage Status](https://coveralls.io/repos/fear-the-dice/auth/badge.svg)](https://coveralls.io/r/fear-the-dice/auth)
[![Build Status](https://travis-ci.org/fear-the-dice/auth.svg)](https://travis-ci.org/fear-the-dice/auth)
[![GPL](https://img.shields.io/badge/license-GPLv2-green.svg)](http://www.gnu.org/licenses/gpl-2.0.html)


#Fear the Dice
A tool for helping manage combat in a turn based environment. Allowing DM's/GM's to control stats such as AC, HP, and damage taken for a group.

##Auth Server
Built in [Scala](http://www.scala-lang.org/).

## Setup

```
$ cd Fear_The_Dice_Auth_Server
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

## Apache Tomcat
Outside of development it is easiest to run this in Apache Tomcat, you can set that up as follows:

+ Download the latest version of Apache Tomcat 7.0 from [here](http://tomcat.apache.org/download-70.cgi)
+ Extract and install

    ```
    $ tar xfvz apache-tomcat-7.0.47.tar.gz
    $ sudo mkdir -p /usr/local
    $ sudo mv ~/Downloads/apache-tomcat-7.0.47 /usr/local
    $ sudo rm -f /Library/Tomcat
    $ sudo ln -s /usr/local/apache-tomcat-7.0.47 /Library/Tomcat
    $ sudo chown -R <your_username> /Library/Tomcat
    $ sudo chmod +x /Library/Tomcat/bin/*.sh
    ```

+ Now edit the config of Tomcat to run on the correct port

    In conf/server.xml replace the following:
    
    ```
    <Connector port="8080" protocol="HTTP/1.1"
        connectionTimeout="20000"
        redirectPort="8443" />
    ```
    
    With this:
    
    
    ```
    <Connector port="3500" protocol="HTTP/1.1"
        connectionTimeout="20000"
        redirectPort="8443" />
    ```

+ Lastly deply the auth server as a war server

    First build the war package (this will return the location of your new war file)

    ```
    sbt package
    [info] Compiling 6 Scala sources to /path/to/your/project/target/scala-2.9.1/classes...
    [info] Packaging /path/to/your/project/target/scala-2.9.1/yourproject_2.9.1-0.1.0-SNAPSHOT.war ...
    [info] Done packaging.
    ```
    
    Once you have built your war package and have the location you can copy it to tomcat
    
    ```
    $ rm -rf /Library/Tomcat/ROOT
    $ cp /path/to/your/project/target/scala-2.9.1/yourproject_2.9.1-0.1.0-SNAPSHOT.war /Library/Tomcat/ROOT.war
    ```    
    
+ Lastly start the server

    ```
    $ /Library/Tomcat/startup.sh
    ```

##License
This tool is protected by the [GNU General Public License v2](http://www.gnu.org/licenses/gpl-2.0.html).

Copyright [Jeffrey Hann](http://jeffreyhann.ca/) 2015
