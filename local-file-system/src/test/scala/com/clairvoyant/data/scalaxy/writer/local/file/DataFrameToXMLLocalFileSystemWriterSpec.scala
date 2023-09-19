package com.clairvoyant.data.scalaxy.writer.local.file

import com.clairvoyant.data.scalaxy.test.util.matchers.DataFrameMatcher
import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader
import com.clairvoyant.data.scalaxy.writer.local.file.formats.XMLFileFormat
import com.clairvoyant.data.scalaxy.writer.local.file.instances.DataFrameToXMLFileWriter
import org.scalatest.BeforeAndAfterEach

class DataFrameToXMLLocalFileSystemWriterSpec extends DataFrameReader with DataFrameMatcher with BeforeAndAfterEach {

  val outputDirPath = s"/tmp/out_${System.currentTimeMillis()}"

  "write()" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat()

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(outputDirPath)

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

  "write() - with attributePrefix" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "attr_type": "x",
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat(
      attributePrefix = "attr_"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(
      path = outputDirPath,
      xmlOptions = Map("attributePrefix" -> "attr_")
    )

    val expectedDF = readXMLFromText(
      text =
        """|<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
           |<rows>
           |    <row type="x">
           |        <col_A>val_A1</col_A>
           |        <col_B>val_B1</col_B>
           |        <col_C>val_C1</col_C>
           |    </row>
           |</rows>""".stripMargin,
      xmlOptions = Map("attributePrefix" -> "attr_")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with declaration" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat(
      declaration = "custom_declaration"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(outputDirPath)

    val expectedDF = readXMLFromText(
      """|<?xml custom_declaration?>
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

  "write() - with rowTag" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat(
      rowTag = "item"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(
      path = outputDirPath,
      xmlOptions = Map("rowTag" -> "item")
    )

    val expectedDF = readXMLFromText(
      text =
        """|<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
           |<rows>
           |    <item>
           |        <col_A>val_A1</col_A>
           |        <col_B>val_B1</col_B>
           |        <col_C>val_C1</col_C>
           |    </item>
           |</rows>""".stripMargin,
      xmlOptions = Map("rowTag" -> "item")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with rootTag" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat(
      rootTag = "items"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(
      path = outputDirPath,
      xmlOptions = Map("rootTag" -> "items")
    )

    val expectedDF = readXMLFromText(
      text =
        """|<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
           |<items>
           |    <row>
           |        <col_A>val_A1</col_A>
           |        <col_B>val_B1</col_B>
           |        <col_C>val_C1</col_C>
           |    </row>
           |</items>""".stripMargin,
      xmlOptions = Map("rootTag" -> "items")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

  "write() - with valueTag" should "write a dataframe to the provided path" in {
    val df = readJSONFromText(
      """|{
         |  "_id": "bk102",
         |  "author": "Ralls, Kim",
         |  "price": {
         |    "_unit": "$",
         |    "#VALUE": 5.95
         |  },
         |  "title": "Midnight Rain"
         |}
         |""".stripMargin
    )

    val xmlFileFormat = XMLFileFormat(
      rowTag = "book",
      valueTag = "#VALUE"
    )

    DataFrameToLocalFileSystemWriter
      .write(
        dataFrame = df,
        fileFormat = xmlFileFormat,
        path = outputDirPath
      )

    val actualDF = readXMLFromFile(
      path = outputDirPath,
      xmlOptions = Map("rowTag" -> "book", "valueTag" -> "#VALUE")
    )

    val expectedDF = readXMLFromText(
      text =
        """|<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
           |<catalog>
           |   <book id="bk102">
           |      <author>Ralls, Kim</author>
           |      <title>Midnight Rain</title>
           |      <price unit="$">5.95</price>
           |   </book>
           |</catalog>""".stripMargin,
      xmlOptions = Map("rowTag" -> "book", "valueTag" -> "#VALUE")
    )

    actualDF should matchExpectedDataFrame(expectedDF)
  }

}
