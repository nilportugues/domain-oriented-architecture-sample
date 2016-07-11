import sbt.Keys._
import sbt._
import sbtdocker.DockerPlugin
import sbtdocker.DockerPlugin.autoImport._

object DOAExampleProject extends Build {
  lazy val DefaultConfiguration = Seq(
    scalaVersion := "2.11.8",
    dockerfile in docker := {
      val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
      val classpath = (managedClasspath in Compile).value
      val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
      val jarTarget = s"/app/${jarFile.getName}"
      // Make a colon separated classpath with the JAR file
      val classpathString = classpath.files.map("/app/" + _.getName)
        .mkString(":") + ":" + jarTarget
      new Dockerfile {
        // Base image
        from("java")
        // Add all files on the classpath
        add(classpath.files, "/app/")
        // Add the JAR file
        add(jarFile, jarTarget)
        // On launch run Java with the classpath and the main class
        entryPoint("java", "-cp", classpathString, mainclass)
      }
    }

  )

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

  lazy val ServiceConfiguration = DefaultConfiguration ++ Seq(
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