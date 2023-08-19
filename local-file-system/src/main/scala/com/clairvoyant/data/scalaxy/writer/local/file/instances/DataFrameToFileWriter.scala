package com.clairvoyant.data.scalaxy.writer.local.file.instances

import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToFileWriter[T]:

  def write(dataFrame: DataFrame, fileFormat: T, path: String, saveMode: SaveMode): Unit
