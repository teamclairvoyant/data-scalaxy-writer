package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.FileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToS3BucketWriter[T]:

  def write(
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit

object DataFrameToS3BucketWriter:

  def apply[T <: FileFormat](
      using dataFrameToS3BucketWriter: DataFrameToS3BucketWriter[T]
  ): DataFrameToS3BucketWriter[T] = dataFrameToS3BucketWriter
