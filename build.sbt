name := "Earth-207-tominaga"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.41",
  "org.mindrot" % "jbcrypt" % "0.4",
  filters
)

play.Project.playJavaSettings
