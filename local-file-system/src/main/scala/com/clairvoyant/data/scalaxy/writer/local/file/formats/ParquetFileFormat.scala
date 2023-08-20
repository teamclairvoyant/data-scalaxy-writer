package com.clairvoyant.data.scalaxy.writer.local.file.formats

case class ParquetFileFormat(
    datetimeRebaseMode: String = "EXCEPTION",
    int96RebaseMode: String = "EXCEPTION",
    mergeSchema: Boolean = false,
    compression: String = "snappy"
) extends FileFormat
