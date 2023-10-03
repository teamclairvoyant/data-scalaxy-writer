package com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances

import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToBQWriter[T]:

  def write(
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String],
      project: Option[String],
      parentProject: Option[String],
      saveMode: SaveMode = SaveMode.Overwrite,
      writerType: T
  ): Unit
