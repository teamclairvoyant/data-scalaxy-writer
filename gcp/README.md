# gcp

User need to add below dependency to the `build.sbt` file:

```sbt
ThisBuild / resolvers += "Github Repo" at "https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-writer/"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  System.getenv("GITHUB_USERNAME"),
  System.getenv("GITHUB_TOKEN")
)

ThisBuild / libraryDependencies += "com.clairvoyant.data.scalaxy" %% "writer-gcp" % "1.0.0"
```

Make sure you add `GITHUB_USERNAME` and `GITHUB_TOKEN` to the environment variables.

`GITHUB_TOKEN` is the Personal Access Token with the permission to read packages.

## GCS Bucket

User can use this library to write/persist spark dataframe to gcs buckets in various file formats.
Supported file formats are:

* CSV
* JSON
* XML
* Parquet

### CSV

Suppose user wants to write the dataframe `df` to gcs bucket `mybucket` under the path `outputPath` in the `csv` format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.CSVFileFormat

val csvFileFormat = CSVFileFormat(
  header = false
)
```

User can provide below options to the `CSVFileFormat` instance:

| Parameter Name            |        Default Value        | Description                                                                                                                                                                        |
|:--------------------------|:---------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| charToEscapeQuoteEscaping |              \              | Sets a single character used for escaping the escape for the quote character.                                                                                                      |
| compression               |            none             | Compression codec to use when saving to file. This can be one of the known case-insensitive shorten names (none, bzip2, gzip, lz4, snappy and deflate).                            |
| dateFormat                |         yyyy-MM-dd          | Sets the string that indicates a date format.                                                                                                                                      |
| emptyValue                |      "" (empty string)      | Sets the string representation of an empty value.                                                                                                                                  |
| encoding                  |            UTF-8            | Specifies encoding (charset) of saved CSV files.                                                                                                                                   |
| escape                    |              \              | Sets a single character used for escaping quotes inside an already quoted value.                                                                                                   |
| escapeQuotes              |            true             | A flag indicating whether values containing quotes should always be enclosed in quotes. Default is to escape all values containing a quote character.                              |
| header                    |            true             | Boolean flag to tell whether csv text contains header names or not.                                                                                                                |
| ignoreLeadingWhiteSpace   |            false            | A flag indicating whether or not leading whitespaces from values being written should be skipped.                                                                                  |
| ignoreTrailingWhiteSpace  |            false            | A flag indicating whether or not trailing whitespaces from values being written should be skipped.                                                                                 |
| lineSep                   |             \n              | Defines the line separator that should be used for writing. Maximum length is 1 character.                                                                                         |
| nullValue                 |            null             | Sets the string representation of a null value.                                                                                                                                    |
| quote                     |              "              | Sets a single character used for escaping quoted values where the separator can be part of the value. <br/>For writing, if an empty string is set, it uses u0000 (null character). |
| quoteAll                  |            false            | A flag indicating whether all values should always be enclosed in quotes. Default is to only escape values containing a quote character.                                           |
| sep                       |              ,              | Delimiter by which fields in a row are separated in a csv text.                                                                                                                    |
| timestampFormat           |     yyyy-MM-dd HH:mm:ss     | Sets the string that indicates a timestamp format.                                                                                                                                 |
| timestampNTZFormat        | yyyy-MM-dd'T'HH:mm:ss[.SSS] | Sets the string that indicates a timestamp without timezone format.                                                                                                                |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances.DataFrameToCSVFileWriter
``````

#### 3. Call API

```scala
DataFrameToGCSBucketWriter
  .write(
    dataFrame = df,
    fileFormat = csvFileFormat,
    bucketName = mybucket,
    path = outputPath
  )
``````

### JSON

Suppose user wants to write the dataframe `df` to the gcs bucket `myBucket` under the path `outputPath` in the `json`
format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.JSONFileFormat

