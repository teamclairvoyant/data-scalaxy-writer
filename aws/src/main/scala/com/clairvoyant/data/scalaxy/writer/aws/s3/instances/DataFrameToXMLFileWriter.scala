package com.clairvoyant.data.scalaxy.writer.aws.s3.instances

import com.clairvoyant.data.scalaxy.writer.aws.s3.DataFrameToS3BucketWriter
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.XMLFileFormat
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToXMLFileWriter extends DataFrameToS3BucketWriter[XMLFileFormat] {

  import com.databricks.spark.xml.*

  override def write(
      dataFrame: DataFrame,
      fileFormat: XMLFileFormat,
      bucketName: String,
      path: String,
      saveMode: SaveMode
  ): Unit =
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
      .xml(s"s3a://$bucketName/$path")

}
