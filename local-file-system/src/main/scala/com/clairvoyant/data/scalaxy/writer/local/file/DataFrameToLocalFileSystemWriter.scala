package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.writer.local.file.formats.FileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

trait DataFrameToLocalFileSystemWriter[T]:

  def write(
      dataFrame: DataFrame,
      fileFormat: T,
      path: String,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit

object DataFrameToLocalFileSystemWriter:

  def apply[T <: FileFormat](
      using dataFrameToLocalFileSystemWriter: DataFrameToLocalFileSystemWriter[T]
  ): DataFrameToLocalFileSystemWriter[T] = dataFrameToLocalFileSystemWriter
