#!/bin/bash

# Read token from first argument
TOKEN="$1"

if [[ -z "$TOKEN" ]]; then
  echo "❌ No token provided. Usage: $0 <jwt-token>"
  exit 1
fi

curl -k -X POST "https://localhost:8080/api/grid" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN" \
     -d @- <<'EOF'
{
  "name": "GRID-B",
  "supplier": {
    "Gasoline70": 200000000,
    "Ethanol70": 250000000,
    "Diesel50": 300000000
  },
  "price_controller": {
    "Gasoline70": 6100,
    "Ethanol70": 4190,
    "Diesel50": 4980
  },
  "isles": [
    [
      {
        "number": 1,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"}
        ]
      },
      {
        "number": 2,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"}
        ]
      }
    ],
    [
      {
        "number": 3,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4980,"fuel_type":"Diesel50","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"}
        ]
      },
      {
        "number": 4,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4980,"fuel_type":"Diesel50","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"}
        ]
      }
    ]
  ]
}
EOF
