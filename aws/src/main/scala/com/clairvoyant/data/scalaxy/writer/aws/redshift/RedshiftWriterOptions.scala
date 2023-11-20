package com.clairvoyant.data.scalaxy.writer.aws.redshift

case class RedshiftWriterOptions(
    tempDirRegion: Option[String] = None,
    iamRoleARN: Option[String] = None,
    forwardSparkS3Credentials: Boolean = false,
    jdbcDriver: Option[String] = None,
    distStyle: String = "EVEN",
    distKey: Option[String] = None,
    sortKeySpec: Option[String] = None,
    includeColumnList: Boolean = false,
    description: Option[String] = None,
    preActions: List[String] = List.empty,
    postActions: List[String] = List.empty,
    extraCopyOptions: Option[String] = None,
    tempFormat: String = "AVRO",
    csvNullString: String = "@NULL@",
    autoPushDown: Boolean = true,
    autoPushDownS3ResultCache: Boolean = false,
    copyRetryCount: Int = 2,
    jdbcOptions: Map[String, String] = Map.empty
)
