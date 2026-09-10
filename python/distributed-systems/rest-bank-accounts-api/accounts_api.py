from flask import Flask, request
from flask_restful import Resource, Api, reqparse

## Authors: André de Macedo Wlodkovski, Gabriel Marcondes Ribas, Gustavo Hammerschmidt.

app = Flask(__name__)
api = Api(app)


class AccountInterface:

	def deposit(self) -> None: pass
	
	def withdraw(self) -> float: pass
	
	def get_balance(self) -> float: pass
	
	def rename(self) -> None: pass 

	def get_name(self) -> str: pass

class InterfaceExecutionHandler: pass

class Account(AccountInterface):

	def __init__(self) -> None: self.balance, self.account_name = float(0), "Account"

	def deposit(self, valor) -> None: self.balance += valor

	def withdraw(self, valor) -> float: self.balance -= valor if valor < self.balance else 0.0

	def get_balance(self) -> float: return round(self.balance, 2)

	def rename(self, nome) -> None: self.account_name = nome

	def get_name(self) -> str: return self.account_name


accounts, account_placed, parser = {}, [], reqparse.RequestParser()
_ = [parser.add_argument(_arg) for _arg in ['value', 'action']]


class ExecutionHandler(InterfaceExecutionHandler):

	def __init__(self, function) -> None: self.function = function

	def __call__(self, *args, **kwargs) -> ():

		global accounts, account_placed

		if int(kwargs['_id']) < 0: kwargs['_id'] = str(-1 * int(kwargs['_id']))

		if self.function.__name__ == "patch":

			parser_args = parser.parse_args()

			value, action = float(parser_args['value']), str(parser_args['action'])
			value = value if value > 0 else -1 * value

			if action not in ['deposit', 'withdraw']: return {'resp': 'Requested action does not exist!'}

			x = 'deposited into' if action == 'deposit' else 'withdrawn from'
			w = 0.0 if action == "withdraw" and value > accounts[str(kwargs['_id'])].get_balance() else value
		
			if not (int(kwargs['_id']) < len(account_placed) and accounts[str(kwargs['_id'])] != None):
				return {'resp': f'Value {value} cannot be {x} account {kwargs["_id"]}: account does not exist!'}

			return self.function(self, *args, **kwargs, value_decorator=value, action_decorator=action, resp=[w, x])

		if not (int(kwargs['_id']) < len(account_placed) and accounts[str(kwargs['_id'])] != None):

			if self.function.__name__ == "delete": return {'resp': f'Account {kwargs["_id"]} cannot be deleted: account does not exist!'}

			elif self.function.__name__ == "get": return {'resp': "Account does not exist, no balance!"}

		return self.function(self, *args, **kwargs)

class EndpointAccounts(Resource):

	## GET	http://localhost:5000/accounts	lists the accounts
	##
	## Exemplo: curl "http://localhost:5000/accounts" -X GET
	##
	def get(self) -> {}: 

		return {'accounts': { str(i) : accounts[str(i)].get_name() 
		         for i in range(len(accounts)) if accounts[str(i)] != None} }

	## POST	http://localhost:5000/accounts 	creates an account with identifier <id>
	##
	## Exemplo: curl "http://localhost:5000/accounts" -X POST
	##
	def post(self) -> {}:

		for ap in range(len(account_placed)):

			if account_placed[ap] == 0:

				account_placed[ap], accounts[str(ap)] = 1, Account()

				accounts[str(ap)].rename('Account ' + str(ap))
				
				return {'resp': f'new account created with id {ap}'}

		index, accounts[str(index)] = len(account_placed), Account()

		account_placed.append(1); accounts[str(index)].rename('Account ' + str(index))

		return {'resp': f'new account created with id {index}'}

class EndpointAccountsId(Resource):

	## GET	http://localhost:5000/accounts/<id>	returns the balance of account <id>
	##
	## Exemplo: curl "http://localhost:5000/accounts/1" -X GET
	##
	@ExecutionHandler
	def get(self, _id) -> {}: return {'balance': str(accounts[str(_id)].get_balance())}

	## PUT (or PATCH)	http://localhost:5000/accounts/<id>	deposits into / withdraws from account <id>
	##
	## Exemplo: curl "http://localhost:5000/accounts/1" -d "action=deposit" -d "value=100" -X PATCH
	##
	@ExecutionHandler
	def patch(self, _id, value_decorator=0.0, action_decorator='', resp=[]) -> {}:

		getattr(accounts[str(_id)], action_decorator)(value_decorator)

		return {'resp': f'Value {resp[0]} was {resp[1]} account {_id}.'}

	## DELETE	http://localhost:5000/accounts/<id>	deletes account <id>
	##
	## Exemplo: curl "http://localhost:5000/accounts/2" -X DELETE
	##
	@ExecutionHandler
	def delete(self, _id) -> {}:

		accounts[str(_id)], account_placed[int(_id)] = None, 0

		return {'resp': f'account {_id} was deleted.'}


api.add_resource(EndpointAccounts, '/accounts')

api.add_resource(EndpointAccountsId, '/accounts/<string:_id>')


if __name__ == '__main__':

	app.run(debug=True)
