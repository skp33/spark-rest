package org.apache.spark.rest

import org.apache.spark.{SparkContext, SparkException}
import org.apache.spark.internal.Logging
import org.apache.spark.rest.RestServer._
import org.apache.spark.ui.{SparkUI, SparkUITab}

/**
 * Created by KPrajapati on 8/25/2018.
 */
class RestServer(sparkContext: SparkContext)
  extends SparkUITab(getSparkUI(sparkContext), "rest-server")
    with Logging {

  getSparkUI(sparkContext).attachHandler(RestAPI.getServletHandler)

  def detach() {
    getSparkUI(sparkContext).detachTab(this)
  }
}

object RestServer {
  def getSparkUI(sparkContext: SparkContext): SparkUI = {
    sparkContext.ui.getOrElse {
      throw new SparkException("Parent SparkUI to attach this tab to not found!")
    }
  }
}
