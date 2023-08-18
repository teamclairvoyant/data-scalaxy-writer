package com.clairvoyant.data.scalaxy.writer.file

import com.clairvoyant.data.scalaxy.test.util.DataScalaxyTestUtil
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach

import java.io.File

class DataFrameToJSONLocalFileSystemWriterSpec extends DataScalaxyTestUtil with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToJSONLocalFileSystemWriter()
      .write(df, outputDirPath)

    val actualDF = readJSONFromFile(outputDirPath)

    val expectedDF = df

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - ignoreNullFields" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": null,
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToJSONLocalFileSystemWriter(
      ignoreNullFields = true
    ).write(df, outputDirPath)

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
