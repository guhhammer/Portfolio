from __future__ import absolute_import, print_function

from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import json


class AssinanteTwitter(StreamListener):
    cont_serie = 0
    cont_futebol = 0
    cont_novela = 0
    cont_filme = 0
    def on_data(self, data):
        conteudoJSON = json.loads(data)
        tweets = conteudoJSON["text"]

        if 'serie' in tweets:
            self.cont_serie +=1
        if 'futebol' in tweets:
            self.cont_futebol += 1
        if 'novela' in tweets :
            self.cont_novela += 1
        if 'filme' in tweets:
            self.cont_filme +=1
        print("serie: ",self.cont_serie, "futebol: ",self.cont_futebol,"novela: ",self.cont_novela,
              'filme: ',self.cont_filme)

        return True

    def on_error(self, status):
        print(status)

# Para executar esse exemplo é preciso possuir uma conta no twitter, caso não possua crie uma.
# Entre no site http://apps.twitter.com e crie uma nova applicação preenchendo as informações
# Será gerado o consumer key e o consumer secret, que são a identificação de sua aplicação no twitter.
print("Inicio do programa")
consumer_key="vFFetJBKs8z0n1SPfb1A9zOCC"
consumer_secret="AfoErlU0ckRuDXDpg6kDhklH5axHzCXnBBDoXkhj69H9boVbW0"

#Você será redirecionado para outra página, clique na aba 'Keys and Access Tokens'
#Crie um token de acesso novo, ele será utilizado no lugar de suas credenciais
access_token="1032042423181090818-Ix1z2fXR5G1IQlV3cDJojh7Qj5Tqy2"
access_token_secret="tdMQpXFb4Y4uLTbyn2UT4YgLVSBK69aVDxnmvED6WHQET"

assinante = AssinanteTwitter()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

stream = Stream(auth, assinante)
stream.filter(track=['TV','serie','futebol','novela','filme'], languages=["pt"])



