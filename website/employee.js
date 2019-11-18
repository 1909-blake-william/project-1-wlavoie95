let currentUser;

function getCurrentUserInfo() {
    fetch('http://localhost:8080/Project1/auth/session-user', {
        credentials: 'include'
    })
    .then(resp => resp.json())
    .then(data => {
        
        currentUser = data;
        console.log(currentUser);
        document.getElementById("nav-username").innerText = "Welcome, " + currentUser.username;
    })
    .catch(err => {
        window.location = 'login.html';
    })
}

getCurrentUserInfo();

