name := "spray-json"

version := "1.3.3-SNAPSHOT"

organization := "com.github.wangzaixiang"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.16" % "test",
  "org.specs2" %% "specs2-scalacheck" % "2.4.16" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
)

scalaVersion := "2.11.7"

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if ( version.value.endsWith("SNAPSHOT") )
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

// resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"