name := "spray-json"

version := "1.3.5"

organization := "com.github.wangzaixiang"

//publishConfiguration := publishConfiguration.value.withOverwrite(true)
//PgpKeys.publishSignedConfiguration := PgpKeys.publishSignedConfiguration.value.withOverwrite(true)

libraryDependencies ++= Seq(

  "org.scala-lang" % "scala-reflect" % scalaVersion.value,

  "org.specs2" %% "specs2-core" % "4.3.3" % "test",
  "org.specs2" %% "specs2-scalacheck" % "4.3.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
)

scalaVersion := "2.12.4"


crossScalaVersions := Seq("2.11.11", "2.12.4")

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/wangzaixiang/spray-json</url>
  <licenses>
  <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
  </license>
  </licenses>
  <scm>
    <url>https://github.com/wangzaixiang/spray-json</url>
    <connection>scm:https://github.com/wangzaixiang/spray-json</connection>
  </scm>
  <developers>
    <developer>
      <id>wangzx</id>
      <name>wang zaixiang</name>
      <url>https://github.com/wangzaixiang</url>
    </developer>
  </developers>
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if ( version.value.endsWith("SNAPSHOT") )
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

// resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
