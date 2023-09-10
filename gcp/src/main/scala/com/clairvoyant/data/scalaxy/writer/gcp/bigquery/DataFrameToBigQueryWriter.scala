package com.clairvoyant.data.scalaxy.writer.gcp.bigquery

import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToBigQueryWriter {

  def write(
      dataFrame: DataFrame,
      table: String,
      temporaryGcsBucket: Option[String],
      createDisposition: Option[String],
      writeDisposition: Option[String],
      partitionField: Option[String],
      clusteredFields: Option[String],
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit =

    dataFrame.write
      .mode(saveMode)
      .format("bigquery")
      .options(
        Map(
          "table" -> table,
          "temporaryGcsBucket" -> temporaryGcsBucket,
          "createDisposition" -> createDisposition,
          "writeDisposition" -> writeDisposition,
          "partitionField" -> partitionField,
          "clusteredFields" -> clusteredFields
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      ).save()

}
