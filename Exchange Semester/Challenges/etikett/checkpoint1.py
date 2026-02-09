import kivy
from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.gridlayout import GridLayout
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from functools import partial
from kivy.uix.screenmanager import ScreenManager, Screen



sm = ScreenManager()




class MyGrid(GridLayout,Screen):
	def __init__(self, **kwargs):
		super(MyGrid, self).__init__(**kwargs)

		self.eq, self.cols, self.spacing = "", 4, [10,10]

		self.t_ = [self.labeler(str(x)) for x in [7,8,9,4,5,6,1,2,3,0]]	
		self.op = [self.labeler(x) for x in ["x","-","+","/","="]]
		
		z, i = 1, 0
		for t in self.t_:
			if z % 4 == 0:
				self.add_widget(self.op[i])
				i, z = i+1, 1
			self.add_widget(t)
			z += 1

		for k in range(i, len(self.op)):
			self.add_widget(self.op[k])

	def labeler(self, t):
		b = Button(text=t, font_size=50, background_normal="normal2.jpg")
		b.bind(on_press=partial(self.pressed, b.text))
		return b

	def pressed(self, instance, t):
			
		if t.text == "=":
			sm.current = "second"
			return
		if t.text == "=":
			print(self.eq, "=", eval(self.eq))
			self.eq = ""
		else:
			self.eq += t.text+" " if t.text != "x" else "* "

	


class MyGrid2(GridLayout,Screen):
	def __init__(self, **kwargs):
		super(MyGrid2, self).__init__(**kwargs)

		self.eq = ""
		self.cols = 1

		self.add_widget(Label(text="Email: "))
		self.email = TextInput(multiline=False)
		self.add_widget(self.email)


sm.add_widget(MyGrid(name="first"))
sm.add_widget(MyGrid2(name="second"))



class MyApp(App):
	def build(self):
		return sm


if __name__ == "__main__":
	MyApp().run()

