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

    DataFrameToRedshiftWriter
      .write(
        dataFrame = df,
        hostName = "my-redshift-cluster.cjlkeer2jbwx.ca-central-1.redshift.amazonaws.com",
        port = 5439,
        databaseName = "dev",
        tableName = "my_redshift_table",
        tempDirS3Path = "s3a://my-tmp-redshift-bucket/redshift-tmp-dir/",
        tempDirRegion = "ca-central-1",
        iamRoleARN = "arn:aws:iam::283220348991:role/service-role/AmazonRedshift-CommandsAccessRole-20231115T135908",
        userName = "admin",
        password = "RedshiftNov2023"
      )
  }

}
