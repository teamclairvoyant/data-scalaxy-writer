package com.clairvoyant.data.scalaxy.writer.gcp.bigquery

import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToBigQueryWriter {

  def write(
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String],
      temporaryGcsBucket: Option[String],
      createDisposition: Option[String],
      writeDisposition: Option[String],
      partitionField: Option[String],
      clusteredFields: Option[String],
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit = {

    val tableOptionToUse =
      dataset match {
        case Some(dataset) =>
          s"$dataset.$table"
        case None =>
          table
      }

    val fixedOptions = Map(
      "table" -> tableOptionToUse
    )

    val optionalOptions = Seq(
      "dataset" -> dataset,
      "temporaryGcsBucket" -> temporaryGcsBucket,
      "createDisposition" -> createDisposition,
      "writeDisposition" -> writeDisposition,
      "partitionField" -> partitionField,
      "clusteredFields" -> clusteredFields,
    ).collect { case (key, Some(value)) => key -> value }

    val optionsMap = fixedOptions ++ optionalOptions

    dataFrame.write
      .mode(saveMode)
      .format("bigquery")
      .options(optionsMap)
      .save()
  }
}
