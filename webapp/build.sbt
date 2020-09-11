enablePlugins(ScalaJSPlugin)

name := "Scala.js Tutorial"
scalaVersion := "2.13.1" // or any other Scala version >= 2.11.12

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

// 操作dom的依赖
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0"

// 提供nodejs环境能够访问dom， 配合 project/plugins.sbt 中 "scalajs-env-jsdom-nodejs" 插件
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
// 添加uTest 支持测试
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")