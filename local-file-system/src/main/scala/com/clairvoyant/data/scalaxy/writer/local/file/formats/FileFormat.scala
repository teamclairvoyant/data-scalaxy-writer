package com.clairvoyant.data.scalaxy.writer.local.file.formats

import zio.config.derivation.*

@nameWithLabel
sealed trait FileFormat

case class CSVFileFormat(
    charToEscapeQuoteEscaping: String = "\\",
    compression: String = "none",
    dateFormat: String = "yyyy-MM-dd",
    emptyValue: String = "",
    encoding: String = "UTF-8",
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
) extends FileFormat

case class JSONFileFormat(
    compression: String = "none",
    dateFormat: String = "yyyy-MM-dd",
    encoding: String = "UTF-8",
    ignoreNullFields: Boolean = false,
    lineSep: String = "\n",
    timestampFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]",
    timestampNTZFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS]",
    timeZone: String = "UTC"
) extends FileFormat

case class XMLFileFormat(
    arrayElementName: String = "item",
    attributePrefix: String = "_",
    compression: Option[String] = None,
    dateFormat: String = "yyyy-MM-dd",
    declaration: String = """version="1.0" encoding="UTF-8" standalone="yes"""",
    nullValue: String = "null",
    rootTag: String = "rows",
    rowTag: String = "row",
    timestampFormat: String = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]",
    valueTag: String = "_VALUE"
) extends FileFormat

case class ParquetFileFormat(
    datetimeRebaseMode: String = "EXCEPTION",
    int96RebaseMode: String = "EXCEPTION",
    mergeSchema: Boolean = false,
    compression: String = "snappy"
) extends FileFormat
