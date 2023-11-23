package com.clairvoyant.data.scalaxy.writer.aws.redshift

import com.clairvoyant.data.scalaxy.test.util.readers.DataFrameReader

class DataFrameToRedshiftWriterSpec extends DataFrameReader {

  "write()" should "write a dataframe to the provided redshift table" in {
    val df = readJSONFromText(
      """|{
         |  "col_A": "val_A1",
         |  "col_B": "val_B1",
         |  "col_C": "val_C1"
         |}""".stripMargin
    )

    val redshiftWriterOptions = RedshiftWriterOptions(
      tempDirRegion = Some("ca-central-1"),
      iamRoleARN = Some("arn:aws:iam::283220348991:role/service-role/AmazonRedshift-CommandsAccessRole-20231115T135908")
    )

    DataFrameToRedshiftWriter
      .write(
        dataFrame = df,
        hostName = "my-redshift-cluster.cjlkeer2jbwx.ca-central-1.redshift.amazonaws.com",
        databaseName = "dev",
        tableName = "my_redshift_table",
        userName = "admin",
        password = "RedshiftNov2023",
        tempDirS3Path = "s3a://my-tmp-redshift-bucket/redshift-tmp-dir/",
        writerOptions = redshiftWriterOptions
      )
  }

}
