val Http4sVersion = "0.23.25"
val CirceVersion = "0.14.6"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.4.14"
val MunitCatsEffectVersion = "1.0.7"
val PureConfigVersion = "0.17.6"

lazy val root = (project in file("."))
  .settings(
    organization := "com.jbb",
    name := "weather",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.3.0",
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"            %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"            %% "http4s-circe"        % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"          % Http4sVersion,
      "io.circe"              %% "circe-parser"        % CirceVersion,
      "io.circe"              %% "circe-generic"       % CirceVersion,
      "com.github.pureconfig" %% "pureconfig-core"     % PureConfigVersion,
      "org.scalameta"         %% "munit"               % MunitVersion           % Test,
      "org.scalatest"         %% "scalatest"           % "3.2.18"               % Test,
      "org.typelevel"         %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "ch.qos.logback"        %  "logback-classic"     % LogbackVersion,
    ),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
