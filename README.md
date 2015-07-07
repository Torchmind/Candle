[![License](https://img.shields.io/github/license/Torchmind/Candle.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Latest Tag](https://img.shields.io/github/tag/Torchmind/Candle.svg?style=flat-square&label=Latest Tag)](https://github.com/Torchmind/Candle/tags)
[![Latest Release](https://img.shields.io/github/release/Torchmind/Candle.svg?style=flat-square&label=Latest Release)](https://github.com/Torchmind/Candle/releases)

Candle Configuration Format
===========================

Table of Contents
-----------------
* [About](#about)
* [Contacts](#contacts)
* [Issues](#issues)
* [Building](#building)
* [Contributing](#contributing)

About
-----

A simple human readable configuration file format.

Contacts
--------

* [IRC #Akkarin on irc.spi.gt](http://irc.spi.gt/iris/?nick=Guest....&channels=Akkarin&prompt=1) (alternatively #Akkarin on esper.net)
* [GitHub](https://github.com/Torchmind/Candle)

Using
-----

When running maven you may simply add a new dependency along with our repository to your ```pom.xml```:

```xml
<repository>
        <id>torchmind</id>
        <url>https://maven.torchmind.com/snapshot/</url>
</repository>

<dependencies>
        <dependency>
                <groupId>com.torchmind.candle</groupId>
                <artifactId>core</artifactId>
                <version>1.0-SNAPSHOT</version>
        </dependency>
</dependencies>
```

Loading a configuration file by using the Candle implementation:
```java
Candle candle = new Candle ();
candle.read (new File("configuration.cndl"));

if (candle.getBoolean ("some.test.node")) {
        System.out.println ("I'm doing things!");
}
```

Creating a configuration file by using the Candle implementation:
```java
Candle candle = new Candle ();

ContainerNode container1 = new ContainerNode ("some");
ContainerNode container2 = new ContainerNode ("test");

container1.append (container2);
candle.append (container1);

CommentNode comment = new CommentNode ("This is a test configuration file!");
container2.append (comment);

BooleanNode value = new BooleanNode ("node", true);
container2.append (value);

candle.write (new File ("configuration.cndl"));
```

Above examples would load/produce the following configuration file:
```candle
some {
        test {
                // This is a test configuration file!
                node = true
        }
}
```

Reading/Writing a configuration file by using the Candle Object Mapper:
```java
public class Configuration {
        private final CandleMapper mapper = new CandleMapper (this);

        @Property
        @Comment ("First root-level test variable")
        private boolean value = true;
        
        @Property ("some.test.node")
        @Comment ("First lower-level test variable")
        private int value = 42;
        
        // Injection Method
        public void load (File file) throws CandleException, IOException {
                this.mapper.inject (file);
        }
        
        public void save (File file) throws CandleException, IOException {
                this.mapper.write (file);
        }
        
        // Construction Method
        public static Configuration load (File file) {
                CandleMapper mapper = new CandleMapper (Configuration.class);
                return mapper.construct (file);
        }
}

Issues
------

You encountered problems with the library or have a suggestion? Create an issue!

1. Make sure your issue has not been fixed in a newer version (check the list of [closed issues](https://github.com/Torchmind/Candle/issues?q=is%3Aissue+is%3Aclosed)
1. Create [a new issue](https://github.com/Torchmind/Candle/issues/new) from the [issues page](https://github.com/Torchmind/Candle/issues)
1. Enter your issue's title (something that summarizes your issue) and create a detailed description containing:
   - What is the expected result?
   - What problem occurs?
   - How to reproduce the problem?
   - Crash Log (Please use a [Pastebin](http://www.pastebin.com) service)
1. Click "Submit" and wait for further instructions

Building
--------

1. Clone this repository via ```git clone https://github.com/Torchmind/Candle.git``` or download a [zip](https://github.com/Torchmind/Candle/archive/master.zip)
1. Build the modification by running ```mvn clean install```
1. The resulting jars can be found in ```api/target```, ```core/target``` and ```mapper/target```

Contributing
------------

Before you add any major changes to the library you may want to discuss them with us (see [Contact](#contact)) as
we may choose to reject your changes for various reasons. All contributions are applied via [Pull-Requests](https://help.github.com/articles/creating-a-pull-request).
Patches will not be accepted. Also be aware that all of your contributions are made available under the terms of the
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt). Please read the [Contribution Guidelines](CONTRIBUTING.md)
for more information.
