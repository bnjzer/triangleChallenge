lazy val commonSettings = Seq(
  scalaVersion := "2.10.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "triangleChallenge",
    version := "1.0.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
