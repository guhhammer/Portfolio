"""Quick check of the statbureau.org inflation-rate API.

The Monte Carlo spreadsheet needed an inflation figure; this script was used
to see what the JSON endpoint returns for a given country and date range.
"""

import requests


parameters = {
    "country": "united-states",
    "start": "2021/1/1",
    "end": "2021/1/1",
}


response = requests.get("https://www.statbureau.org/calculate-inflation-rate-json", params=parameters)

print(response.status_code)

print(response.json())
