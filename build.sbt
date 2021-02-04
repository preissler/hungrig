name := "hungrig"

scalaVersion := "2.13.4"

lazy val configVersion = "0.14.0"
lazy val logVersionClassic = "1.2.3"
lazy val logVersion = "3.9.2"
lazy val scalaTestVersion = "3.1.1"
lazy val akkaHttpVersion = "10.1.11"
lazy val AkkaVersion = "2.6.10"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % logVersionClassic,
  "com.typesafe.scala-logging" %% "scala-logging" % logVersion,
  "com.github.pureconfig" %% "pureconfig" % configVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test
)
