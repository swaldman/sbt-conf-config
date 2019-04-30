# sbt-conf-config

A tiny sbt plugin to let builds read typesafe config from a conf directory.

Builds that use this plugin can define a file called `conf/application.conf` 
(or `conf/application.json` or `conf/application.properties`), then the setting `typesafeConfig` 
will be a Typesafe [Config](https://www.javadoc.io/doc/com.typesafe/config/1.2.1) object containing, 
in the following order of preference:

1. System Properties
2. Local config (from `conf/application.*`)
3. The default `Config` that Typesafe Config's `ConfigFactory.load()` would load.

You can override the `conf` directory with setting `confDirectory`. You can override the basename of the
local configuration from `application` by overriding setting `typesafeConfigFileBaseName`.
