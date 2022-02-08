import kivy
from kivy.app import App
from kivy.uix.label import Label
from kivy.uix.gridlayout import GridLayout
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from kivy.uix.screenmanager import ScreenManager, Screen
from functools import partial

sm, name_correlate, path_to_remember = ScreenManager(), [["a","MainPage"], ["b", "Galery"], ["c","Camera"], ["d","Settings"]], ""

main_pics_path = ['normal.jpg', 'normal2.jpg', 'normal.jpg',
				  'normal2.jpg', 'normal.jpg', 'normal.jpg',
				  'normal.jpg', 'normal.jpg', 'normal2.jpg']


class MainPage(GridLayout, Screen):
	def __init__(self, **kwargs):
		super(MainPage, self).__init__(**kwargs)
		self.cols, self.inside, self.header, self.block1, self.block2, self.block3, self.bottom = 1, GridLayout(cols=1, spacing=[10,10]), GridLayout(cols=2, row_default_height=10, spacing=[10,10]), GridLayout(cols=3, spacing=[10,10]), GridLayout(cols=3, spacing=[10,10]), GridLayout(cols=3,  row_default_height=10, spacing=[10,10]), GridLayout(cols=4,spacing=[10,10])		 		
		[[self.header.add_widget(Label(text=x, font_size=40, width=100)) for x in ["Label it","   "] ], [self.block1.add_widget(i) for i in [self.display_picture(x) for x in main_pics_path[0:3]]],[self.block2.add_widget(i) for i in [self.display_picture(x) for x in main_pics_path[3:6]]],[[self.block3.add_widget(i) for i in [self.display_picture(x) for x in main_pics_path[6:9]]]], [self.bottom.add_widget(self.select_bar(i)) for i in [x[0] for x in name_correlate]]]			
		[self.inside.add_widget(self.header), self.inside.add_widget(self.block1), self.inside.add_widget(self.block2), self.inside.add_widget(self.block3), self.inside.add_widget(self.bottom), self.add_widget(self.inside)]
		
	def display_picture(self, picname):
		b = Button(font_size=50, background_normal=picname)
		b.bind(on_press=partial(self.pressed, picname))
		return b

	def select_bar(self, text):
		b = Button(text=text, font_size=30)
		b.bind(on_press=partial(self.selected, b.text))
		return b

	def selected(self, instance, text):
		sm.current = [x[1] for x in name_correlate if x[0]==text.text][0]

	def pressed(self, instance, picname):
		print(instance)

class Galery(GridLayout, Screen):
	def __init__(self, **kwargs):
		super(Galery, self).__init__(**kwargs)

		self.cols = 1

		self.header = GridLayout()
		self.header.cols, self.header.spacing = 4, [10,10]

		self.header.add_widget(Label(text="Label it", font_size=50))
		[self.header.add_widget(Label(text=x,font_size=50)) for x in ["","",""] ]
		
		self.add_widget(self.header)

		self.bottom = GridLayout()
		self.bottom.cols, self.bottom.spacing = 4, [10,10]

		self.main_bar_names = [x[0] for x in name_correlate]

		for i in self.main_bar_names:
			self.bottom.add_widget(self.select_bar(i))

		self.add_widget(self.bottom)
	
	def select_bar(self, text):
		b = Button(text=text, font_size=30)
		b.bind(on_press=partial(self.selected, b.text))
		return b

	def selected(self, instance, text):
		sm.current = [x[1] for x in name_correlate if x[0]==text.text][0]

class Camera(GridLayout, Screen):
	def __init__(self, **kwargs):
		super(Camera, self).__init__(**kwargs)

		self.cols = 1

		self.header = GridLayout()
		self.header.cols, self.header.spacing = 4, [10,10]

		self.header.add_widget(Label(text="Label it", font_size=50))
		[self.header.add_widget(Label(text=x,font_size=50)) for x in ["","",""] ]
		
		self.add_widget(self.header)

		self.bottom = GridLayout()
		self.bottom.cols, self.bottom.spacing = 4, [10,10]

		self.main_bar_names = [x[0] for x in name_correlate]

		for i in self.main_bar_names:
			self.bottom.add_widget(self.select_bar(i))

		self.add_widget(self.bottom)
	
	def select_bar(self, text):
		b = Button(text=text, font_size=30)
		b.bind(on_press=partial(self.selected, b.text))
		return b

	def selected(self, instance, text):
		sm.current = [x[1] for x in name_correlate if x[0]==text.text][0]

class Settings(GridLayout, Screen):
	def __init__(self, **kwargs):
		super(Settings, self).__init__(**kwargs)

		self.cols = 1

		self.header = GridLayout()
		self.header.cols, self.header.spacing = 4, [10,10]

		self.header.add_widget(Label(text="Label it", font_size=50))
		[self.header.add_widget(Label(text=x,font_size=50)) for x in ["","",""] ]
		
		self.add_widget(self.header)

		self.bottom = GridLayout()
		self.bottom.cols, self.bottom.spacing = 4, [10,10]

		self.main_bar_names = [x[0] for x in name_correlate]

		for i in self.main_bar_names:
			self.bottom.add_widget(self.select_bar(i))

		self.add_widget(self.bottom)
	
	def select_bar(self, text):
		b = Button(text=text, font_size=30)
		b.bind(on_press=partial(self.selected, b.text))
		return b

	def selected(self, instance, text):
		sm.current = [x[1] for x in name_correlate if x[0]==text.text][0]

class OnePicturePage(GridLayout, Screen):
	pass

sm.add_widget(MainPage(name="MainPage"))
sm.add_widget(Galery(name="Galery"))
sm.add_widget(Camera(name="Camera"))
sm.add_widget(Settings(name="Settings"))

sm.add_widget(OnePicturePage(name="OnePicturePage"))

class MyApp(App):
	def build(self):
		return sm

if __name__=="__main__":
	MyApp().run()
