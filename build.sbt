name := "from-oop-to-fp-through-a-practical-case"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.typelevel" %% "cats-effect" % "1.2.0",

  "org.scalikejdbc" %% "scalikejdbc"        % "3.3.+",
  "com.h2database"  %  "h2"                 % "1.4.+",
  "ch.qos.logback"  %  "logback-classic"    % "1.2.+"
)
