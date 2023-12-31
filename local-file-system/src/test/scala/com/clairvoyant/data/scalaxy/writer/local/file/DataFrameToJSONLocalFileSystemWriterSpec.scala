package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.local.file.formats.JSONFileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToJSONFileWriter
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach

import java.io.File

class DataFrameToJSONLocalFileSystemWriterSpec extends DataFrameReader with DataFrameMatcher with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  val dataFrameToLocalFileSystemWriter = DataFrameToLocalFileSystemWriter[JSONFileFormat]

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val jsonFileFormat = JSONFileFormat()

    dataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = jsonFileFormat,
        path = outputDirPath
      )
    val actualDF = readJSONFromFile(outputDirPath)

    val expectedDF = df

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with ignoreNullFields" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": null,
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val jsonFileFormat = JSONFileFormat(
      ignoreNullFields = true
    )

    dataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = jsonFileFormat,
        path = outputDirPath
      )

    val actualDF = readJSONFromFile(outputDirPath)

    val expectedDF = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  override def afterEach(): Unit = FileUtils.deleteDirectory(new File(outputDirPath))

}
