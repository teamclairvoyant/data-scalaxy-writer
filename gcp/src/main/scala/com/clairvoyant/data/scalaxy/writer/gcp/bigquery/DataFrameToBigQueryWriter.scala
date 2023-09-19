package com.clairvoyant.data.scalaxy.writer.gcp.bigquery

import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances.DataFrameToBQWriter
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.BigQueryWriterType
import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToBigQueryWriter {

  def write[T <: BigQueryWriterType](
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String] = None,
      project: Option[String] = None,
      parentProject: Option[String] = None,
      saveMode: SaveMode = SaveMode.Overwrite,
      writerType: T
  )(using dataFrameToBQWriter: DataFrameToBQWriter[T]): Unit =
    dataFrameToBQWriter.write(
      dataFrame,
      table,
      dataset,
      project,
      parentProject,
      saveMode,
      writerType
    )

}
