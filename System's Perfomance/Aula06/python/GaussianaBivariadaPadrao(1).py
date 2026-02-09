import numpy as np
import matplotlib.pyplot as plt

def f(x, y, ro):
    return (1/(2*np.pi*np.sqrt(1-(ro**2))))*np.exp(-(1/(2*(1-ro**2)))*(x**2-2*ro*x*y+y**2))

fig = plt.figure()
ax = plt.axes(projection="3d")

x = np.linspace(-4, 4, 100)
y = np.linspace(-4, 4, 100)

X, Y = np.meshgrid(x, y)
Z = f(X, Y, 0)


ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('z')

ax = plt.axes(projection='3d')
ax.plot_surface(X, Y, Z, rstride=3, cstride=3, linewidth=1, antialiased=True,
                cmap='winter', edgecolor='none')
ax.set_title('Gaussiana Bivariada Padrão');
ax.set_zlim(0,0.15)

plt.show()

fig, ax = plt.subplots()
ax.contour(X, Y, Z)
ax.set_title('Contorno Gaussiana Bivariada Padrão')
plt.show()
