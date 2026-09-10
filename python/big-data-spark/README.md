# Big data with Spark (Python)

Coursework from the "Big Data Fundamentals" course at PUCPR (2021): analysing multi-gigabyte CSV files stored in HDFS on a Spark cluster, first with the low-level RDD API (map / filter / reduceByKey) and then with Spark SQL and DataFrames.

For a non-technical reader: these notebooks answer questions such as "which city had the most COVID-19 deaths" or "which stock had the largest one-day gain" over millions of rows, using a cluster of machines instead of a single computer.

| Folder | What it is |
| --- | --- |
| `spark-rdd-covid-analysis/` | 21 questions over 4.4 million COVID-19 test records (positives by sex, state and city, most common symptoms, cases per day and per week, death rate by age, busiest weekdays) answered with RDD transformations, including hand-built date-to-week and weekday mappings |
| `spark-sql-bovespa-analysis/` | 16 questions over 8.1 million daily quotes of the Brazilian stock exchange (trading days of PETR4, historical highs, largest gains and losses, yearly means and standard deviations of the most traded tickers per currency) answered with Spark SQL, DataFrame aggregations and a weekday UDF |
| `spark-exam-network-attacks/` | Individual exam: eight questions over 2 million network-flow records of DDoS attacks (busiest days, flows per label, top source IPs, most accessed services, duration classes) answered with RDDs |
| `written-activities/` | Written answers (Portuguese): Big Data scenarios for an energy / logistics / customer-service company, a MapReduce practice with Java solutions and screenshots, and an HDFS practice |

The notebooks keep the outputs of the cluster runs; re-running them needs a Spark cluster and the datasets (not included). The professor's slides and the HTML/PDF exports of the notebooks were removed.
