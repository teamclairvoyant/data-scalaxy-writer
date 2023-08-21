# data-scalaxy-writer

This library provides APIs to write a spark dataframe to various target source systems.

This library supports below target systems:

* Local File System
* AWS


## local-file-system

User can use this library to write/persist spark dataframe to the local file system in various file formats.
Supported file formats are:

* CSV
* JSON
* XML
* Parquet

Please see the detailed documentation [here](local-file-system/README.md).

## aws

User can use this library to write/persist spark dataframe to s3 buckets in various file formats.
Supported file formats are:

* CSV
* JSON
* XML
* Parquet

Please see the detailed documentation [here](aws/README.md).