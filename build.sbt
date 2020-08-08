import ReleaseTransformations._

lazy val buildSettings = Seq(
  organization := "com.elega9t",
  organizationName := "Elega9t Ltd.",
  organizationHomepage := Some(new URL("https://elega9t.com")),
  publishArtifact in Test := false,
  sbtPlugin := true,
  scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
  scriptedBufferLog := false,
  scriptedLaunchOpts := {
    scriptedLaunchOpts.value ++ Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
  },
  scalaVersion := "2.12.10",
  crossSbtVersions := Vector("0.13.16"),
  releaseCrossBuild := true,
  releaseTagName := {
    (version in ThisBuild).value
  },
  parallelExecution := true,

  sonatypeProfileName := "com.elega9t",
  publishMavenStyle := true,
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/elega9t/sbt-liquibase-plugin")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/elega9t/sbt-liquibase-plugin"),
      "scm:git@github.com:elega9t/sbt-liquibase-plugin.git"
    )
  ),
  developers := List(
    Developer(id="ydubey_elega9t", name="Yogesh Dubey", email="yogesh@elega9t.com", url=url("https://www.elega9t.com"))
  ),
  publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
  ),
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommand("^publishSigned"),
    releaseStepCommand("sonatypeReleaseAll"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),

)

lazy val sbtLiquibase = Project(
  id = "sbt-liquibase",
  base = file(".")
)
.enablePlugins(ScriptedPlugin)
.settings(buildSettings)
.settings(
  libraryDependencies += "org.liquibase" % "liquibase-core" % "3.10.2"
)
