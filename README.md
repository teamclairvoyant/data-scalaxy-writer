# data-scalaxy-writer

This library provides APIs to write a spark dataframe to various target source systems.

This library supports below target systems:

* Local File System
* AWS
* GCP


## local-file-system

User can use this library to write/persist spark dataframe to the local file system in various file formats.
Supported file formats are:

* CSV
* JSON
* XML
* Parquet

Please see the detailed documentation [here](local-file-system/README.md).

## aws

User can use this library to write/persist spark dataframe to various storage options provided by AWS.

Currently, we are supporting below options:

* S3 Buckets

Please see the detailed documentation [here](aws/README.md).

## gcp

User can use this library to write/persist spark dataframe to various storage options provided by google cloud system.

Currently, we are supporting below options:

* GCS
* BigQuery

Please see the detailed documentation [here](gcp/README.md).
