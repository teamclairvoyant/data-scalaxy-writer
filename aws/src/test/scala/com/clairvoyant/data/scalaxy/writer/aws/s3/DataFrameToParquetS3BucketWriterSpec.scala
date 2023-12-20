package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.mock.S3BucketMock
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.ParquetFileFormat
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToParquetFileWriter

class DataFrameToParquetS3BucketWriterSpec extends DataFrameReader with DataFrameMatcher with S3BucketMock {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  val dataFrameToS3BucketWriter = DataFrameToS3BucketWriter[ParquetFileFormat]

  "write()" should "write a dataframe to the provided s3 path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val parquetFileFormat = ParquetFileFormat()

    val bucketName = "test-bucket"

    s3Client.createBucket(bucketName)

    dataFrameToS3BucketWriter
      .write(
        dataFrame = df,
        fileFormat = parquetFileFormat,
        bucketName = bucketName,
        path = outputDirPath
      )

    val actualDF = readParquet(s"s3a://$bucketName/$outputDirPath")

    val expectedDF = df

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
