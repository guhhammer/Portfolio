
use crate::{models::{user::User, user_fetch::UserFetch}, utils::{match_category, match_tier}};

pub fn user_to_userfetch(user: User) -> UserFetch {

    UserFetch {

        profile_image: user.profile_image, // The profile image will be a base64 string

        profile_name: user.profile_name,

        profile_short_description: user.profile_short_description,

        donate_address: user.donate_address,

        social_media: user.social_media.join("!%&$$&%!"),

        long_description: user.long_description,

        categories: match_category::match_categories_to_string(&user.categories),

        tier: match_tier::to_str(&user.tier),

    }

}

