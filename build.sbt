import Dependencies._

val scala212 = "2.12.13"
val scala213 = "2.13.5"

val javacSettings = Seq(
  "-source", "1.8",
  "-target", "1.8",
  "-Xlint:deprecation",
  "-Xlint:unchecked"
)

def scalacOpts: Seq[String] = Seq(
  "-target:jvm-1.8",
  "-deprecation",
  "-feature",
  "-encoding", "UTF-8",

  "-Ywarn-unused:imports",
  "-Xlint:nullary-unit",

  "-Xlint",
  "-Ywarn-dead-code"
)

lazy val commonSettings = Def.settings(
  scalafmtOnCompile := false,
  scapegoatVersion in ThisBuild := "1.4.8",
  organization := "koapi",
  version := "0.0.1-RC1-SNAPSHOT",
  scalaVersion := scala213,
  crossScalaVersions := Seq(scala213, scala212),
  scalacOptions ++= scalacOpts,
  scalacOptions in (Compile, doc) ++= Seq("-Xfatal-warnings"),
  javacOptions in Compile ++= javacSettings,
  javacOptions in Test ++= javacSettings
)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= coreDependencies(scalaVersion.value))

lazy val `finagle-http` = project
  .in(file("finagle-http"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= finagleDependencies)
  .dependsOn(core)

lazy val `aws-api-gateway` = project
  .in(file("aws-api-gateway"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= awsApiGatewayDependencies)
  .dependsOn(core)

lazy val `play-json` = project
  .in(file("play-json"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= playJsonDependencies)
  .dependsOn(core)

lazy val `examples` = project
  .in(file("examples"))
  .settings(commonSettings)
  .settings(mainClass := Some("koapi.finagle.Main"))
  .dependsOn(core)
  .dependsOn(`finagle-http`)
  .dependsOn(`play-json`)

lazy val root = project
  .in(file("."))
  .settings(
    name := "koapi",
  )
  .settings(commonSettings)
  .aggregate(
    `core`,
    `finagle-http`,
    `aws-api-gateway`,
    `play-json`,
    `examples`
  )

addCommandAlias("validate", ";clean;scalafmtCheck;scapegoat;coverage;test;coverageReport")

ThisBuild / publishMavenStyle := true
