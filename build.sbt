name := "spray-json"

version := "1.3.2"

organization := "com.github.wangzaixiang"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.16" % "test",
  "org.specs2" %% "specs2-scalacheck" % "2.4.16" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
)

scalaVersion := "2.11.7"