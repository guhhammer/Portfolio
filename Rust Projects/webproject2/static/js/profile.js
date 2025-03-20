document.addEventListener('DOMContentLoaded', function() {
    
    
    let hNameElement = document.getElementById("h-name");
    let profileName = hNameElement.getAttribute("data-profile-name");
    hNameElement.innerHTML = profileName;

    let pShortElement = document.getElementById("p-description");
    let shortDescription = pShortElement.getAttribute("data-profile-name");
    pShortElement.innerHTML = shortDescription;

});
