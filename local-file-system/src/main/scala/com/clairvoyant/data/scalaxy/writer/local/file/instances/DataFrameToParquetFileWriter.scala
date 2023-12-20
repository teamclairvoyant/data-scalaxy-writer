package com.clairvoyant.data.scalaxy.writer.local.file.instances

import com.clairvoyant.data.scalaxy.writer.local.file.DataFrameToLocalFileSystemWriter
import com.clairvoyant.data.scalaxy.writer.local.file.formats.ParquetFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToParquetFileWriter extends DataFrameToLocalFileSystemWriter[ParquetFileFormat] {

  override def write(dataFrame: DataFrame, fileFormat: ParquetFileFormat, path: String, saveMode: SaveMode): Unit =
    dataFrame.write
      .options(
        Map(
          "datetimeRebaseMode" -> fileFormat.datetimeRebaseMode,
          "int96RebaseMode" -> fileFormat.int96RebaseMode,
          "mergeSchema" -> fileFormat.mergeSchema,
          "compression" -> fileFormat.compression
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      )
      .mode(saveMode)
      .parquet(path)

}
