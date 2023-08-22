package com.clairvoyant.data.scalaxy.writer.gcp.gcs

import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.FileFormat
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances.DataFrameToGCSFileWriter
import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToGCSBucketWriter {

  def write[T <: FileFormat](
      dataFrame: DataFrame,
      fileFormat: T,
      bucketName: String,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  )(using dataFrameToGCSFileWriter: DataFrameToGCSFileWriter[T]): Unit =
    dataFrameToGCSFileWriter.write(dataFrame, fileFormat, bucketName, path, saveMode)

}
