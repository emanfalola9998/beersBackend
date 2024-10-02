name := """play-scala-seed"""
organization := "BeersRUs"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.15"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "BeersRUs.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "BeersRUs.binders._"

libraryDependencies ++= Seq(
  guice,
  "org.playframework" %% "play-slick"            % "6.1.0",
  "org.playframework" %% "play-slick-evolutions" % "6.1.0",
  "mysql" % "mysql-connector-java" % "8.0.32",
  "com.typesafe.play" %% "play-json" % "2.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
"org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test


)

// Force scala-xml version to resolve conflicts
dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"

// Enable Play modules
enablePlugins(PlayScala)

