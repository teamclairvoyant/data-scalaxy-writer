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
| :------------------------ | :-------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
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

Suppose user wants to write the dataframe `df` to the gcs bucket `myBucket` under the path `outputPath` in the `json` format.
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
| :----------------- | :-------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------------ |
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
| :--------------- | :---------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
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

Suppose user wants to write the dataframe `df` to gcs bucket `myBucket` under the path `outputPath` in the `parquet` format.
Then user need to perform below steps:

#### 1. Define file format

```scala
import com.clairvoyant.data.scalaxy.writer.gcp.gcs.formats.ParquetFileFormat

val parquetFileFormat = ParquetFileFormat()
```

User can provide below options to the `ParquetFileFormat` instance:

| Parameter Name     | Default Value | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| :----------------- | :-----------: | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
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