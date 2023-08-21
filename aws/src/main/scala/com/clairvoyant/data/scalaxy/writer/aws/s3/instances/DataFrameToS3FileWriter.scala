package com.clairvoyant.data.scalaxy.writer.aws.s3.instances

import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToS3FileWriter[T]:

  def write(
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit
