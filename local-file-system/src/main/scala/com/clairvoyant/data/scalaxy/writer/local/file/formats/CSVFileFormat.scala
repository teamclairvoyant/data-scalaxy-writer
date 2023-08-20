package com.clairvoyant.data.scalaxy.writer.local.file.formats

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
