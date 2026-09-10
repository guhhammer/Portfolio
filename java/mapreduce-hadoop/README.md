# MapReduce on Hadoop (Java)

Fifteen MapReduce jobs from the Big Data course at PUCPR (2021), run on a Hadoop cluster over the UN commodity-trade dataset (a `;`-separated CSV: country, year, commodity code, commodity, flow, trade value in USD, weight in kg, ...). Each job is one class with a mapper, a reducer and a driver `main`; the `outputs/` folder keeps the `part-r-00000` result each job produced on the cluster.

| # | Job | Question it answers |
| --- | --- | --- |
| 01 | `TransactionsPerCountry` | Which country has the most trade transactions? |
| 02 | `BrazilTransactionsPerCommodity` | How many Brazilian transactions per commodity? |
| 03 | `TransactionsPerYear` | How many transactions per year? |
| 04 | `TransactionsPerCommodity` | How many transactions per commodity? |
| 05 | `TransactionsPerCommodityIn2016` | ... per commodity, in 2016 only |
| 06 | `BrazilTransactionsPerCommodityIn2016` | ... per commodity, Brazil in 2016 |
| 07 | `TotalWeightPerCommodity` | Total traded weight per commodity |
| 08 | `TotalWeightPerCommodityAndYear` | Total traded weight per commodity and year |
| 09 | `AverageWeightPerCommodityAndYear` | Average traded weight per commodity and year |
| 10 | `BrazilAverageWeightPerCommodityAndYear` | ... for Brazil |
| 11 | `BrazilAverageWeightPerFlowAndYear` | Average weight of Brazilian imports/exports per year |
| 12 | `BrazilAveragePricePerKgByCommodityAndYear` | Average price per kg of Brazilian trade, per commodity and year |
| 13 | `MaxTradeValuePerCommodityCode` | Highest transaction value per commodity code |
| 14 | `MaxPricePerKgByCommodity` | Highest price per kg per commodity |
| 15 | `TransactionsPerCountryFlowAndYear` | Transactions per country, flow and year |

Build with Maven (`pom.xml` declares `hadoop-client` as a provided dependency) and submit any job to a cluster:

```sh
mvn package
hadoop jar target/mapreduce-trade-practices-1.0.jar TransactionsPerCountry <input.csv> <output-dir>
```

The sources were type-checked against the Hadoop 3 MapReduce API; running them needs a Hadoop installation. The Spark side of the same course is in [`../../python/big-data-spark/`](../../python/big-data-spark/).
