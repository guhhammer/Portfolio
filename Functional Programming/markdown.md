# Exemplo de execução de código em *Markdown*

## Código em *Julia*

Para executar o código a seguir em *Julia*: Ctrl+Shift+Enter. "Or you can just click the run button in preview." O resultado da execução será exibido logo abaixo do código fonte.

O procedimento mencionado acima pode ser usado para executar código em outras linguagens, como *Python*, por exemplo.

- *Código fonte --- Imagem gerada*

```julia {cmd="C:/Users/Gustavo/AppData/Roaming/Julia-1.1.1/bin/julia.exe" hide:false class:"line-numbers"}
println("This code will run Julia program")
println("Com a opção hide:true você verá o resultado da execução, mas não verá este código fonte.")
println(4+5)
println((x->x+1)(10))
println("Versão da linguagem: ", VERSION)
```

Exibindo a versão da linguage `Julia`:

```haskell {cmd="C:/Program Files (x86)/Haskell Platform/8.6.3/bin/runhaskel.exe" hide:false class:"line-numbers"}
main2+3
```

```haskell {cmd="C:/Users/Gustavo/ghci" hide:false class:"line-numbers"}

```

## Código em *Python*

Funções básicas>

```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}
(lambda x: x+1)(9)
print((lambda x: x+1)(45))
```


```python{cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false}
from __future__ import division
import sympy as sym
from sympy import *
from IPython.display import display
from sympy.interactive import printing
printing.init_printing()

x, y, z, h = symbols("x y z h")
a, b, k, m, n = symbols("a b k m n", integer=True)
f, g = map(Function, 'fg')


exp1 = ((x + y)**3 - (x + y))
display(exp1)
```
### Um gráfico

```python {cmd="C:/Users/Gustavo/Anaconda3/python.exe" output="markdown" hide=false matplotlib=true}

import matplotlib
import matplotlib.pyplot as plt
import numpy as np
x = np.linspace(0, 5, 100)
y = x ** 2

plt.plot(x, y, 'r')
plt.plot(y, x, 'b')
plt.xlabel('x')
plt.ylabel('y')
plt.title('mpe for fun')
plt.show()
print("job done")
```
