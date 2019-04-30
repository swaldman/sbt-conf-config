organization := "com.mchange"

version := "0.0.1-SNAPSHOT"

name := "sbt-conf-config"

sbtPlugin := true

/* 
 * sbt 1.x insists on typesafe-config 1.2.1, 
 * so newer API is not available in builds the plugin has loaded.
 * best to not make it available here.
 */

// libraryDependencies += "com.typesafe" % "config" % "1.3.4"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"

// publishing stuff

val nexus = "https://oss.sonatype.org/"
val nexusSnapshots = nexus + "content/repositories/snapshots"
val nexusReleases = nexus + "service/local/staging/deploy/maven2"

resolvers += ("releases" at nexusReleases)
resolvers += ("snapshots" at nexusSnapshots)

ThisBuild / publishTo := {
  if (isSnapshot.value) Some("snapshots" at nexusSnapshots ) else Some("releases"  at nexusReleases )
}