val jsonFileFormat = JSONFileFormat(
  ignoreNullFields = true
)
```

User can provide below options to the `JSONFileFormat` instance:

| Parameter Name     |        Default Value        | Description                                                                                                                                             |
|:-------------------|:---------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------|
| compression        |            none             | Compression codec to use when saving to file. This can be one of the known case-insensitive shorten names (none, bzip2, gzip, lz4, snappy and deflate). |
| dateFormat         |         yyyy-MM-dd          | Sets the string that indicates a date format.                                                                                                           |
| encoding           |            UTF-8            | Specifies encoding (charset) of saved CSV files.                                                                                                        |
| ignoreNullFields   |            false            | Whether to ignore null fields when generating JSON objects.                                                                                             |
| lineSep            |             \n              | Defines the line separator that should be used for writing. Maximum length is 1 character.                                                              |
| timestampFormat    |     yyyy-MM-dd HH:mm:ss     | Sets the string that indicates a timestamp format.                                                                                                      |
| timestampNTZFormat | yyyy-MM-dd'T'HH:mm:ss[.SSS] | Sets the string that indicates a timestamp without timezone format.                                                                                     |
| timezone           |             UTC             | Sets the string that indicates a time zone ID to be used to format timestamps in the JSON datasources or partition values.                              |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances.DataFrameToJSONFileWriter
``````

#### 3. Call API

```scala
DataFrameToGCSBucketWriter
  .write(
    dataFrame = df,
    fileFormat = jsonFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

### XML

Suppose user wants to write the dataframe `df` to gcs bucket `myBucket` under the path `outputPath` in the `xml` format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.XMLFileFormat

val xmlFileFormat = XMLFileFormat(
  attributePrefix = "attr_"
)
```

User can provide below options to the `XMLFileFormat` instance:

| Parameter Name   |                  Default Value                  | Description                                                                                                                                                                                                                                                                                          |
|:-----------------|:-----------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| arrayElementName |                      item                       | Name of XML element that encloses each element of an array-valued column when writing.                                                                                                                                                                                                               |
| attributePrefix  |                        _                        | The prefix for attributes so that we can differentiating attributes and elements. This will be the prefix for field names.                                                                                                                                                                           |
| compression      |                      None                       | Compression codec to use when saving to file. <br/>Should be the fully qualified name of a class implementing org.apache.hadoop.io.compress.CompressionCodec or one of case-insensitive shorten names (bzip2, gzip, lz4, and snappy). <br/>Defaults to no compression when a codec is not specified. |
| dateFormat       |                   yyyy-MM-dd                    | Controls the format used to write DateType format columns.                                                                                                                                                                                                                                           |
| declaration      | version="1.0" encoding="UTF-8" standalone="yes" | Content of XML declaration to write at the start of every output XML file, before the rootTag. Set to empty string to suppress.                                                                                                                                                                      |
| nullValue        |                      null                       | The value to write null value. Default is string null. When this is null, it does not write attributes and elements for fields.                                                                                                                                                                      |
| rootTag          |                      rows                       | The root tag of your xml files to treat as the root. It can include basic attributes by specifying a value like books foo="bar".                                                                                                                                                                     |
| rowTag           |                       row                       | The row tag of your xml files to treat as a row.                                                                                                                                                                                                                                                     |
| timestampFormat  |        yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]         | Controls the format used to write TimestampType format columns.                                                                                                                                                                                                                                      |
| valueTag         |                     _VALUE                      | The tag used for the value when there are attributes in the element having no child.                                                                                                                                                                                                                 |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances.DataFrameToXMLFileWriter
``````

#### 3. Call API

```scala
DataFrameToGCSBucketWriter
  .write(
    dataFrame = df,
    fileFormat = xmlFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

### PARQUET

Suppose user wants to write the dataframe `df` to gcs bucket `myBucket` under the path `outputPath` in the `parquet`
format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.ParquetFileFormat

val parquetFileFormat = ParquetFileFormat()
```

User can provide below options to the `ParquetFileFormat` instance:

| Parameter Name     | Default Value | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
|:-------------------|:-------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| datetimeRebaseMode |   EXCEPTION   | The datetimeRebaseMode option allows to specify the rebasing mode for the values of the DATE, TIMESTAMP_MILLIS, TIMESTAMP_MICROS logical types from the Julian to Proleptic Gregorian calendar. <br/> Currently supported modes are: <br/> EXCEPTION: fails in reads of ancient dates/timestamps that are ambiguous between the two calendars. <br/> CORRECTED: loads dates/timestamps without rebasing. <br/> LEGACY: performs rebasing of ancient dates/timestamps from the Julian to Proleptic Gregorian calendar. |
| int96RebaseMode    |   EXCEPTION   | The int96RebaseMode option allows to specify the rebasing mode for INT96 timestamps from the Julian to Proleptic Gregorian calendar. Currently supported modes are: <br/> EXCEPTION: fails in reads of ancient INT96 timestamps that are ambiguous between the two calendars. <br/> CORRECTED: loads INT96 timestamps without rebasing. <br/> LEGACY: performs rebasing of ancient timestamps from the Julian to Proleptic Gregorian calendar.                                                                        |
| mergeSchema        |     false     | Sets whether we should merge schemas collected from all Parquet part-files.                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| compression        |    snappy     | Compression codec to use when saving to file. This can be one of the known case-insensitive shorten names (none, uncompressed, snappy, gzip, lzo, brotli, lz4, and zstd).                                                                                                                                                                                                                                                                                                                                             |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.instances.DataFrameToParquetFileWriter
``````

#### 3. Call API

```scala
DataFrameToGCSBucketWriter
  .write(
    dataFrame = df,
    fileFormat = parquetFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

## BigQuery

User can use this library to write/persist spark dataframe to google cloud BigQuery table.

There are two ways to write the dataframe to BigQuery table:

* Direct Write
* Indirect Write

You can read about the difference between these two
approaches [here](https://github.com/GoogleCloudDataproc/spark-bigquery-connector#writing-data-to-bigquery).

### Direct Write

Suppose user wants to write the dataframe `df` to the bigQuery table named `myBQTable` present under the
dataset `myBQDataset`.
Then user need to perform below steps:

#### 1. Define BigQuery writer type

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.DirectBigQueryWriterType

val bigQueryWriterType = DirectBigQueryWriterType(
  createDisposition = "CREATE_IF_NEEDED"
)
``````

Apart from `createDisposition`, user can pass below parameters to the `DirectBigQueryWriterType` instance:

| Parameter Name                 |  Default Value   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
|:-------------------------------|:----------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| bigQueryTableLabel             |    Empty List    | Can be used to add labels to the table while writing to a table. Multiple labels can be set.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| clusteredFields                |       None       | A string of non-repeated, top level columns seperated by comma.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| createDisposition              | CREATE_IF_NEEDED | Specifies whether the job is allowed to create new tables. The permitted values are:<br/>CREATE_IF_NEEDED - Configures the job to create the table if it does not exist<br/>CREATE_NEVER - Configures the job to fail if the table does not exist <br/>This option takes place only in case Spark has decided to write data to the table based on the SaveMode.                                                                                                                                                                                                                                            |
| datetimeZoneId                 |       UTC        | The time zone ID used to convert BigQuery's DATETIME into Spark's Timestamp, and vice versa.<br/>The value should be a legal time zone name, that appears is accepted by Java's java.time.ZoneId.                                                                                                                                                                                                                                                                                                                                                                                                          |
| destinationTableKmsKeyName     |       None       | Describes the Cloud KMS encryption key that will be used to protect destination BigQuery table. The BigQuery Service Account associated with your project requires access to this encryption key. for further Information about using CMEK with BigQuery see [here](https://cloud.google.com/bigquery/docs/customer-managed-encryption#key_resource_id).<br/>The table will be encrypted by the key only if it created by the connector. A pre-existing unencrypted table won't be encrypted just by setting this option.                                                                                  |
| enableListInference            |      false       | Indicates whether to use schema inference specifically when the mode is Parquet.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| enableModeCheckForSchemaFields |       true       | Checks the mode of every field in destination schema to be equal to the mode in corresponding source field schema, during DIRECT write.                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| httpConnectTimeout             |       6000       | The timeout in milliseconds to establish a connection with BigQuery. Can be alternatively set in the Spark configuration (spark.conf.set("httpConnectTimeout", ...)) or in Hadoop Configuration (fs.gs.http.connect-timeout).                                                                                                                                                                                                                                                                                                                                                                              |
| httpMaxRetry                   |        10        | The maximum number of retries for the low-level HTTP requests to BigQuery. Can be alternatively set in the Spark configuration (spark.conf.set("httpMaxRetry", ...)) or in Hadoop Configuration (fs.gs.http.max.retry).                                                                                                                                                                                                                                                                                                                                                                                    |
| proxyAddress                   |       None       | Address of the proxy server. The proxy must be a HTTP proxy and address should be in the `host:port` format. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.address).<br/>(Optional. Required only if connecting to GCP via proxy.)                                                                                                                                                                                                                                                                                                     |
| proxyUsername                  |       None       | The userName used to connect to the proxy. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.username).                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| proxyPassword                  |       None       | The password used to connect to the proxy. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.password).                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| queryJobPriority               |   INTERACTIVE    | Priority levels set for the job while reading data from BigQuery query. The permitted values are:<br/>BATCH - Query is queued and started as soon as idle resources are available, usually within a few minutes. If the query hasn't started within 3 hours, its priority is changed to INTERACTIVE.<br/>INTERACTIVE - Query is executed as soon as possible and count towards the concurrent rate limit and the daily rate limit.<br/>For WRITE, this option will be effective when DIRECT write is used with OVERWRITE mode, where the connector overwrites the destination table using MERGE statement. |
| writeAtLeastOnce               |      false       | Guarantees that data is written to BigQuery at least once. This is a lesser guarantee than exactly once. This is suitable for streaming scenarios in which data is continuously being written in small batches.<br/>Supported only by the `DIRECT` write method and mode is NOT `Overwrite`.                                                                                                                                                                                                                                                                                                               |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances.DataFrameToDirectBQWriter
``````

#### 3. Call API

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.DataFrameToBigQueryWriter

DataFrameToBigQueryWriter
  .write[DirectBigQueryWriterType](
    dataFrame = df,
    table = myBQTable,
    dataset = myBQDataset,
    writerType = bigQueryWriterType
  )
``````

### Direct Write

Suppose user wants to write the dataframe `df` to the bigQuery table named `myBQTable` present under the
dataset `myBQDataset`.
Then user need to perform below steps:

#### 1. Define BigQuery writer type

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.types.IndirectBigQueryWriterType

val bigQueryWriterType = IndirectBigQueryWriterType(
  createDisposition = "CREATE_IF_NEEDED"
)
``````

Apart from `createDisposition`, user can pass below parameters to the `DirectBigQueryWriterType` instance:

| Parameter Name             |  Default Value   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|:---------------------------|:----------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| allowFieldAddition         |      false       | Adds the ALLOW_FIELD_ADDITION SchemaUpdateOption to the BigQuery LoadJob. Allowed values are true and false.                                                                                                                                                                                                                                                                                                                                                                                                              |
| allowFieldRelaxation       |      false       | Adds the ALLOW_FIELD_RELAXATION SchemaUpdateOption to the BigQuery LoadJob. Allowed values are true and false.                                                                                                                                                                                                                                                                                                                                                                                                            |
| bigQueryTableLabel         |    Empty List    | Can be used to add labels to the table while writing to a table. Multiple labels can be set.                                                                                                                                                                                                                                                                                                                                                                                                                              |
| clusteredFields            |       None       | A string of non-repeated, top level columns seperated by comma.                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| createDisposition          | CREATE_IF_NEEDED | Specifies whether the job is allowed to create new tables. The permitted values are:<br/>CREATE_IF_NEEDED - Configures the job to create the table if it does not exist<br/>CREATE_NEVER - Configures the job to fail if the table does not exist <br/>This option takes place only in case Spark has decided to write data to the table based on the SaveMode.                                                                                                                                                           |
| datetimeZoneId             |       UTC        | The time zone ID used to convert BigQuery's DATETIME into Spark's Timestamp, and vice versa.<br/>The value should be a legal time zone name, that appears is accepted by Java's java.time.ZoneId.                                                                                                                                                                                                                                                                                                                         |
| datePartition              |       None       | The date partition the data is going to be written to. Should be a date string given in the format YYYYMMDD. Can be used to overwrite the data of a single partition.<br/>Can also be used with different partition types like:<br/>HOUR: YYYYMMDDHH<br/>MONTH: YYYYMM<br/>YEAR: YYYY                                                                                                                                                                                                                                     |
| destinationTableKmsKeyName |       None       | Describes the Cloud KMS encryption key that will be used to protect destination BigQuery table. The BigQuery Service Account associated with your project requires access to this encryption key. for further Information about using CMEK with BigQuery see [here](https://cloud.google.com/bigquery/docs/customer-managed-encryption#key_resource_id).<br/>The table will be encrypted by the key only if it created by the connector. A pre-existing unencrypted table won't be encrypted just by setting this option. |
| enableListInference        |      false       | Indicates whether to use schema inference specifically when the mode is Parquet.                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| httpConnectTimeout         |       6000       | The timeout in milliseconds to establish a connection with BigQuery. Can be alternatively set in the Spark configuration (spark.conf.set("httpConnectTimeout", ...)) or in Hadoop Configuration (fs.gs.http.connect-timeout).                                                                                                                                                                                                                                                                                             |
| httpMaxRetry               |        10        | The maximum number of retries for the low-level HTTP requests to BigQuery. Can be alternatively set in the Spark configuration (spark.conf.set("httpMaxRetry", ...)) or in Hadoop Configuration (fs.gs.http.max.retry).                                                                                                                                                                                                                                                                                                   |
| intermediateFormat         |     parquet      | The format of the data before it is loaded to BigQuery, values can be either "parquet","orc" or "avro". In order to use the Avro format, the spark-avro package must be added in runtime.                                                                                                                                                                                                                                                                                                                                 |
| partitionExpirationMs      |       None       | Number of milliseconds for which to keep the storage for partitions in the table. The storage in a partition will have an expiration time of its partition time plus this value.                                                                                                                                                                                                                                                                                                                                          |
| partitionField             |       None       | If field is specified together with `partitionType`, the table is partitioned by this field. The field must be a top-level TIMESTAMP or DATE field. Its mode must be NULLABLE or REQUIRED. If the option is not set for a partitioned table, then the table will be partitioned by pseudo column, referenced via either'_PARTITIONTIME' as TIMESTAMP type, or '_PARTITIONDATE' as DATE type.                                                                                                                              |
| partitionType              |       None       | Supported types are: HOUR, DAY, MONTH, YEAR. This option is mandatory for a target table to be partitioned. (Optional. Defaults to DAY if PartitionField is specified).                                                                                                                                                                                                                                                                                                                                                   |
| persistentGcsBucket        |       None       | The GCS bucket that holds the data before it is loaded to BigQuery. If informed, the data won't be deleted after write data into BigQuery.                                                                                                                                                                                                                                                                                                                                                                                |
| persistentGcsPath          |       None       | The GCS path that holds the data before it is loaded to BigQuery. Used only with persistentGcsBucket.                                                                                                                                                                                                                                                                                                                                                                                                                     |
| proxyAddress               |       None       | Address of the proxy server. The proxy must be a HTTP proxy and address should be in the `host:port` format. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.address).<br/>(Optional. Required only if connecting to GCP via proxy.)                                                                                                                                                                                                                    |
| proxyUsername              |       None       | The userName used to connect to the proxy. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.username).                                                                                                                                                                                                                                                                                                                                                   |
| proxyPassword              |       None       | The password used to connect to the proxy. Can be alternatively set in the Spark configuration (spark.conf.set(...)) or in Hadoop Configuration (fs.gs.proxy.password).                                                                                                                                                                                                                                                                                                                                                   |
| temporaryGcsBucket         |       None       | The GCS bucket that temporarily holds the data before it is loaded to BigQuery. Required unless set in the Spark configuration (spark.conf.set(...)).                                                                                                                                                                                                                                                                                                                                                                     |
| useAvroLogicalTypes        |      false       | When loading from Avro (`.option("intermediateFormat", "avro")`), BigQuery uses the underlying Avro types instead of the logical types [by default].  Supplying this option converts Avro logical types to their corresponding BigQuery data types.                                                                                                                                                                                                                                                                       |

#### 2. Import type class instance

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.instances.DataFrameToIndirectBQWriter
``````

#### 3. Call API

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.bigquery.DataFrameToBigQueryWriter

DataFrameToBigQueryWriter
  .write[IndirectBigQueryWriterType](
    dataFrame = df,
    table = myBQTable,
    dataset = myBQDataset,
    writerType = bigQueryWriterType
  )
``````

User can provide below parameters to the `write` method:

| Parameter Name | Mandatory | Default Value | Description                                                                                                                                 |
|:---------------|:---------:|:-------------:|:--------------------------------------------------------------------------------------------------------------------------------------------|
| dataFrame      |    Yes    |     None      | Spark dataframe to be written to BigQuery table.                                                                                            |
| table          |    Yes    |     None      | The name of big query table where dataframe needs to be persisted.                                                                          |
| dataset        |    No     |     None      | The dataset containing the table. If you are providing fully qualified name in `table` parameter, then you can ignore this option.          |
| project        |    No     |     None      | The Google Cloud Project ID of the table.<br/>(Optional. Defaults to the project of the Service Account being used)                         |
| parentProject  |    No     |     None      | The Google Cloud Project ID of the table to bill for the export.<br/>(Optional. Defaults to the project of the Service Account being used). |
| saveMode       |    No     |   Overwrite   | Mode of writing; default is overwrite; can be avoided if writeDisposition/ createDisposition has been defined                               |
| writerType     |    Yes    |     None      | The instance of direct or indirect big query writer type.                                                                                   |

Also, note that for writing to the BigQuery it is necessary to have below privileges to the user:

| Role Name                   | Purpose                          |
|:----------------------------|:---------------------------------|
| roles/bigquery.dataEditor   | Access BigQuery Tables           |
| roles/bigquery.jobUser      | Create and query BigQuery tables |
| roles/storage.objectViewer  | To list and read GCS contents    |
| roles/storage.objectCreator | To create folders in GCS         |
