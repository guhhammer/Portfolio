document.addEventListener('DOMContentLoaded', function() {
    // Ensure the DOM is fully loaded before accessing the input
    
    const searchInput = document.getElementById('i-search');

    searchInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent the default action (form submission)
            triggerSearchFunction(); // Call the function you want to trigger
        }
    });

    function triggerSearchFunction() {

        const searchQuery = searchInput.value;
        
        if (searchQuery == "") { window.location.href = "/search"; }

        //make category box in above search bar.

        const categoryValue = 'false'; // categoryCheckbox.checked ? 'true' : 'false';

        window.location.href = `/search?query=${searchQuery}&category=${categoryValue}`

        
    }
});
