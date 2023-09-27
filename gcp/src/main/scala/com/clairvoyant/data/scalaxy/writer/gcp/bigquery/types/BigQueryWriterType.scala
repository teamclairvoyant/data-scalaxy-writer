package com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types

import zio.config.derivation.*

@nameWithLabel
sealed trait BigQueryWriterType

case class DirectBigQueryWriterType(
    bigQueryTableLabel: List[String] = List.empty,
    clusteredFields: Option[String] = None,
    createDisposition: String = "CREATE_IF_NEEDED",
    datetimeZoneId: String = "UTC",
    destinationTableKmsKeyName: Option[String] = None,
    enableListInference: Boolean = false,
    enableModeCheckForSchemaFields: Boolean = true,
    httpConnectTimeout: Long = 60000L,
    httpMaxRetry: Int = 10,
    proxyAddress: Option[String] = None,
    proxyUsername: Option[String] = None,
    proxyPassword: Option[String] = None,
    queryJobPriority: String = "INTERACTIVE",
    writeAtLeastOnce: Boolean = false
) extends BigQueryWriterType

case class IndirectBigQueryWriterType(
    allowFieldAddition: Boolean = false,
    allowFieldRelaxation: Boolean = false,
    bigQueryTableLabel: List[String] = List.empty,
    clusteredFields: Option[String] = None,
    createDisposition: String = "CREATE_IF_NEEDED",
    datetimeZoneId: String = "UTC",
    datePartition: Option[String] = None,
    destinationTableKmsKeyName: Option[String] = None,
    enableListInference: Boolean = false,
    httpConnectTimeout: Long = 60000L,
    httpMaxRetry: Int = 10,
    intermediateFormat: String = "parquet",
    partitionExpirationMs: Option[Long] = None,
    partitionField: Option[String] = None,
    partitionType: Option[String] = None,
    persistentGcsBucket: Option[String] = None,
    persistentGcsPath: Option[String] = None,
    proxyAddress: Option[String] = None,
    proxyUsername: Option[String] = None,
    proxyPassword: Option[String] = None,
    temporaryGcsBucket: String,
    useAvroLogicalTypes: Boolean = false
) extends BigQueryWriterType
