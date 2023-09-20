package com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances

import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.IndirectBigQueryWriterType
import org.apache.spark.sql.{DataFrame, SaveMode}

implicit object DataFrameToIndirectBQWriter extends DataFrameToBQWriter[IndirectBigQueryWriterType] {

  override def write(
      dataFrame: DataFrame,
      table: String,
      dataset: Option[String],
      project: Option[String],
      parentProject: Option[String],
      saveMode: SaveMode,
      writerType: IndirectBigQueryWriterType
  ): Unit =
    writerType.bigQueryTableLabel
      .foldLeft(dataFrame.write) { (dfWriter, label) => dfWriter.option("bigQueryTableLabel", label) }
      .options(
        Map(
          "clusteredFields" -> writerType.clusteredFields,
          "dataset" -> dataset,
          "datePartition" -> writerType.datePartition,
          "project" -> project,
          "parentProject" -> parentProject,
          "destinationTableKmsKeyName" -> writerType.destinationTableKmsKeyName,
          "partitionExpirationMs" -> writerType.partitionExpirationMs,
          "partitionField" -> writerType.partitionField,
          "partitionType" -> writerType.partitionType,
          "persistentGcsBucket" -> writerType.persistentGcsBucket,
          "persistentGcsPath" -> writerType.persistentGcsPath,
          "proxyAddress" -> writerType.proxyAddress,
          "proxyUsername" -> writerType.proxyUsername,
          "proxyPassword" -> writerType.proxyPassword
        ).collect { case (key, Some(value)) =>
          key -> value.toString
        }
      )
      .options(
        Map(
          "allowFieldAddition" -> writerType.allowFieldAddition,
          "allowFieldRelaxation" -> writerType.allowFieldRelaxation,
          "createDisposition" -> writerType.createDisposition,
          "datetimeZoneId" -> writerType.datetimeZoneId,
          "enableListInference" -> writerType.enableListInference,
          "httpConnectTimeout" -> writerType.httpConnectTimeout,
          "httpMaxRetry" -> writerType.httpMaxRetry,
          "intermediateFormat" -> writerType.intermediateFormat,
          "temporaryGcsBucket" -> writerType.temporaryGcsBucket,
          "useAvroLogicalTypes" -> writerType.useAvroLogicalTypes,
          "writeMethod" -> "indirect"
        ).map((optionName, optionValue) => (optionName, optionValue.toString))
      )
      .format("bigquery")
      .mode(saveMode)
      .save(table)

}
