from flask import Flask, request
from flask_restful import Resource, Api, reqparse

## Nomes: André de Macedo Wlodkovski, Gabriel Marcondes Ribas, Gustavo Hammerschmidt.

app = Flask(__name__)
api = Api(app)


class InterfaceConta:

	def depositar(self) -> None: pass
	
	def sacar(self) -> float: pass
	
	def getsaldo(self) -> float: pass
	
	def renomear(self) -> None: pass 

	def getnome(self) -> str: pass

class InterfaceExecutionHandler: pass

class Conta(InterfaceConta):

	def __init__(self) -> None: self.saldo, self.nome_conta = float(0), "Conta"

	def depositar(self, valor) -> None: self.saldo += valor

	def sacar(self, valor) -> float: self.saldo -= valor if valor < self.saldo else 0.0

	def getsaldo(self) -> float: return round(self.saldo, 2)

	def renomear(self, nome) -> None: self.nome_conta = nome

	def getnome(self) -> str: return self.nome_conta


lista_de_contas, account_placed, parser = {}, [], reqparse.RequestParser()
_ = [parser.add_argument(_arg) for _arg in ['valor', 'action']]


class ExecutionHandler(InterfaceExecutionHandler):

	def __init__(self, function) -> None: self.function = function

	def __call__(self, *args, **kwargs) -> ():

		global lista_de_contas, account_placed

		if int(kwargs['_id']) < 0: kwargs['_id'] = str(-1 * int(kwargs['_id']))

		if self.function.__name__ == "patch":

			parser_args = parser.parse_args()

			valor, action = float(parser_args['valor']), str(parser_args['action'])
			valor = valor if valor > 0 else -1 * valor

			if action not in ['depositar', 'sacar']: return {'resp' : 'Action solicitada inexistente!'}

			x = 'depositado na' if action=='depositar' else 'sacado da'
			w = 0.0 if action=="sacar" and valor > lista_de_contas[str(kwargs['_id'])].getsaldo() else valor
		
			if not (int(kwargs['_id']) < len(account_placed) and lista_de_contas[str(kwargs['_id'])] != None):
				return {'resp' : f'Valor de {valor} nao pode ser {x} conta {kwargs["_id"]}, conta inexistente!'}

			return self.function(self, *args, **kwargs, valor_decorator=valor, action_decorator=action, resp=[w, x])

		if not (int(kwargs['_id']) < len(account_placed) and lista_de_contas[str(kwargs['_id'])] != None):

			if self.function.__name__ == "delete": return {'resp' : f'Conta {kwargs["_id"]} nao pode ser deletada, conta inexistente!'}

			elif self.function.__name__ == "get": return {'resp' : "Conta inexistente, sem saldo!"}

		return self.function(self, *args, **kwargs)

class EndpointContas(Resource):

	## GET	http://localhost:5000/contas	obtém lista de contas
	##
	## Exemplo: curl "http://localhost:5000/contas" -X GET
	##
	def get(self) -> {}: 

		return {'lista de contas' : { str(i) : lista_de_contas[str(i)].getnome() 
		         for i in range(len(lista_de_contas)) if lista_de_contas[str(i)] != None} }

	## POST	http://localhost:5000/contas 	cria conta com identificador <id>
	##
	## Exemplo: curl "http://localhost:5000/contas" -X POST
	##
	def post(self) -> {}:

		for ap in range(len(account_placed)):

			if account_placed[ap] == 0:

				account_placed[ap], lista_de_contas[str(ap)] = 1, Conta()

				lista_de_contas[str(ap)].renomear('Conta '+str(ap))
				
				return {'resp' : f'nova conta criada com id {ap}'}

		index, lista_de_contas[str(index)] = len(account_placed), Conta()

		account_placed.append(1); lista_de_contas[str(index)].renomear('Conta '+str(index))

		return {'resp' : f'nova conta criada com id {index}'}

class EndpointContasId(Resource):

	## GET	http://localhost:5000/contas/<id>	obtém saldo da conta <id>
	##
	## Exemplo: curl "http://localhost:5000/contas/1" -X GET
	##
	@ExecutionHandler
	def get(self, _id) -> {}: return {'saldo' : str(lista_de_contas[str(_id)].getsaldo())}

	## PUT (ou PATCH)	http://localhost:5000/contas/<id>	efetua depósito/saque na conta <id>
	##
	## Exemplo: curl "http://localhost:5000/contas/1" -d "action=depositar" -d "valor=100" -X PATCH
	##
	@ExecutionHandler
	def patch(self, _id, valor_decorator=0.0, action_decorator='', resp=[]) -> {}:

		getattr(lista_de_contas[str(_id)], action_decorator)(valor_decorator)

		return {'resp' : f'Valor de {resp[0]} foi {resp[1]} conta {_id}.'}

	## DELETE	http://localhost:5000/contas/<id>	deleta a conta <id>
	##
	## Exemplo: curl "http://localhost:5000/contas/2" -X DELETE
	##
	@ExecutionHandler
	def delete(self, _id) -> {}:

		lista_de_contas[str(_id)], account_placed[int(_id)] = None, 0

		return {'resp' : f'conta {_id} foi deletada.'}


api.add_resource(EndpointContas, '/contas')

api.add_resource(EndpointContasId, '/contas/<string:_id>')


if __name__ == '__main__':

	app.run(debug=True)
