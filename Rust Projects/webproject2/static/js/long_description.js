document.addEventListener("DOMContentLoaded", function () {

  let dLongElement = document.getElementById("long-description");
  let longDescription = dLongElement.getAttribute("data-profile-name");
  dLongElement.innerHTML = longDescription;
  
});