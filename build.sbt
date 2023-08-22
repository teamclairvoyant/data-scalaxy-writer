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
val gcsConnectorVersion = "hadoop3-2.2.17"
val s3MockVersion = "0.2.6"
val scalaParserCombinatorsVersion = "2.3.0"
val sparkVersion = "3.4.1"
val sparkXMLVersion = "0.16.0"
val zioConfigVersion = "4.0.0-RC16"

// ----- TOOL DEPENDENCIES ----- //

val dataScalaxyTestUtilDependencies = Seq(
  "com.clairvoyant.data.scalaxy" %% "test-util" % dataScalaxyTestUtilVersion % Test
)

val gcsConnectorDependencies = Seq("com.google.cloud.bigdataoss" % "gcs-connector" % gcsConnectorVersion)

val s3MockDependencies = Seq(
  "io.findify" %% "s3mock" % s3MockVersion % Test
)
  .map(_.cross(CrossVersion.for3Use2_13))
  .map(_ excludeAll ("org.scala-lang.modules", "scala-xml"))

val scalaParserCombinatorsDependencies = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserCombinatorsVersion
)

val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)
  .map(_ excludeAll ("org.scala-lang.modules", "scala-xml"))
  .map(_.cross(CrossVersion.for3Use2_13))

val sparkHadoopCloudDependencies = Seq(
  "org.apache.spark" %% "spark-hadoop-cloud" % sparkVersion
).map(_.cross(CrossVersion.for3Use2_13))

val sparkXMLDependencies = Seq(
  "com.databricks" %% "spark-xml" % sparkXMLVersion
).map(_.cross(CrossVersion.for3Use2_13))

val zioConfigDependencies = Seq(
  "dev.zio" %% "zio-config-magnolia" % zioConfigVersion
).map(_ excludeAll ("org.scala-lang.modules", "scala-collection-compat"))

// ----- MODULE DEPENDENCIES ----- //

val localFileSystemDependencies =
  dataScalaxyTestUtilDependencies ++
    sparkDependencies ++
    sparkXMLDependencies ++
    zioConfigDependencies

val awsDependencies =
  dataScalaxyTestUtilDependencies ++
    s3MockDependencies ++
    scalaParserCombinatorsDependencies ++
    sparkDependencies ++
    sparkHadoopCloudDependencies ++
    sparkXMLDependencies ++
    zioConfigDependencies

val gcpDependencies =
  dataScalaxyTestUtilDependencies ++
    gcsConnectorDependencies ++
    sparkDependencies ++
    sparkXMLDependencies ++
    zioConfigDependencies

// ----- PROJECTS ----- //

lazy val `data-scalaxy-writer` = (project in file("."))
  .settings(
    publish / skip := true,
    publishLocal / skip := true
  )
  .aggregate(`writer-local-file-system`, `writer-aws`)

lazy val `writer-local-file-system` = (project in file("local-file-system"))
  .settings(
    version := "1.0.0",
    libraryDependencies ++= localFileSystemDependencies
  )

lazy val `writer-aws` = (project in file("aws"))
  .settings(
    version := "1.0.0",
    libraryDependencies ++= awsDependencies,
    Test / parallelExecution := false
  )

lazy val `writer-gcp` = (project in file("gcp"))
  .settings(
    version := "1.0.0",
    libraryDependencies ++= gcpDependencies,
    Test / parallelExecution := false
  )
