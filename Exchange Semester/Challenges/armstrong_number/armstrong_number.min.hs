stringfy = \x -> show x
splitter = \(x:y) num count -> if num == count then x else splitter y num (count+1)
split = \(x:y) index -> splitter (x:y) index 0
compare_ = \ch (x:y) -> if ch == show x then x else compare_ ch y
comparator_ = \c -> compare_ c [0,1,2,3,4,5,6,7,8,9]
separate_ = \x count c -> if length (stringfy x) == count then c else separate_ x (count+1) (c++[[split (stringfy x) count]])
separator = \x -> separate_ x 0 []
converter_ [] r = r
converter_ (x:y) r = converter_ y (r++[(comparator_ x)])
convert = \(x:y) -> converter_ (x:y) []
pow _ 0 res = res
pow x y res = pow x (y-1) res*x
power x y = pow x y 1
summer [] sum = sum
summer (x:y) sum = summer y sum+x
raiser [] base z = z
raiser (x:y) base z = raiser y base z++[power x base]
armstrong_number = \number base -> number == (summer (raiser (convert (separator number)) base []) 0)
ann = \n b -> armstrong_number n b