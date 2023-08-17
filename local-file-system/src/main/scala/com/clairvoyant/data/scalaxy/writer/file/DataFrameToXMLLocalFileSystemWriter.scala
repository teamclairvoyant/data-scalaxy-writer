package com.clairvoyant.data.scalaxy.writer.file

import com.clairvoyant.data.scalaxy.writer.formats.XMLDataFrameWriter
import com.databricks.spark.xml.*
import org.apache.spark.sql.{DataFrame, SaveMode}

class DataFrameToXMLLocalFileSystemWriter(
    arrayElementName: String = "item",
    attributePrefix: String = "_",
    compression: String = "none",
    dateFormat: String = "yyyy-MM-dd",
    declaration: String = """version="1.0" encoding="UTF-8" standalone="yes"""",
    nullValue: String = "null",
    rootTag: String = "rows",
    rowTag: String = "row",
    timestampFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]",
    valueTag: String = "_VALUE"
) extends XMLDataFrameWriter(
      arrayElementName,
      attributePrefix,
      compression,
      dateFormat,
      declaration,
      nullValue,
      rootTag,
      rowTag,
      timestampFormat,
      valueTag
    ):

  override def write(df: DataFrame, path: String, saveMode: SaveMode): Unit =
    df.write
      .options(dataFrameToXMLWriterOptions)
      .mode(saveMode)
      .xml(path)
