import numpy as np
import matplotlib.pyplot as plt


fig = plt.figure()
ax = plt.axes(projection="3d")

z = np.linspace(0, 15, 1000)
x = np.cos(z)
y = np.sin(z)

xyticks = np.arange(-1, 1.5, 0.5)
ax.set_xticks(xyticks)
ax.set_yticks(xyticks)

ax.plot3D(x, y, z, 'blue')
plt.show()



def f(x, y):
    return np.exp(-(x+y))

fig = plt.figure()
ax = plt.axes(projection="3d")


x = np.linspace(0, 3, 30)
y = np.linspace(0, 3, 30)

X, Y = np.meshgrid(x, y)
Z = f(X, Y)


ax = plt.axes(projection='3d')
ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('z')

ax.plot_surface(X, Y, Z, rstride=1, cstride=1,
                cmap='winter', edgecolor='none')
ax.set_title('Exemplo 1');

plt.show()


def F(x, y):
    return (1-np.exp(-x))*(1-np.exp(-y))

fig = plt.figure()
ax = plt.axes(projection="3d")


x = np.linspace(0, 3, 30)
y = np.linspace(0, 3, 30)
X, Y = np.meshgrid(x, y)
Z = F(X, Y)

ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('z')

ax = plt.axes(projection='3d')
ax.plot_surface(X, Y, Z, rstride=1, cstride=1,
                cmap='winter', edgecolor='none')
ax.set_title('Exemplo 5');

plt.show()

