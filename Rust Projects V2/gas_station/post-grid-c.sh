#!/bin/bash

# Read token from first argument
TOKEN="$1"

if [[ -z "$TOKEN" ]]; then
  echo "❌ No token provided. Usage: $0 <jwt-token>"
  exit 1
fi

echo "✅ Using token: $TOKEN"

curl -k -X POST "https://localhost:8080/api/grid" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN" \
     -d @- <<'EOF'
{
  "name": "GRID-C",
  "supplier": {
    "Gasoline70": 200000000,
    "Gasoline80": 150000000,
    "Gasoline95": 100000000
  },
  "price_controller": {
    "Gasoline70": 6100,
    "Gasoline80": 6300,
    "Gasoline95": 6870
  },
  "isles": [
    [
      {
        "number": 1,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6300,"fuel_type":"Gasoline80","pump_status":"Active"},
          {"number":2,"liters_thousandth":0,"price_thousandth":6870,"fuel_type":"Gasoline95","pump_status":"Active"}
        ]
      },
      {
        "number": 2,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6300,"fuel_type":"Gasoline80","pump_status":"Active"},
          {"number":2,"liters_thousandth":0,"price_thousandth":6870,"fuel_type":"Gasoline95","pump_status":"Active"}
        ]
      },
      {
        "number": 3,
        "triggers": [
          {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
          {"number":1,"liters_thousandth":0,"price_thousandth":6300,"fuel_type":"Gasoline80","pump_status":"Active"},
          {"number":2,"liters_thousandth":0,"price_thousandth":6870,"fuel_type":"Gasoline95","pump_status":"Active"}
        ]
      }
    ]
  ]
}
EOF
