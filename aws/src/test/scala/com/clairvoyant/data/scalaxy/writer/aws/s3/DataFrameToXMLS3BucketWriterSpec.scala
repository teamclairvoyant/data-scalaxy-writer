package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.mock.S3BucketMock
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.XMLFileFormat
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToXMLFileWriter

class DataFrameToXMLS3BucketWriterSpec extends DataFrameReader with DataFrameMatcher with S3BucketMock {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided s3 path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat()

    val bucketName = "test-bucket"

    s3Client.createBucket(bucketName)

    DataFrameToS3BucketWriter
      .write[XMLFileFormat](
        dataFrame = df,
        fileFormat = xmlFileFormat,
        bucketName = bucketName,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(s"s3a://$bucketName/$outputDirPath")

    val expectedDF = readXMLFromText(
      """|<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
         |<rows>
         |    <row>
         |        <col_A>val_A1</col_A>
         |        <col_B>val_B1</col_B>
         |        <col_C>val_C1</col_C>
         |    </row>
         |</rows>""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
