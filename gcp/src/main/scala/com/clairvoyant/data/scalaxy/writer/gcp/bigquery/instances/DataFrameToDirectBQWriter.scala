package com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances

import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.DataFrameToBigQueryWriter
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.DirectBigQueryWriterType
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToDirectBQWriter extends DataFrameToBigQueryWriter[DirectBigQueryWriterType] {

  override def write(
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String],
      project: Option[String],
      parentProject: Option[String],
      saveMode: SaveMode,
      writerType: DirectBigQueryWriterType
  ): Unit =
    writerType.bigQueryTableLabel
      .foldLeft(dataFrame.write) { (dfWriter, label) => dfWriter.option("bigQueryTableLabel", label) }
      .options(
        Map(
          "clusteredFields" -> writerType.clusteredFields,
          "dataset" -> dataset,
          "destinationTableKmsKeyName" -> writerType.destinationTableKmsKeyName,
          "project" -> project,
          "parentProject" -> parentProject,
          "proxyAddress" -> writerType.proxyAddress,
          "proxyUsername" -> writerType.proxyUsername,
          "proxyPassword" -> writerType.proxyPassword
        ).collect { case (key, Some(value)) =>
          key -> value
        }
      )
      .options(
        Map(
          "createDisposition" -> writerType.createDisposition,
          "datetimeZoneId" -> writerType.datetimeZoneId,
          "enableListInference" -> writerType.enableListInference,
          "enableModeCheckForSchemaFields" -> writerType.enableModeCheckForSchemaFields,
          "httpConnectTimeout" -> writerType.httpConnectTimeout,
          "httpMaxRetry" -> writerType.httpMaxRetry,
          "queryJobPriority" -> writerType.queryJobPriority,
          "writeAtLeastOnce" -> writerType.writeAtLeastOnce,
          "writeMethod" -> "direct"
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      )
      .format("bigquery")
      .mode(saveMode)
      .save(table)

}
