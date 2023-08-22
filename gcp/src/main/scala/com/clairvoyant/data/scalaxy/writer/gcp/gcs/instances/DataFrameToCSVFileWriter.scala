package com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances

import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.CSVFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToCSVFileWriter extends DataFrameToGCSFileWriter[CSVFileFormat] {

  import org.apache.spark.sql.catalyst.csv.CSVOptions.*

  override def write(
      dataFrame: DataFrame,
      fileFormat: CSVFileFormat,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit =
    dataFrame.write
      .options(
        Map(
          CHAR_TO_ESCAPE_QUOTE_ESCAPING -> fileFormat.charToEscapeQuoteEscaping,
          COMPRESSION -> fileFormat.compression,
          DATE_FORMAT -> fileFormat.dateFormat,
          ENCODING -> fileFormat.encoding,
          EMPTY_VALUE -> fileFormat.emptyValue,
          ESCAPE -> fileFormat.escape,
          ESCAPE_QUOTES -> fileFormat.escapeQuotes,
          HEADER -> fileFormat.header,
          IGNORE_LEADING_WHITESPACE -> fileFormat.ignoreLeadingWhiteSpace,
          IGNORE_TRAILING_WHITESPACE -> fileFormat.ignoreTrailingWhiteSpace,
          LINE_SEP -> fileFormat.lineSep,
          NULL_VALUE -> fileFormat.nullValue,
          QUOTE -> fileFormat.quote,
          QUOTE_ALL -> fileFormat.quoteAll,
          SEP -> fileFormat.sep,
          TIMESTAMP_FORMAT -> fileFormat.timestampFormat,
          TIMESTAMP_NTZ_FORMAT -> fileFormat.timestampNTZFormat
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      )
      .mode(saveMode)
      .csv(s"gs://$bucketName/$path")

}
