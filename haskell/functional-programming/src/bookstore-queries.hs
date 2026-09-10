-- Bookstore queries: a tuple type with accessors, then map / filter / foldr queries over a list of books.
-- Functional Programming course, PUCPR (2019). Load with: ghci bookstore-queries.hs
--
-- Assignment
-- 1 - A database of books: type Book
-- 2 - Accessors for the fields
-- 3 - List the books of a bookstore
-- 4 - List the books bought from a given year on
-- 5 - List the books bought from a given year on, by genre
-- 6 - Total sale value of the books above, by purchase year, by title, by author, by author and title
--
-- (bookstore, title, author, purchase year, genre, sale price)

-- In "radical" functional programming a structure never changes once created.
-- Accessors are named functions that pick one field out of a type.
-- Every functional language needs a garbage collector: objects die when nothing references them.

bookstore = [("Saraiva","Logic","Carnap",2017,"Math",25.90),("Saraiva","The Witcher","Andrzej Sapkowski",2018,"Literature",40.00),("Saraiva","Logic Vol.2","Carnap",2018,"Math",30.90)]

-- Question 1: the Book type.
type Book = (String, String, String, Integer, String, Double)

-- Question 2: accessors.
title :: Book -> String
title (_,x,_,_,_,_) = x

purchaseYear :: Book -> Integer
purchaseYear (_,_,_,x,_,_) = x

genre :: Book -> String
genre (_,_,_,_,x,_) = x

price :: Book -> Double
price (_,_,_,_,_,x) = x

author :: Book -> String
author (_,_,x,_,_,_) = x

-- Question 3: titles of the bookstore.
titles :: [Book] -> [String]
titles = \x -> map (\y -> title y) x

-- Question 4: titles bought from a given year on.
titlesFrom :: Integer -> [Book] -> [String]
titlesFrom year x = map (\z -> title z) (filter (\y -> purchaseYear y >= year) x)

-- Question 5: titles bought from a given year on, of a given genre.
titlesFromByGenre :: Integer -> String -> [Book] -> [String]
titlesFromByGenre year g x = map (\w -> title w) (filter (\y -> genre y == g && (purchaseYear y == year || purchaseYear y > year)) x)

-- Question 6: total sale value ...

-- ... by purchase year (from the year on):
salesByYear :: Integer -> [Book] -> Double
salesByYear year x = foldr (+) 0.0 (map (\w -> price w) (filter (\y -> purchaseYear y > year || purchaseYear y == year) x))

-- ... by title:
salesByTitle :: String -> [Book] -> Double
salesByTitle book x = foldr (+) 0.0 (map (\w -> price w) (filter (\y -> title y == book) x))

-- ... by author:
salesByAuthor :: String -> [Book] -> Double
salesByAuthor n x = foldr (+) 0.0 (map (\w -> price w) (filter (\y -> author y == n) x))

-- ... by author and title:
salesByAuthorAndTitle :: String -> String -> [Book] -> Double
salesByAuthorAndTitle b n x = foldr (+) 0.0 (map (\w -> price w) (filter (\y -> author y == n && title y == b) x))
