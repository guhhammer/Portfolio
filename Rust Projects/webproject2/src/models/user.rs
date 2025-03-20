use rocket::serde::{Deserialize, Serialize};
use super::tier::Tier;
use crate::models::category::Category;

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct User {
   
    pub profile_image: String, // The profile image will be a base64 string
   
    pub profile_name: String,
   
    pub profile_short_description: String,
   
    pub donate_address: String,
   
    pub social_media: Vec<String>,
   
    pub long_description: String,

    pub categories: Vec<Category>, 

    pub tier: Tier,

}

// social media I added:


/*

// add access to other chains 
facebook
twitter
instagram
linkedin
youtube
tiktok
snapchat
pinterest
reddit
twitch
discord

// Communication platforms
whatsapp
telegram
signal
skype
slack

// Blogging & writing platforms // new
medium
tumblr

// Video & image-sharing platforms // new
vimeo
flickr
imgur
artstation
deviantart
dribbble
behance

// Music & audio platforms // new
soundcloud
spotify
pandora
lastfm
bandcamp
mixcloud

// Personal website and others // new
website
wordpress
others

// Cryptocurrency addresses // new
     bitcoin
     ethereum
     litecoin

// credit card payment and send to wallet.

*/
