package com.clairvoyant.data.scalaxy.writer.aws.s3.instances

import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.ParquetFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToParquetFileWriter extends DataFrameToS3FileWriter[ParquetFileFormat] {

  override def write(
      dataFrame: DataFrame,
      fileFormat: ParquetFileFormat,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit =
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
      .parquet(s"s3a://$bucketName/$path")

}
