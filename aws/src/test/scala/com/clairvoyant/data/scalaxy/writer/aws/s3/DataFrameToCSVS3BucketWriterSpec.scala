package com.clairvoyant.data.scalaxy.writer.aws.s3

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.mock.S3BucketMock
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.CSVFileFormat
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToCSVFileWriter

class DataFrameToCSVS3BucketWriterSpec extends DataFrameReader with DataFrameMatcher with S3BucketMock {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided s3 path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val csvFileFormat = CSVFileFormat()

    val bucketName = "test-bucket"

    s3Client.createBucket(bucketName)

    DataFrameToS3BucketWriter
      .write[CSVFileFormat](
        dataFrame = df,
        fileFormat = csvFileFormat,
        bucketName = bucketName,
        path = outputDirPath
      )

    val actualDF = readCSVFromFile(s"s3a://$bucketName/$outputDirPath")

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
