package com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances

import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToGCSFileWriter[T]:

  def write(
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit
