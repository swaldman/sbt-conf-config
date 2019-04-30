package com.mchange.sc.v1.sbtconfconfig

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.Def.Initialize

import com.typesafe.config.{Config => TSConfig, ConfigFactory => TSConfigFactory}

object SbtConfConfig extends AutoPlugin {

  object autoImport {
    val confDirectory              = settingKey[File]("The directory from which local configuration should be loaded.")
    val typesafeConfigFileBaseName = settingKey[String]("The basename of the file(s) from which local configuration should be loaded.")

    val typesafeConfig             = settingKey[TSConfig]("The loaded configuration, including system properties, local configuration, default application config, default reference config.")
  }

  import autoImport._

  lazy val defaults : Seq[sbt.Def.Setting[_]] = Seq(
    Global / confDirectory := { baseDirectory.value / "conf" },
    Global / typesafeConfigFileBaseName := { "application" },
    Global / typesafeConfig := {
      val confDir  = confDirectory.value
      val baseName = typesafeConfigFileBaseName.value

      val localConfig = TSConfigFactory.parseFileAnySyntax( new File(confDir, baseName) )

      /* 
       * sbt 1.x insists on typesafe-config 1.2.1, so defaultApplication() is not available
       * 
       * TSConfigFactory.systemProperties().withFallback(
       *  localConfig.withFallback(
       *    TSConfigFactory.defaultApplication().withFallback(
       *      TSConfigFactory.defaultReference()
       *    )
       *  )
       * )
       */

      TSConfigFactory.systemProperties().withFallback( localConfig.withFallback( TSConfigFactory.load() ) )
    }
  )

  // plug-in setup

  override def requires = JvmPlugin

  override def trigger = allRequirements

  override def projectSettings = defaults

}
