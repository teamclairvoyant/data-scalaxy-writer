package com.clairvoyant.data.scalaxy.writer.local.file.instances

import com.clairvoyant.data.scalaxy.writer.local.file.DataFrameToLocalFileSystemWriter
import com.clairvoyant.data.scalaxy.writer.local.file.formats.XMLFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToXMLFileWriter extends DataFrameToLocalFileSystemWriter[XMLFileFormat] {

  import com.databricks.spark.xml.*

  override def write(dataFrame: DataFrame, fileFormat: XMLFileFormat, path: String, saveMode: SaveMode): Unit =
    val xmlOptions = Map(
      "arrayElementName" -> fileFormat.arrayElementName,
      "attributePrefix" -> fileFormat.attributePrefix,
      "dateFormat" -> fileFormat.dateFormat,
      "declaration" -> fileFormat.declaration,
      "nullValue" -> fileFormat.nullValue,
      "rootTag" -> fileFormat.rootTag,
      "rowTag" -> fileFormat.rowTag,
      "timestampFormat" -> fileFormat.timestampFormat,
      "valueTag" -> fileFormat.valueTag
    )

    dataFrame.write
      .options(
        fileFormat.compression
          .map(compressionCodec => xmlOptions + ("compression" -> compressionCodec))
          .getOrElse(xmlOptions)
      )
      .mode(saveMode)
      .xml(path)

}
