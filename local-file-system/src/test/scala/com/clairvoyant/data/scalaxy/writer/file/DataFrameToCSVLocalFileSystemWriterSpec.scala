package com.clairvoyant.data.scalaxy.writer.file

import com.clairvoyant.data.scalaxy.test.util.DataScalaxyTestUtil
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach

import java.io.File

class DataFrameToCSVLocalFileSystemWriterSpec extends DataScalaxyTestUtil with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter()
      .write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with emptyValue" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": ""
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      emptyValue = "NA"
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,NA""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with header as false" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      header = false
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(
      path = outputDirPath,
      csvOptions = Map("header" -> "false")
    )

    val expectedDF = readCSVFromText(
      text = "val_A1,val_B1,val_C1",
      csvOptions = Map("header" -> "false")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with ignoreLeadingWhiteSpace as false" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "    val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      ignoreLeadingWhiteSpace = false
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,    val_B1,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with ignoreLeadingWhiteSpace as true" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "    val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      ignoreLeadingWhiteSpace = true
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with ignoreTrailingWhiteSpace as false" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1    ",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      ignoreTrailingWhiteSpace = false
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1    ,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with ignoreTrailingWhiteSpace as true" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1    ",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      ignoreTrailingWhiteSpace = true
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,val_C1""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with lineSep" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      lineSep = "#"
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(
      path = outputDirPath,
      csvOptions = Map("lineSep" -> "#")
    )

    val expectedDF = readCSVFromText(
      text = "col_A,col_B,col_C#val_A1,val_B1,val_C1",
      csvOptions = Map("lineSep" -> "#")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with nullValue" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": null
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      nullValue = "Invalid"
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(outputDirPath)

    val expectedDF = readCSVFromText(
      """|col_A,col_B,col_C
         |val_A1,val_B1,Invalid""".stripMargin
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with sep" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    DataFrameToCSVLocalFileSystemWriter(
      sep = ";"
    ).write(df, outputDirPath)

    val actualDF = readCSVFromFile(
      path = outputDirPath,
      csvOptions = Map("sep" -> ";")
    )

    val expectedDF = readCSVFromText(
      text =
        """|col_A;col_B;col_C
           |val_A1;val_B1;val_C1""".stripMargin,
      csvOptions = Map("sep" -> ";")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  override def afterEach(): Unit = FileUtils.deleteDirectory(new File(outputDirPath))

}
