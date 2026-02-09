from flask import Flask, request
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)

counter_get = 0

class endpoint(Resource):

	def get(self) -> {}:

		global counter_get

		return {"cont": counter_get}

class endpoint_msg(Resource):

	def patch(self, msg) -> {}:

		global counter_get

		counter_get += 1

		return {"resp": str(msg) + "_ECO"}

api.add_resource(endpoint, '/eco')
## Access Method: curl "http://localhost:5000/eco" -X GET

api.add_resource(endpoint_msg, '/eco/<string:msg>')
## Access method: curl "http://localhost:{PORT}/eco/{MESSAGE}" -X PATCH

if __name__ == '__main__':

	app.run(debug=True)
