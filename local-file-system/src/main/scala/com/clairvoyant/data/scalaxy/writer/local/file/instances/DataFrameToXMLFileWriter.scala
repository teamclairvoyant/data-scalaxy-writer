package com.clairvoyant.data.scalaxy.writer.local.file.instances

import com.clairvoyant.data.scalaxy.writer.local.file.formats.XMLFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToXMLFileWriter extends DataFrameToFileWriter[XMLFileFormat] {

  import com.databricks.spark.xml.*

  override def write(dataFrame: DataFrame, fileFormat: XMLFileFormat, path: String, saveMode: SaveMode): Unit =
    dataFrame.write
      .options(
        Map(
          "arrayElementName" -> fileFormat.arrayElementName,
          "attributePrefix" -> fileFormat.attributePrefix,
          "compression" -> fileFormat.compression,
          "dateFormat" -> fileFormat.dateFormat,
          "declaration" -> fileFormat.declaration,
          "nullValue" -> fileFormat.nullValue,
          "rootTag" -> fileFormat.rootTag,
          "rowTag" -> fileFormat.rowTag,
          "timestampFormat" -> fileFormat.timestampFormat,
          "valueTag" -> fileFormat.valueTag
        )
      )
      .mode(saveMode)
      .xml(path)

}
