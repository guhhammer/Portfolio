

soma :: Int -> Float -> Float
soma valorn acc = if valorn == 0 then acc+1 else (soma (valorn-1) (acc+(1/(2^valorn))))