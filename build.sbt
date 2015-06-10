name := "auth"
version := "0.0.0"
scalaVersion := "2.11.6"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  "com.jason-goodwin" %% "authentikat-jwt" % "0.4.1"
)
