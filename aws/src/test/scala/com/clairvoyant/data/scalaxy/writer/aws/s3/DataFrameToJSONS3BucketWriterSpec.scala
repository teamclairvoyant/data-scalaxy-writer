package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.mock.S3BucketMock
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.JSONFileFormat
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToJSONFileWriter

class DataFrameToJSONS3BucketWriterSpec extends DataFrameReader with DataFrameMatcher with S3BucketMock {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided s3 path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val jsonFileFormat = JSONFileFormat()

    val bucketName = "test-bucket"

    s3Client.createBucket(bucketName)

    DataFrameToS3BucketWriter
      .write[JSONFileFormat](
        dataFrame = df,
        fileFormat = jsonFileFormat,
        bucketName = bucketName,
        path = outputDirPath
      )

    val actualDF = readJSONFromFile(s"s3a://$bucketName/$outputDirPath")

    val expectedDF = df

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
