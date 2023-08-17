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
  "DataScalaxyCommon Repo" at "https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-common/",
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
  Wart.ToString
)

// ----- TOOL VERSIONS ----- //

val dataScalaxyCommonVersion = "1.0.0"
val dataScalaxyTestUtilVersion = "1.0.0"

// ----- TOOL DEPENDENCIES ----- //

val dataScalaxyCommonDependencies = Seq(
  "com.clairvoyant.data.scalaxy" %% "common" % dataScalaxyCommonVersion
)

val dataScalaxyTestUtilDependencies = Seq(
  "com.clairvoyant.data.scalaxy" %% "test-util" % dataScalaxyTestUtilVersion
)

// ----- MODULE DEPENDENCIES ----- //

val localFileSystemDependencies = dataScalaxyCommonDependencies ++ dataScalaxyTestUtilDependencies

// ----- PROJECTS ----- //

lazy val `local-file-system` = project
  .settings(
    libraryDependencies ++= localFileSystemDependencies
  )
