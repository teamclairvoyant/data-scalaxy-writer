package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.writer.local.file.formats.FileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToFileWriter
import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToLocalFileSystemWriter {

  def write[T <: FileFormat](
      dataFrame: DataFrame,
      fileFormat: T,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  )(using dataFrameToFileWriter: DataFrameToFileWriter[T]): Unit =
    dataFrameToFileWriter.write(dataFrame, fileFormat, path, saveMode)

}
