document.addEventListener("DOMContentLoaded", () => {

    let sm = document.getElementById("d-container-socialmedia").getAttribute("data-profile-name").split("!%&$$&%!");
    
    let links = [
        "artstation", "bandcamp", "behance", "deviantart", "dribbble", "discord",
        "email", "facebook", "flickr", "github", "imgur", "instagram", "last.fm", 
        "linkedin", "mixcloud", "pandora", "pinterest", "reddit", "signal", 
        "slack", "snapchat","soundcloud", "spotify", "tiktok", "tumblr", "twitch", 
        "t.me", "twitter", "vimeo", "wa.me", "wordpress", "youtube",  "whatsapp", "telegram",
    ];

    let paths = []; let names = [];

    for (const s of sm) {
    
        let ll = "link";

        for (const l of links) { if (s.includes(l)) { ll = l; } }
    
        if (ll == "t.me") { ll = "telegram"; }

        if (ll == "wa.me") { ll = "whatsapp"; }

        paths.push("/static/images/social_media_svgs/"+ll+".svg");
        names.push(ll);

    }

    const container = document.getElementById("d-container-socialmedia");

    container.innerHTML = "";

    let i = 0;
    paths.forEach((key) => {

        if (i == 3) {

            const a = document.createElement("a");
            a.id = "a-sm-toggle";
            a.target = "_blank";
            a.className = "hover:opacity-80"; 
            
            const img = document.createElement("img");
            img.src = "/static/images/social_media_svgs/more.svg";
            img.alt = names[i]; i += 1; 
            img.className = "w-8 h-8 rounded-full";  
            
            a.appendChild(img);
            container.appendChild(a);
            
        } else if (i > 3) {
            
        }
        else {
            
            const a = document.createElement("a");
            a.href = sm[i];
            a.target = "_blank";
            a.className = "hover:opacity-80";
            
            const img = document.createElement("img");
            img.src = key;
            img.alt = names[i]; i += 1; 
            img.className = "w-8 h-8 rounded-full";  
            
            a.appendChild(img);
            container.appendChild(a);
            
        }

    });


});

document.querySelectorAll("a img").forEach(img => {
    img.addEventListener("click", function(event) {
        event.stopPropagation(); // Stops event bubbling but allows link navigation
    });
});


document.addEventListener("a-sm-toggle").forEach(img => {
    img.addEventListener("click", function(event) {
        event.stopPropagation(); // Stops event bubbling but allows link navigation
    });
});
