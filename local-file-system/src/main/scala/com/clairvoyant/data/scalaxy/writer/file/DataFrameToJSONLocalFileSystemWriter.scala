package com.clairvoyant.data.scalaxy.writer.file

import com.clairvoyant.data.scalaxy.writer.formats.JSONDataFrameWriter
import org.apache.spark.sql.{DataFrame, SaveMode}

class DataFrameToJSONLocalFileSystemWriter(
    compression: String = "none",
    dateFormat: String = "yyyy-MM-dd",
    encoding: String = "UTF-8",
    ignoreNullFields: Boolean = false,
    lineSep: String = "\n",
    timestampFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]",
    timestampNTZFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS]",
    timeZone: String = "UTC"
) extends JSONDataFrameWriter(
      compression,
      dateFormat,
      encoding,
      ignoreNullFields,
      lineSep,
      timestampFormat,
      timestampNTZFormat,
      timeZone
    ):

  override def write(df: DataFrame, path: String, saveMode: SaveMode): Unit =
    df.write
      .options(dataFrameToJSONWriterOptions)
      .mode(saveMode)
      .json(path)
