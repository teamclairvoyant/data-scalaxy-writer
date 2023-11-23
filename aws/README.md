# aws

User need to add below dependency to the `build.sbt` file:

```sbt
ThisBuild / resolvers += "Github Repo" at "https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-writer/"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  System.getenv("GITHUB_USERNAME"),
  System.getenv("GITHUB_TOKEN")
)

ThisBuild / libraryDependencies += "com.clairvoyant.data.scalaxy" %% "writer-aws" % "1.0.0"
```

Make sure you add `GITHUB_USERNAME` and `GITHUB_TOKEN` to the environment variables.

`GITHUB_TOKEN` is the Personal Access Token with the permission to read packages.

## S3 BUCKET

User can use this library to write/persist spark dataframe to s3 buckets in various file formats.
Supported file formats are:

* CSV
* JSON
* XML
* Parquet

### CSV

Suppose user wants to write the dataframe `df` to s3 bucket `mybucket` under the path `outputPath` in the `csv` format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.CSVFileFormat

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
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToCSVFileWriter
``````

#### 3. Call API

```scala
DataFrameToS3BucketWriter
  .write(
    dataFrame = df,
    fileFormat = csvFileFormat,
    bucketName = mybucket,
    path = outputPath
  )
``````

### JSON

Suppose user wants to write the dataframe `df` to the s3 bucket `myBucket` under the path `outputPath` in the `json`
format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.JSONFileFormat

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
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToJSONFileWriter
``````

#### 3. Call API

```scala
DataFrameToS3BucketWriter
  .write(
    dataFrame = df,
    fileFormat = jsonFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

### XML

Suppose user wants to write the dataframe `df` to s3 bucket `myBucket` under the path `outputPath` in the `xml` format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.XMLFileFormat

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
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToXMLFileWriter
``````

#### 3. Call API

```scala
DataFrameToS3BucketWriter
  .write(
    dataFrame = df,
    fileFormat = xmlFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

### PARQUET

Suppose user wants to write the dataframe `df` to s3 bucket `myBucket` under the path `outputPath` in the `parquet`
format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.aws.s3.formats.ParquetFileFormat

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
import com.clairvoyant.data.scalaxy.writer.aws.s3.instances.DataFrameToParquetFileWriter
``````

#### 3. Call API

```scala
DataFrameToS3BucketWriter
  .write(
    dataFrame = df,
    fileFormat = parquetFileFormat,
    bucketName = myBucket,
    path = outputPath
  )
``````

## REDSHIFT

User can use this library to write/persist spark dataframe to redshift tables.

User need to perform below steps in order to write the dataframe to redshift table:

### 1. Import necessary classes

```scala
import com.clairvoyant.data.scalaxy.writer.aws.redshift.*
```

### 2. Call the write API

```scala
val redshiftWriterOptions = RedshiftWriterOptions(
  tempDirRegion = Some("ca-central-1"),
  iamRoleARN = Some("arn:aws:iam::283220348991:role/service-role/AmazonRedshift-CommandsAccessRole-20231115T135908")
)

DataFrameToRedshiftWriter
  .write(
    dataFrame = df,
    hostName = "my-host",
    port = 5439,
    databaseName = "dev",
    tableName = "my_redshift_table",
    userName = "testuser",
    password = "testpassword",
    tempDirS3Path = "s3a://my-tmp-redshift-bucket/redshift-tmp-dir/",
    writerOptions = redshiftWriterOptions
  )
```

User need to provide below arguments to the `write` API:

| Argument Name | Mandatory |                               Default Value                               | Description                                                     |
|:--------------|:---------:|:-------------------------------------------------------------------------:|:----------------------------------------------------------------|
| dataFrame     |    Yes    |                                     -                                     | Dataframe to write to redshift table.                           |
| hostName      |    Yes    |                                     -                                     | Redshift host name.                                             |
| port          |    No     |                                   5439                                    | Redshift port.                                                  |
| databaseName  |    No     |                                    dev                                    | Redshift database name.                                         |
| tableName     |    Yes    |                                     -                                     | Redshift table name.                                            |
| userName      |    Yes    |                                     -                                     | Redshift user name.                                             |
| password      |    Yes    |                                     -                                     | Redshift password.                                              |
| tempDirS3Path |    Yes    |                                     -                                     | S3 path to store temporary files.                               |
| writerOptions |    No     | Default instance of <br/>`RedshiftWriterOptions` <br/>with default values | Redshift writer options represented by `RedshiftWriterOptions`. |
| saveMode      |    No     |                                 overwrite                                 | Save mode to use while writing to redshift table.               |

User can pass below options to the `RedshiftWriterOptions` instance:

<table>
 <tr>
    <th>Parameter Name</th>
    <th>Mandatory</th>
    <th>Default value</th>
    <th>Description</th>
 </tr>
 <tr>
    <td>tempDirRegion</td>
    <td>No</td>
    <td>-</td>
    <td>
       <p>AWS region where tempdir is located. Setting this option will improve connector performance for interactions with tempdir as well as automatically supply this value as part of COPY and UNLOAD operations during connector writes and reads. If the region is not specified, the connector will attempt to use the <a href="https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html">Default Credential Provider Chain</a> for resolving where the tempdir region is located. In some cases, such as when the connector is being used outside of an AWS environment, this resolution will fail. Therefore, this setting is highly recommended in the following situations:</p>
       <ol>
          <li>When the connector is running outside of AWS as automatic region discovery will fail and negatively affect connector performance.</li>
          <li>When tempdir is in a different region than the Redshift cluster as using this setting alleviates the need to supply the region manually using the extracopyoptions and extraunloadoptions parameters.</li>
          <li>When the connector is running in a different region than tempdir as it improves the connector's access performance of tempdir.</li>
       </ol>
    </td>
 </tr>
 <tr>
   <td>iamRoleARN</td>
   <td>Only if using IAM roles to authorize Redshift COPY/UNLOAD operations</td>
   <td>-</td>
   <td>Fully specified ARN of the <a href="http://docs.aws.amazon.com/redshift/latest/mgmt/copy-unload-iam-role.html">IAM Role</a> attached to the Redshift cluster, ex: arn:aws:iam::123456789000:role/redshift_iam_role</td>
 </tr>
 <tr>
    <td>forwardSparkS3Credentials</td>
    <td>No</td>
    <td>false</td>
    <td>
        If true then this library will automatically discover the credentials that Spark is
        using to connect to S3 and will forward those credentials to Redshift over JDBC.
        These credentials are sent as part of the JDBC query, so it is strongly
        recommended to enable SSL encryption of the JDBC connection when using this option.
    </td>
 </tr>
 <tr>
    <td>jdbcDriver</td>
    <td>No</td>
    <td>Determined by the JDBC URL's subprotocol</td>
    <td>The class name of the JDBC driver to use. This class must be on the classpath. In most cases, it should not be necessary to specify this option, as the appropriate driver classname should automatically be determined by the JDBC URL's subprotocol.</td>
 </tr>
 <tr>
    <td>distStyle</td>
    <td>No</td>
    <td>EVEN</td>
    <td>The Redshift <a href="http://docs.aws.amazon.com/redshift/latest/dg/c_choosing_dist_sort.html">Distribution Style</a> to
    be used when creating a table. Can be one of EVEN, KEY or ALL (see Redshift docs). When using KEY, you
    must also set a distribution key with the distkey option.
    </td>
 </tr>
 <tr>
    <td>distKey</td>
    <td>No, unless using DISTSTYLE KEY</td>
    <td>-</td>
    <td>The name of a column in the table to use as the distribution key when creating a table.</td>
 </tr>
 <tr>
    <td>sortKeySpec</td>
    <td>No</td>
    <td>-</td>
    <td>
        <p>A full Redshift <a href="http://docs.aws.amazon.com/redshift/latest/dg/t_Sorting_data.html">Sort Key</a> definition.</p>
        <p>Examples include:</p>
        <ul>
            <li>SORTKEY(my_sort_column)</li>
            <li>COMPOUND SORTKEY(sort_col_1, sort_col_2)</li>
            <li>INTERLEAVED SORTKEY(sort_col_1, sort_col_2)</li>
        </ul>
    </td>
 </tr>
 <tr>
    <td>includeColumnList</td>
    <td>No</td>
    <td>false</td>
    <td>
        If true then this library will automatically extract the columns from the schema
        and add them to the COPY command according to the <a href="http://docs.aws.amazon.com/redshift/latest/dg/copy-parameters-column-mapping.html">Column List docs</a>.
        (e.g. `COPY "PUBLIC"."tablename" ("column1" [,"column2", ...])`).
    </td>
</tr>
 <tr>
    <td>description</td>
    <td>No</td>
    <td>-</td>
    <td>
    <p>A description for the table. Will be set using the SQL COMMENT command, and should show up in most query tools.
    See also the description metadata to set descriptions on individual columns.
 </tr>
 <tr>
    <td>preActions</td>
    <td>No</td>
    <td>-</td>
    <td>
        <p>This can be a list of SQL commands to be executed before loading COPY command.
        It may be useful to have some DELETE commands or similar run here before loading new data. If the command contains
        %s, the table name will be formatted in before execution (in case you're using a staging table).</p>
        <p>Be warned that if this commands fail, it is treated as an error, and you'll get an exception. If using a staging
        table, the changes will be reverted and the backup table restored if pre actions fail.</p>
    </td>
 </tr>
 <tr>
    <td>postActions</td>
    <td>No</td>
    <td>No default</td>
    <td>
        <p>This can be a list of SQL commands to be executed after a successful COPY when loading data.
        It may be useful to have some GRANT commands or similar run here when loading new data. If the command contains
        %s, the table name will be formatted in before execution (in case you're using a staging table).</p>
        <p>Be warned that if this commands fail, it is treated as an error and you'll get an exception. If using a staging
        table, the changes will be reverted and the backup table restored if post actions fail.</p>
    </td>
 </tr>
 <tr>
    <td>extraCopyOptions</td>
    <td>No</td>
    <td>No default</td>
    <td>
        <p>A list extra options to append to the Redshift COPY command when loading data, e.g. TRUNCATECOLUMNS
        or MAXERROR n (see the <a href="http://docs.aws.amazon.com/redshift/latest/dg/r_COPY.html#r_COPY-syntax-overview-optional-parameters">Redshift docs</a>
        for other options).</p>
        <p>Note that since these options are appended to the end of the COPY command, only options that make sense
        at the end of the command can be used, but that should cover most possible use cases.</p>
    </td>
 </tr>
 <tr>
    <td>tempFormat</td>
    <td>No</td>
    <td>AVRO</td>
    <td>
        <p>
            The format in which to save temporary files in S3 when writing to Redshift.
            Defaults to "AVRO"; the other allowed values are "CSV", "CSV GZIP", and "PARQUET" for CSV,
            gzipped CSV, and parquet, respectively.
        </p>
        <p>
            Redshift is significantly faster when loading CSV than when loading Avro files, so
            using that tempformat may provide a large performance boost when writing
            to Redshift.
        </p>
        <p>
            Parquet should not be used as the tempformat when using an S3 bucket (tempdir) in a region that is different
            from the region where the redshift cluster you are writing to resides. This is because cross-region copies are
            not supported in redshift when using parquet as the format.
        </p>
    </td>
 </tr>
 <tr>
    <td>csvNullString</td>
    <td>No</td>
    <td>@NULL@</td>
    <td>
    <p>
        The String value to write for nulls when using the CSV tempformat.
        This should be a value which does not appear in your actual data.
    </p>
    </td>
 </tr>
 <tr>
    <td>autoPushDown</td>
    <td>No</td>
    <td>True</td>
    <td>
        <p>
            Apply predicate and query pushdown by capturing and analyzing the Spark logical plans for SQL operations. 
            The operations are translated into a SQL query and then executed in Redshift to improve performance.
        </p>
        <p>Once autopushdown is enabled, it is enabled for all the Redshift tables in the same Spark session.</p>
    </td>
 </tr>
 <tr>
    <td>autoPushDownS3ResultCache</td>
    <td>No</td>
    <td>False</td>
    <td>Cache the query SQL to unload data S3 path mapping in memory so that the same query don't need to execute again in the same Spark session.</td>
 </tr>
 <tr>
    <td>copyRetryCount</td>
    <td>No</td>
    <td>2</td>
    <td>Number of times to retry a copy operation including dropping and creating any required table before failing.</td>
 </tr>
 <tr>
    <td>jdbcOptions</td>
    <td>No</td>
    <td>-</td>
    <td>
        Additional map of parameters to pass to the underlying JDBC driver where the wildcard is the name of the JDBC parameter (e.g., jdbc.ssl). Note that the jdbc prefix will be stripped off before passing to the JDBC driver.
        A complete list of possible options for the Redshift JDBC driver may be seen in the <a href="https://docs.aws.amazon.com/redshift/latest/mgmt/jdbc20-configuration-options.html">Redshift docs</a>.
    </td>
</tr>
</table>