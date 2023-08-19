package com.clairvoyant.data.scalaxy.writer.local.file.formats

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
