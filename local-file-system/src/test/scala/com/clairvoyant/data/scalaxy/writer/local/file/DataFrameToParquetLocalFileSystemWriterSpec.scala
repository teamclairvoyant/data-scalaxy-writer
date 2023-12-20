package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.local.file.formats.ParquetFileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToParquetFileWriter
import org.scalatest.BeforeAndAfterEach

class DataFrameToParquetLocalFileSystemWriterSpec
    extends DataFrameReader
    with DataFrameMatcher
    with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  val dataFrameToLocalFileSystemWriter = DataFrameToLocalFileSystemWriter[ParquetFileFormat]

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val parquetFileFormat = ParquetFileFormat()

    dataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = parquetFileFormat,
        path = outputDirPath
      )

    val actualDF = readParquet(outputDirPath)

    val expectedDF = df

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
