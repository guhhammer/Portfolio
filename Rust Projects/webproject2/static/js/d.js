function resize_plus() {
    const container = document.getElementById("d-container");
    const longDescription = document.getElementById("long-description");
    const moreButton = document.getElementById("b-more");

    // Toggle the "expanded" class on the container
    container.classList.toggle("expanded");

    if (container.classList.contains("expanded")) {
        // Move the container to the left
        container.style.marginLeft = "0";

        // Show the vertical bar and long description
        longDescription.classList.remove("hidden");

        // Change the button text to "-"
        moreButton.textContent = "−"; // Unicode minus sign

        // Calculate the remaining space for the long description
        const containerRect = container.getBoundingClientRect();
        const viewportWidth = window.innerWidth;
        const remainingWidth = viewportWidth - containerRect.right - 32; // 32px for mr-8 (8 * 4)

        // Set the width and position of the long description
        longDescription.style.width = `${remainingWidth}px`;
        longDescription.style.left = `${containerRect.width}px`; // Start right after the container
   
    } else {

        // Reset the container to the center
        container.style.marginLeft = "auto";

        // Hide the vertical bar and long description
        longDescription.classList.add("hidden");

        // Change the button text back to "+"
        moreButton.textContent = "+";
    
    }

}

function donateTo() {

    let donateAddress = document.getElementById("b-donate").getAttribute("data-profile-name");

    alert("Thank you for your donation! "+ donateAddress.toString());

}

document.addEventListener('DOMContentLoaded', function() {

    let imgElement = document.getElementById("i-profile");

    let base64Data = imgElement.getAttribute("data-profile-name"); // Get the Base64 string

    if (base64Data) {
    
        imgElement.src = "data:image/png;base64," + base64Data; // Set the src attribute to display the image
    
    }

});
