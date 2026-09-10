from __future__ import absolute_import, print_function

from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import json


class TwitterSubscriber(StreamListener):
    count_series = 0
    count_football = 0
    count_soap_opera = 0
    count_movie = 0
    def on_data(self, data):
        json_content = json.loads(data)
        tweets = json_content["text"]

        if 'serie' in tweets:
            self.count_series += 1
        if 'futebol' in tweets:
            self.count_football += 1
        if 'novela' in tweets:
            self.count_soap_opera += 1
        if 'filme' in tweets:
            self.count_movie += 1
        print("series: ", self.count_series, "football: ", self.count_football, "soap opera: ", self.count_soap_opera,
              'movie: ', self.count_movie)

        return True

    def on_error(self, status):
        print(status)

# To run this example you need a Twitter developer account: create an application at
# https://developer.twitter.com to obtain the consumer key/secret, then generate an access token.
# Never commit real credentials; read them from environment variables instead.
import os
print("Program start")
consumer_key = os.environ.get("TWITTER_CONSUMER_KEY", "<your-consumer-key>")
consumer_secret = os.environ.get("TWITTER_CONSUMER_SECRET", "<your-consumer-secret>")

access_token = os.environ.get("TWITTER_ACCESS_TOKEN", "<your-access-token>")
access_token_secret = os.environ.get("TWITTER_ACCESS_TOKEN_SECRET", "<your-access-token-secret>")

subscriber = TwitterSubscriber()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

stream = Stream(auth, subscriber)
# Portuguese keywords: TV, series, football, soap opera, movie
stream.filter(track=['TV', 'serie', 'futebol', 'novela', 'filme'], languages=["pt"])
