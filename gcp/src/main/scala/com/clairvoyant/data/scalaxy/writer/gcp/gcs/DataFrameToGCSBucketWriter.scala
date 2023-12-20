package com.clairvoyant.data.scalaxy.writer.gcp.gcs

import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.FileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToGCSBucketWriter[T]:

  def write(
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit

object DataFrameToGCSBucketWriter:

  def apply[T <: FileFormat](
      using dataFrameToGCSBucketWriter: DataFrameToGCSBucketWriter[T]
  ): DataFrameToGCSBucketWriter[T] = dataFrameToGCSBucketWriter
