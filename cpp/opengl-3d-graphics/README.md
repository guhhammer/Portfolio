# OpenGL 3D graphics (C++ / freeglut)

Two exercises from the Computer Graphics course at PUCPR (2019), originally written on Windows with Visual Studio and freeglut, now portable (the `windows.h` include is guarded). They need freeglut and a display:

```sh
sudo apt install freeglut3-dev      # Debian/Ubuntu
make                                # builds scene-editor/scene-editor and bouncing-cube/bouncing-cube
```

| Project | What it is |
| --- | --- |
| [`scene-editor/`](scene-editor/) | Reads `scene.txt` (solid id, position and RGB per line: cube, sphere, cone, octahedron, cylinder, icosahedron), draws the solids under one light with Gouraud shading, and lets you rotate the scene (arrow keys), zoom (mouse buttons), resize the solids (Shift/Ctrl) and recolour the selected one (F1-F6 select, F7-F10 change the colour). The cube and the cone are built by hand from triangle strips and a triangle fan. |
| [`bouncing-cube/`](bouncing-cube/) | A tiny vector class drives a cube that moves under a gravity vector and bounces off the walls and floor of an orthographic view; the arrow keys rotate the view. |

The sources were type-checked against a stub of the freeglut API in this repository's clean-up; the graphical run was done on Windows at the time.
