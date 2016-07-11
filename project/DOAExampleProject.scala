import java.io.{File, PrintWriter}

import sbt.Keys._
import sbt._
import sbtdocker.DockerPlugin
import sbtdocker.DockerPlugin.autoImport._

object DOAExampleProject extends Build {
  lazy val DefaultConfiguration = Seq(
    scalaVersion := "2.11.8"
  )

  lazy val DockerizedConfiguration = Seq(
    dockerfile in docker := {
      val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
      val outputServiceJar: File = sbt.Keys.`package`.in(Compile, packageBin).in(outputService).value
      val inputServiceJar: File = sbt.Keys.`package`.in(Compile, packageBin).in(inputService).value

      val classpath = (managedClasspath in Compile).value
      val outputClasspath = (managedClasspath in Compile in outputService).value
      val inputClasspath = (managedClasspath in Compile in inputService).value

      val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
      val outputMainclass = mainClass.in(Compile, packageBin).in(outputService).value.getOrElse(sys.error("Expected exactly one main class"))
      val inputMainclass = mainClass.in(Compile, packageBin).in(inputService).value.getOrElse(sys.error("Expected exactly one main class"))

      val jarTarget = s"/app/${jarFile.getName}"
      val outputJarTarget = s"/app/${outputServiceJar.getName}"
      val inputJarTarget = s"/app/${inputServiceJar.getName}"
      // Make a colon separated classpath with the JAR file
      val classpathString = classpath.files.map("/app/" + _.getName)
        .mkString(":") + ":" + jarTarget

      val outputClasspathString = outputClasspath.files.map("/app/" + _.getName)
        .mkString(":") + ":" + outputJarTarget

      val inputClasspathString = inputClasspath.files.map("/app/" + _.getName)
        .mkString(":") + ":" + inputJarTarget

      val startAllFile = File.createTempFile("_startAll", ".sh")
      new PrintWriter(startAllFile) {
        write(
          s"""
            |#!/bin/sh
            |java -cp $outputClasspathString $outputMainclass &
            |java -cp $inputClasspathString $inputMainclass &
            |java -cp $classpathString $mainclass
          """.stripMargin.trim)
        close()
      }

      new Dockerfile {
        // Base image
        from("java")
        // Add all files on the classpath
        add(classpath.files, "/app/")
        add(outputClasspath.files, "/app/")
        add(inputClasspath.files, "/app/")
        // Add the JAR file
        add(jarFile, jarTarget)
        add(outputServiceJar, outputJarTarget)
        add(inputServiceJar, inputJarTarget)
        add(startAllFile, "/bin/start-all.sh")
        // On launch run Java with the classpath and the main class
        run("chmod", "+x", "/bin/start-all.sh")
        cmd("./bin/start-all.sh")
      }
    }
  ) ++ DefaultConfiguration

  object Dependencies {
    lazy val SpringBoot = Seq(
      "org.springframework.boot" % "spring-boot-starter" % "1.3.6.RELEASE",
      "org.springframework.boot" % "spring-boot-starter-web" % "1.3.6.RELEASE",
      "org.springframework.boot" % "spring-boot-starter-tomcat" % "1.3.6.RELEASE" % "provided"
    )

    lazy val Camel = Seq(
      "org.apache.camel" % "camel-scala" % "2.17.2",
      "org.apache.camel" % "camel-jms" % "2.17.2",
      "org.apache.camel" % "camel-ahc" % "2.17.2",
      "org.apache.camel" % "camel-netty4-http" % "2.17.2",
      "org.apache.activemq" % "activemq-client" % "5.13.3",
      "org.apache.activemq" % "activemq-camel" % "5.13.3"
    )

    lazy val HttpComponents = Seq(
      "org.apache.httpcomponents" % "httpclient" % "4.5.2" exclude("commons-logging","commons-logging")
    )

    lazy val Logging = Seq(
    )

    lazy val Parsing = Seq(
      "org.scala-lang" % "scala-xml" % "2.11.0-M4"
    )
  }

  import Dependencies._

  lazy val CamelConfiguration = DefaultConfiguration ++ Seq(
    libraryDependencies ++= Camel ++ Logging ++ Parsing
  )

  lazy val ServiceConfiguration = DockerizedConfiguration ++ Seq(
    libraryDependencies ++= SpringBoot ++ Logging
  )

  lazy val ALConfiguration = DefaultConfiguration ++ Seq(
    libraryDependencies ++= HttpComponents ++ Logging
  )

  lazy val cmsAL = project.settings(ALConfiguration)
  lazy val cmsApplication = project.settings(ServiceConfiguration).dependsOn(cmsAL).enablePlugins(DockerPlugin)
  lazy val webApplication = project.settings(ServiceConfiguration).dependsOn(cmsAL).enablePlugins(DockerPlugin)
  lazy val outputService = project.settings(CamelConfiguration)
  lazy val inputService = project.settings(CamelConfiguration)
}