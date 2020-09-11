addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.2.0")

// 支持访问DOM功能，而Node.js中不提供该功能
libraryDependencies += "org.scala-js" %% "scalajs-env-jsdom-nodejs" % "1.0.0"