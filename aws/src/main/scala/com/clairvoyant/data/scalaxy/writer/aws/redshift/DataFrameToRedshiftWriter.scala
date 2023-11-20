package com.clairvoyant.data.scalaxy.writer.aws.redshift

import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToRedshiftWriter {

  def write(
      dataFrame: DataFrame,
      hostName: String,
      port: Int,
      databaseName: String,
      tableName: String,
      userName: String,
      password: String,
      tempDirS3Path: String,
      writerOptions: RedshiftWriterOptions,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit =
    val dataFrameWriterOptions =
      Map(
        "url" -> s"jdbc:redshift://$hostName:$port/$databaseName",
        "dbtable" -> tableName,
        "user" -> userName,
        "password" -> password,
        "tempdir" -> tempDirS3Path,
        "tempformat" -> writerOptions.tempFormat,
        "diststyle" -> writerOptions.distStyle,
        "preactions" -> writerOptions.preActions.mkString(";"),
        "postactions" -> writerOptions.postActions.mkString(";"),
        "csvnullstring" -> writerOptions.csvNullString
      ) ++
        Map(
          "tempdir_region" -> writerOptions.tempDirRegion,
          "aws_iam_role" -> writerOptions.iamRoleARN,
          "jdbcdriver" -> writerOptions.jdbcDriver,
          "distkey" -> writerOptions.distKey,
          "sortkeyspec" -> writerOptions.sortKeySpec,
          "description" -> writerOptions.description,
          "extracopyoptions" -> writerOptions.extraCopyOptions
        ).collect { case (key, Some(value)) =>
          key -> value
        } ++
        Map(
          "forward_spark_s3_credentials" -> writerOptions.forwardSparkS3Credentials,
          "include_column_list" -> writerOptions.includeColumnList,
          "autopushdown" -> writerOptions.autoPushDown,
          "autopushdown.s3_result_cache" -> writerOptions.autoPushDownS3ResultCache,
          "copyretrycount" -> writerOptions.copyRetryCount
        ).map((optionName, optionValue) => (optionName, optionValue.toString)) ++
        writerOptions.jdbcOptions

    dataFrame.write
      .format(source = "io.github.spark_redshift_community.spark.redshift")
      .options(dataFrameWriterOptions)
      .mode(saveMode)
      .save()

}
