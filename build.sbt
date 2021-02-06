import sbtrelease.ReleaseStateTransformations._

name := "hungrig"
organization := "com.hungrig"

scalaVersion := "2.13.4"

ThisBuild / scalafmtOnCompile := true

lazy val configVersion = "0.14.0"
lazy val logVersionClassic = "1.2.3"
lazy val logVersion = "3.9.2"
lazy val scalaTestVersion = "3.1.1"
lazy val akkaHttpVersion = "10.1.11"
lazy val AkkaVersion = "2.6.10"
lazy val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % logVersionClassic,
  "com.typesafe.scala-logging" %% "scala-logging" % logVersion,
  "com.github.pureconfig" %% "pureconfig" % configVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion
)

enablePlugins(DockerPlugin)
dockerfile in docker := {
  val artifact: File = assembly.value
  val mainClass = "com.hungrig.Main"
  val jarTarget = s"/app/${artifact.name}"
  println(artifact.name)
  println(jarTarget)
  new Dockerfile {
    // Base image
    from("gcr.io/atta-277923/base-image:1.0.0")
    // Add all files on the classpath
    add(artifact, jarTarget)
    cmdRaw("/bin/sh -c \"java -cp " + jarTarget + " " + mainClass + "\"")
  }
}

docker / imageNames := Seq(ImageName(s"gcr.io/atta-277923/${name.value}:${version.value}"))

releaseCommitMessage := s"Setting version to ${(version in ThisBuild).value} for hungrig"

releaseProcess := Seq
  .empty[ReleaseStep] // disable publish to nexus (should be enabled explicitly for RELEASE_PUBLISH)

releaseProcess ++= (if (sys.env.contains("RELEASE_VERSION_BUMP"))
                      Seq[ReleaseStep](
                        checkSnapshotDependencies,
                        inquireVersions,
                        setReleaseVersion,
                        commitReleaseVersion
                      )
                    else Seq.empty[ReleaseStep])

releaseProcess ++= (if (sys.env.contains("RELEASE_PUBLISH"))
                      Seq[ReleaseStep](inquireVersions, setNextVersion, commitNextVersion)
                    else Seq.empty[ReleaseStep])
