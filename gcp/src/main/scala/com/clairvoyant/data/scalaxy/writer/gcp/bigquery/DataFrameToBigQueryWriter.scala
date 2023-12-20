package com.clairvoyant.data.scalaxy.writer.gcp.bigquery

import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.BigQueryWriterType
import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToBigQueryWriter[T]:

  def write(
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String] = None,
      project: Option[String] = None,
      parentProject: Option[String] = None,
      saveMode: SaveMode = SaveMode.Overwrite,
      writerType: T
  ): Unit

object DataFrameToBigQueryWriter:

  def apply[T <: BigQueryWriterType](
      using dataFrameToBigQueryWriter: DataFrameToBigQueryWriter[T]
  ): DataFrameToBigQueryWriter[T] = dataFrameToBigQueryWriter
