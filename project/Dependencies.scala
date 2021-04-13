import sbt._

object Dependencies {
  val logback = Seq("ch.qos.logback" % "logback-core" % "1.2.3")
  val slf4jApi = Seq("org.slf4j" % "slf4j-api" % "1.7.30")
  val slf4jSimple = Seq("org.slf4j" % "slf4j-simple" % "1.7.30")

  def reflections(scalaVersion: String) =
    Seq("org.reflections" % "reflections" % "0.9.10") ++
    Seq("org.scala-lang" % "scala-reflect" % scalaVersion)

  val scalatest = Seq("org.scalatest" %% "scalatest" % "3.1.2")

  val playJsonVersion = "2.8.1"
  val playJson = Seq("com.typesafe.play" %% "play-json" % playJsonVersion)

  val finagle = Seq("com.twitter" %% "finagle-http" % "20.1.0")
  val jacksonScala = Seq("com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.2")

  val awsLambda = Seq(
    "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
    "com.amazonaws" % "aws-lambda-java-events" % "3.0.0"
  )

  val config = Seq("com.typesafe" % "config" % "1.4.0")

  val testDependencies = scalatest.map(_ % Test)
  val commonDependencies = config ++ slf4jApi ++ slf4jSimple ++ logback

  def coreDependencies(scalaVersion: String) = reflections(scalaVersion) ++ commonDependencies ++ testDependencies
  val finagleDependencies = finagle ++ jacksonScala ++ commonDependencies ++ testDependencies
  val awsApiGatewayDependencies = awsLambda ++ commonDependencies ++ testDependencies
  val playJsonDependencies = playJson ++ commonDependencies ++ testDependencies
}
