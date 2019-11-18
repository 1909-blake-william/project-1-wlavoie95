function login(event) {
    event.preventDefault();

    const username = document.getElementById('inputUsername').value;
    const password = document.getElementById('inputPassword').value;
    const user = {
        username,
        password,
    };
    console.log(user);
    fetch('http://localhost:8080/Project1/auth/login', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info
        body: JSON.stringify(user)
    })
        .then(resp => resp.json())
        .then(data => {
            // redirect
            if (data.role === 2) {
                console.log('navigate to manager');
                window.location = 'manager.html';
            }
            else if (data.role === 1) {
                console.log('navigate to employee');
                window.location = 'employee.html';
            } else {
                document.getElementById('error-message').innerText = 'Failed to login';
            }
        })
        .catch(err => console.log(err));


}

