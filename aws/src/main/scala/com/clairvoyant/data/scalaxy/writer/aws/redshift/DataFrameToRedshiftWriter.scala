package com.clairvoyant.data.scalaxy.writer.aws.redshift

import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToRedshiftWriter {

  def write(
      dataFrame: DataFrame,
      hostName: String,
      port: Int,
      databaseName: String,
      tableName: String,
      tempDirS3Path: String,
      tempDirRegion: String,
      iamRoleARN: String,
      userName: String,
      password: String,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit =
    dataFrame.write
      .format(source = "io.github.spark_redshift_community.spark.redshift")
      .options {
        Map(
          "url" -> s"jdbc:redshift://$hostName:$port/$databaseName",
          "user" -> userName,
          "password" -> password,
          "dbtable" -> tableName,
          "tempdir" -> tempDirS3Path,
          "tempdir_region" -> tempDirRegion,
          "aws_iam_role" -> iamRoleARN
        )
      }
      .mode(saveMode)
      .save()

}
