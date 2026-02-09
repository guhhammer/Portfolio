
import requests



parameters = {	
	"country": "united-states",
	"start": "2021/1/1",
	"end": "2021/1/1",
}




response = requests.get("https://www.statbureau.org/calculate-inflation-rate-json", params=parameters)

print(response.status_code)



print(response.json())
