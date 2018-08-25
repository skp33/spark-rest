package org.apache.spark.rest

import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import org.glassfish.jersey.server.ServerProperties
import org.glassfish.jersey.servlet.ServletContainer

/**
 * Created by KPrajapati on 8/25/2018.
 */
object RestAPI {
  def getServletHandler: ServletContextHandler = {
    val jerseyContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS)
    jerseyContext.setContextPath("/rest")

    val holder: ServletHolder = new ServletHolder(classOf[ServletContainer])
    holder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "org.apache.spark.rest.services")

    jerseyContext.addServlet(holder, "/*")
    jerseyContext
  }
}
