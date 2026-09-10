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
  "name": "GRID-A",
  "supplier": {
    "Diesel500": 500000000,
    "Ethanol70": 250000000,
    "Gasoline70": 200000000,
    "Gasoline85": 100000000,
    "Gasoline95": 50000000,
    "Diesel50": 300000000
  },
  "price_controller": {
    "Ethanol70": 4190,
    "Gasoline85": 6496,
    "Gasoline95": 6870,
    "Gasoline70": 6100,
    "Diesel50": 4980,
    "Diesel500": 5080
  },
  "isles": [
    [
      {
        "number": 1,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6496,"fuel_type":"Gasoline85","pump_status":"Active"},
          {"number":2,"liters_thousandth":0,"price_thousandth":6870,"fuel_type":"Gasoline95","pump_status":"Active"}
        ]
      },
      {
        "number": 2,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6496,"fuel_type":"Gasoline85","pump_status":"Active"},
          {"number":2,"liters_thousandth":0,"price_thousandth":6870,"fuel_type":"Gasoline95","pump_status":"Active"}
        ]
      }
    ],
    [
      {
        "number": 3,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"}
        ]
      },
      {
        "number": 4,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"}
        ]
      }
    ],
    [
      {
        "number": 5,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"}
        ]
      },
      {
        "number": 6,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4190,"fuel_type":"Ethanol70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"}
        ]
      }
    ],
    [
      {
        "number": 7,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":4980,"fuel_type":"Diesel50","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":4980,"fuel_type":"Diesel50","pump_status":"Active"}
        ]
      },
      {
        "number": 8,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":5080,"fuel_type":"Diesel500","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":5080,"fuel_type":"Diesel500","pump_status":"Active"}
        ]
      }
    ]
  ]
}
EOF
