package org.apache.spark.rest

import org.apache.spark.{SparkContext, SparkException}
import org.apache.spark.internal.Logging
import org.apache.spark.ui.SparkUI
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import org.glassfish.jersey.server.ServerProperties
import org.glassfish.jersey.servlet.ServletContainer

/**
 * Created by KPrajapati on 8/25/2018.
 */
class RestAPI {
  def getServletHandler: ServletContextHandler = {
    val jerseyContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS)
    jerseyContext.setContextPath("/rest")

    val holder: ServletHolder = new ServletHolder(classOf[ServletContainer])
    holder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "org.apache.spark.rest.services")

    jerseyContext.addServlet(holder, "/*")
    jerseyContext
  }
}

object RestAPI extends Logging {
  def getSparkUI(sparkContext: SparkContext): SparkUI = {
    sparkContext.ui.getOrElse {
      throw new SparkException("Parent SparkUI to attach this tab to not found!")
    }
  }

  def apply(sparkContext: SparkContext): Unit = attach(sparkContext)

  def attach(sparkContext: SparkContext): Unit = {
    getSparkUI(sparkContext).attachHandler(new RestAPI().getServletHandler)
    logInfo("Started rest-server")
  }

  def detach(sparkContext: SparkContext): Unit = {
    getSparkUI(sparkContext).detachHandler(new RestAPI().getServletHandler)
    logInfo("Stopped rest-server")
  }
}
