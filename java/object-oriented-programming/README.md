# Object-oriented programming (Java)

Coursework from the Object-Oriented Programming course at PUCPR (2018), rewritten in English: interfaces and inheritance, composition and collections, polymorphism, a small vector-space search engine, and two information-security exercises (steganography and RSA/AES). Everything compiles with `javac` (verified); each project has its own `src/` and the entry class named below.

The concurrency exercises that were kept in this folder during the degree (barbershops, dining philosophers, producer-consumer, one-lane bridge, parallel Fibonacci) now live in [`../concurrent-programming/`](../concurrent-programming/).

## Projects

| Project | What it is | Run |
| --- | --- | --- |
| [`bank-and-crypto-accounts/`](bank-and-crypto-accounts/) | A bank modelled with interfaces (`Customer`, `CheckingAccount`, `LimitedCheckingAccount`, `SavingsAccount`, `Loan`, `Cryptocurrency` with `Bitcoin`/`Ethereum`), one implementation per account type and a `Bank` facade that links accounts to customers. | `bank.Main` |
| [`search-engine/`](search-engine/) | Vector-space information retrieval over six documents: dictionary, TF / DF / IDF / TF-IDF matrices and cosine-similarity ranking of a query. | `app.Main [folder] [query...]` |
| [`prefix-expression-evaluator/`](prefix-expression-evaluator/) | Expression trees with constants, variables and n-ary operators printed in prefix (Polish) notation and evaluated. | `prefix.Demo`, `prefix.Main` (interactive) |
| [`image-steganography/`](image-steganography/) | Least-significant-bit steganography: hides a text and a whole secret image inside a cover image and extracts them back (`images/`). | `stego.Steganography` from the project folder |
| [`rsa-and-aes-ciphers/`](rsa-and-aes-ciphers/) | Public-key (RSA 2048) and symmetric (AES-128) encryption round trips with the JCA. | `ciphers.RsaExample`, `ciphers.AesExample` |
| [`real-estate-valuation/`](real-estate-valuation/) | Polymorphism: houses and apartments appreciate/depreciate differently through the same `Property` calls. | `realestate.Analysis` |
| [`employee-salaries/`](employee-salaries/) | Inheritance and overriding: employees, salespeople (commission) and managers (bonus). | `payroll.Main` |
| [`students-and-courses/`](students-and-courses/) | Composition and collections: a course with a teacher and enrolled students, per-student and class averages. | `school.Main` |
| [`countries-and-continents/`](countries-and-continents/) | Countries grouped in a continent: totals, densities, extremes, neighbours and common neighbours. | `geo.Continent` |
| [`small-exercises/`](small-exercises/) | One package per class exercise: points and lines, grade average, a counter, Fibonacci, a zeta-series approximation, a fraction progression, real-number decomposition, a sorting benchmark (merge/quick/heap/pigeonhole/comb), a circular doubly linked list, a car configurator and a company payroll. | e.g. `sorting.SortingBenchmark` |

Build any project with, for example:

```sh
javac -d out $(find search-engine/src -name '*.java') && (cd search-engine && java -cp ../out app.Main)
```
