


y = 10


test :: Int -> Int
test = \x -> x*y


--soma x y = x + y

s = \x y -> x + y


soma :: Int -> Int -> Int
soma = \m n -> sum [x | x <- [m,m+2 .. n], mod x 2 == 0]

-- Sobre a fórmula utilizada:
{-
  Função horária da posição:
      - S = So + Vo*t + (a*(t^2))/2
      S -> posição final: output em metros
      So -> posição inicial: 0 metros
      Vo -> velocidade inicial: 0 m/s
      t -> tempo: input em segundos
      a -> aceleração da gravidade

      - Manipulação Algébrica da função:
          - S = So + Vo*t + (a*(t^2))/2
          - S = 0 + 0*t + (a*(t^2))/2
          - S = (a*(t^2))/2
-}


a = 9.86  -- aceleração da gravidade.

-- Função cálculo de altitude de objeto:
altitude :: Float -> Float
altitude = \t -> a*(t^2)/2

{-
    A função altitude é impura pois depende da gravidade do local - que pode
    variar. Ela depende de uma variável global: a, logo, essa função pode não
    retornar o mesmo valor(output) para o mesmo input.
-}


