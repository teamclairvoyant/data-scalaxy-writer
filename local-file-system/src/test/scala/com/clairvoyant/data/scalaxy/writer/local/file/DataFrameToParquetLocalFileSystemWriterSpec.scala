package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.test.util.DataScalaxyTestUtil
import com.clairvoyant.data.scalaxy.writer.local.file.formats.ParquetFileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToParquetFileWriter
import org.scalatest.BeforeAndAfterEach

class DataFrameToParquetLocalFileSystemWriterSpec extends DataScalaxyTestUtil with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val parquetFileFormat = ParquetFileFormat()

    DataFrameToLocalFileSystemWriter
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
