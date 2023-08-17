package com.clairvoyant.data.scalaxy.writer.file

import com.clairvoyant.data.scalaxy.writer.formats.CSVDataFrameWriter
import org.apache.spark.sql.{DataFrame, SaveMode}

class DataFrameToCSVLocalFileSystemWriter(
    charToEscapeQuoteEscaping: String = "\\",
    compression: String = "none",
    dateFormat: String = "yyyy-MM-dd",
    encoding: String = "UTF-8",
    emptyValue: String = "",
    escape: String = "\\",
    escapeQuotes: Boolean = true,
    header: Boolean = true,
    ignoreLeadingWhiteSpace: Boolean = true,
    ignoreTrailingWhiteSpace: Boolean = true,
    lineSep: String = "\n",
    nullValue: String = "null",
    quote: String = "\"",
    quoteAll: Boolean = false,
    sep: String = ",",
    timestampFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]",
    timestampNTZFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS]"
) extends CSVDataFrameWriter(
      charToEscapeQuoteEscaping,
      compression,
      dateFormat,
      encoding,
      emptyValue,
      escape,
      escapeQuotes,
      header,
      ignoreLeadingWhiteSpace,
      ignoreTrailingWhiteSpace,
      lineSep,
      nullValue,
      quote,
      quoteAll,
      sep,
      timestampFormat,
      timestampNTZFormat
    ):

  override def write(df: DataFrame, path: String, saveMode: SaveMode): Unit =
    df.write
      .options(dataFrameToCSVWriterOptions)
      .mode(saveMode)
      .csv(path)
