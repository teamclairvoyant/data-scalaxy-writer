package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.local.file.formats.CSVFileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToCSVFileWriter
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach

import java.io.File

class DataFrameToCSVLocalFileSystemWriterSpec extends DataFrameReader with DataFrameMatcher with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val csvFileFormat = CSVFileFormat()

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      emptyValue = "NA"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      header = false
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      ignoreLeadingWhiteSpace = false
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      ignoreLeadingWhiteSpace = true
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      ignoreTrailingWhiteSpace = false
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      ignoreTrailingWhiteSpace = true
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      lineSep = "#"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      nullValue = "Invalid"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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

    val csvFileFormat = CSVFileFormat(
      sep = ";"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = csvFileFormat,
        path = outputDirPath
      )

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
