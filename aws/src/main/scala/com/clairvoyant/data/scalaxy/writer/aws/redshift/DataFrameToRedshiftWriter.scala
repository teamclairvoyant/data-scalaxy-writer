package com.clairvoyant.data.scalaxy.writer.aws.redshift

import org.apache.spark.sql.{DataFrame, SaveMode}

object DataFrameToRedshiftWriter {

  def write(
      dataFrame: DataFrame,
      hostName: String,
      port: Int,
      databaseName: String,
      tableName: String,
      tempS3Path: String,
      iamRoleARN: String,
      saveMode: SaveMode = SaveMode.Overwrite
  ): Unit =
    dataFrame.write
      .format(source = "io.github.spark_redshift_community.spark.redshift")
      .options {
        Map(
          "url" -> s"jdbc:redshift:iam//$hostName:$port/$databaseName",
          "dbtable" -> tableName,
          "tempdir" -> tempS3Path,
          "aws_iam_role" -> iamRoleARN
        )
      }
      .mode(saveMode)
      .save()

}
