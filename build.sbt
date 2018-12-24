import sbt.ExclusionRule

name := "functionalProgrammingScala"

version := "0.1"

scalaVersion := "2.12.8"


// Dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
)
