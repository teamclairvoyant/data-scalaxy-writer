package com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances

import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.JSONFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToJSONFileWriter extends DataFrameToGCSFileWriter[JSONFileFormat] {

  import org.apache.spark.sql.catalyst.json.JSONOptions.*

  override def write(
      dataFrame: DataFrame,
      fileFormat: JSONFileFormat,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit =
    dataFrame.write
      .options(
        Map(
          COMPRESSION -> fileFormat.compression,
          DATE_FORMAT -> fileFormat.dateFormat,
          ENCODING -> fileFormat.encoding,
          IGNORE_NULL_FIELDS -> fileFormat.ignoreNullFields,
          LINE_SEP -> fileFormat.lineSep,
          TIMESTAMP_FORMAT -> fileFormat.timestampFormat,
          TIMESTAMP_NTZ_FORMAT -> fileFormat.timestampNTZFormat,
          TIME_ZONE -> fileFormat.timeZone
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      )
      .mode(saveMode)
      .json(s"gs://$bucketName/$path")

}
