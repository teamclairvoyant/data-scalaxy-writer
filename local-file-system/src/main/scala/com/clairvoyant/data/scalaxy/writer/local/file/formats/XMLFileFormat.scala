package com.clairvoyant.data.scalaxy.writer.local.file.formats

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
