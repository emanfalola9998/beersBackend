name := "play-scala-seed"

organization := "BeersRUs"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, JavaAppPackaging)

libraryDependencies ++= Seq(
  guice,
  "org.playframework" %% "play-slick"             % "6.1.0",
  "org.playframework" %% "play-slick-evolutions"  % "6.1.0",
  "mysql"              % "mysql-connector-java"   % "8.0.32",
  "com.typesafe.play" %% "play-json"              % "2.9.2",
  "ch.qos.logback"     % "logback-classic"        % "1.2.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
)

// Dependency conflict resolution (Play 2.8.x needs older XML versions sometimes)
dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"

// Optional: Twirl or route imports (uncomment if needed)
// TwirlKeys.templateImports += "BeersRUs.controllers._"
// play.sbt.routes.RoutesKeys.routesImport += "BeersRUs.binders._"
