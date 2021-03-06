import com.earldouglas.xsbtwebplugin.PluginKeys._
import com.earldouglas.xsbtwebplugin.WebPlugin._

import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object FearTheDiceAuthBuild extends Build {
  val Organization = "com.fearthedice"
  val Name = "Fear The Dice Auth"
  val Version = "0.2.0"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.4.0.RC1"

  lazy val project = Project (
    "fear-the-dice-auth",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      port in container.Configuration := 3500,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      resolvers += Classpaths.sbtPluginReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "compile;container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "compile;container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0",
        "com.jason-goodwin" %% "authentikat-jwt" % "0.4.1",
        "net.debasishg" %% "redisclient" % "3.0"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  ).enablePlugins(JavaAppPackaging)
}
