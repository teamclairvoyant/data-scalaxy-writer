ThisBuild / scalaVersion := "3.3.0"

ThisBuild / organization := "com.clairvoyant.data.scalaxy"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  System.getenv("GITHUB_USERNAME"),
  System.getenv("GITHUB_TOKEN")
)

// ----- RESOLVERS ----- //

ThisBuild / resolvers ++= Seq(
  "DataScalaxyTestUtil Repo" at "https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-test-util/"
)

// ----- PUBLISH TO GITHUB PACKAGES ----- //

ThisBuild / publishTo := Some(
  "Github Repo" at s"https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-writer/"
)

// ----- SCALAFIX ----- //

ThisBuild / semanticdbEnabled := true
ThisBuild / scalafixOnCompile := true

// ----- WARTREMOVER ----- //

ThisBuild / wartremoverErrors ++= Warts.allBut(
  Wart.Any,
  Wart.DefaultArguments,
  Wart.FinalCaseClass,
  Wart.LeakingSealed,
  Wart.ToString
)

// ----- TOOL VERSIONS ----- //

val dataScalaxyTestUtilVersion = "1.0.0"
val sparkVersion = "3.4.1"
val sparkXMLVersion = "0.16.0"

// ----- TOOL DEPENDENCIES ----- //

val dataScalaxyTestUtilDependencies = Seq(
  "com.clairvoyant.data.scalaxy" %% "test-util" % dataScalaxyTestUtilVersion % Test
)

val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)
  .map(_ excludeAll ("org.scala-lang.modules", "scala-xml"))
  .map(_.cross(CrossVersion.for3Use2_13))

val sparkXMLDependencies = Seq(
  "com.databricks" %% "spark-xml" % sparkXMLVersion
).map(_.cross(CrossVersion.for3Use2_13))

// ----- MODULE DEPENDENCIES ----- //

val localFileSystemDependencies =
  dataScalaxyTestUtilDependencies ++
    sparkDependencies ++
    sparkXMLDependencies

// ----- PROJECTS ----- //

lazy val `data-scalaxy-writer` = (project in file("."))
  .settings(
    publish / skip := true,
    publishLocal / skip := true
  )
  .aggregate(`writer-local-file-system`)

lazy val `writer-local-file-system` = project
  .settings(
    version := "1.0.0",
    libraryDependencies ++= localFileSystemDependencies
  )
