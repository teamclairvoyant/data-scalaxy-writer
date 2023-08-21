package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.FileFormat
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToS3FileWriter
import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToS3BucketWriter {

  def write[T <: FileFormat](
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  )(using dataFrameToS3FileWriter: DataFrameToS3FileWriter[T]): Unit =
    dataFrameToS3FileWriter.write(dataFrame, fileFormat, bucketName, path, saveMode)

}
