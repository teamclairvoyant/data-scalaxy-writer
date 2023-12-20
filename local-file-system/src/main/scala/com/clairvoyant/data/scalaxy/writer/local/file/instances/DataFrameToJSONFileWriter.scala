package com.clairvoyant.data.scalaxy.writer.local.file.instances

import com.clairvoyant.data.scalaxy.writer.local.file.DataFrameToLocalFileSystemWriter
import com.clairvoyant.data.scalaxy.writer.local.file.formats.JSONFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToJSONFileWriter extends DataFrameToLocalFileSystemWriter[JSONFileFormat] {

  import org.apache.spark.sql.catalyst.json.JSONOptions.*

  override def write(dataFrame: DataFrame, fileFormat: JSONFileFormat, path: String, saveMode: SaveMode): Unit =
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
      .json(path)

}
