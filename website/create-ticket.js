
function postTicket(event) {
    event.preventDefault();

    const amount = document.getElementById('inputAmount').value;
    const reimbType = document.getElementById('inputReimbType').value;
    const 
    const user = getCurrentUserInfo();
    fetch(`http://localhost:8080/Project1/reimbursements/employee/ticket`, {
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