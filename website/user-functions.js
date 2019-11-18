let currentUser;
let reimbursements;
let filteredReimbursements;

function view(filter) {
    document.getElementById('reimbursement-table-body').innerText = '';
    if (filter === '') {
        refreshTable();
    } else if (filter === 'Pending') {
        fetch(`http://localhost:8080/Project1/reimbursements/manager?status=${filter}`, {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                reimbursements = data;
                reimbursements.forEach(addReimbursementsToTable);
            })
            .catch(console.log);
    } else if (filter === 'Approved') {
        fetch(`http://localhost:8080/Project1/reimbursements/manager?status=${filter}`, {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                reimbursements = data;
                reimbursements.forEach(addReimbursementsToTable);
            })
            .catch(console.log);
    } else if (filter === 'Denied') {
        fetch(`http://localhost:8080/Project1/reimbursements/manager?status=${filter}`, {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                reimbursements = data;
                reimbursements.forEach(addReimbursementsToTable);
            })
            .catch(console.log);
    }

}

function addReimbursementsToTable(reimbursement) {
    console.log(reimbursement);
    //create the row element
    const row = document.createElement('tr');

    // create all the td elements and append them to the row
    const timeSubmitted = document.createElement('td');
    timeSubmitted.innerText = reimbursement.submitted;
    console.log(timeSubmitted)
    row.appendChild(timeSubmitted);

    const rAmount = document.createElement('td');
    rAmount.innerText = reimbursement.amount;
    console.log(rAmount)
    row.appendChild(rAmount);

    const rDescription = document.createElement('td');
    rDescription.innerText = reimbursement.description;
    console.log(rDescription)
    row.appendChild(rDescription);

    const rType = document.createElement('td');
    rType.innerText = reimbursement.ticketType;
    console.log(rType)
    row.appendChild(rType);

    const empName = document.createElement('td');
    empName.innerText = reimbursement.authorFirstName + ' ' + reimbursement.authorLastName;
    console.log(empName)
    row.appendChild(empName);

    const empEmail = document.createElement('td');
    empEmail.innerText = reimbursement.authorEmail;
    console.log(empEmail)
    row.appendChild(empEmail);

    if (reimbursement.statusType !== 'Pending') {

        const timeResolved = document.createElement('td');
        timeResolved.innerText = reimbursement.resolved;
        console.log(timeResolved)
        row.appendChild(timeResolved);

        const mName = document.createElement('td');
        mName.innerText = reimbursement.resolverFirstName + ' ' + reimbursement.resolverLastName;
        console.log(mName)
        row.appendChild(mName);

        const mEmail = document.createElement('td');
        mEmail.innerText = reimbursement.resolverEmail;
        console.log(mEmail)
        row.appendChild(mEmail);

        const rStatus = document.createElement('td');
        rStatus.innerText = reimbursement.statusType;
        row.appendChild(rStatus);

        const manage = document.createElement('td');
        manage.innerHTML = ' ';
        row.appendChild(manage);

    } else {

        const timeResolved = document.createElement('td');
        timeResolved.innerText = ' ';
        console.log(timeResolved)
        row.appendChild(timeResolved);

        const mName = document.createElement('td');
        mName.innerText = ' ';
        console.log(mName)
        row.appendChild(mName);

        const mEmail = document.createElement('td');
        mEmail.innerText = ' ';
        console.log(mEmail)
        row.appendChild(mEmail);

        const rStatus = document.createElement('td');
        rStatus.innerText = reimbursement.statusType;
        row.appendChild(rStatus);

        const manage = document.createElement('td');
        manage.innerHTML = `<button class="btn btn-lg btn-success btn-block" type="button" onclick="updateTicket('Approved', ${reimbursement.id})">Approve</button>
        <button class="btn btn-lg btn-danger btn-block" type="button" onclick="updateTicket('Denied', ${reimbursement.id})">Deny</button>`;
        row.appendChild(manage);
    }

    // append the row into the table
    //document.getElementById('reimbursement-table-body').appendChild(row)
    let output = document.getElementById('reimbursement-table-body')
    console.log(output)
    output.appendChild(row);
}

function refreshTable() {
    if (currentUser.role === 2) { //manager
        fetch('http://localhost:8080/Project1/reimbursements/manager', {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                reimbursements = data;
                reimbursements.forEach(element => {
                    addReimbursementsToTable(element)
                });
            })
            .catch(console.log);
    } else {
        let url = `http://localhost:8080/Project1/reimbursements/employee?username=${currentUser.username}`;
        fetch(url, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                reimbursements = data;
                reimbursements.forEach(element => {
                    addReimbursementsToTable(element)
                });
            })
            .catch(console.log);
    }
}



function getCurrentUserInfo() {
    fetch('http://localhost:8080/Project1/auth/session-user', {
        credentials: 'include'
    })
        .then(resp => resp.json())
        .then(data => {

            currentUser = data;
            console.log(currentUser);
            document.getElementById("nav-username").innerText = "Welcome, " + currentUser.username;
            refreshTable();
        })
        .catch(err => {
            window.location = 'login.html';
        })
}

getCurrentUserInfo();

function ticketPage() {
    fetch('http://localhost:8080/Project1/reimbursements/employee/create-ticket', {
        credentials: 'include'
    })
        .then(resp => resp.json())
        .then(data => {
            window.location = 'create-ticket.html';
        })
        .catch(err => {
            //window.location = 'login.html';
        })
}


function updateTicket(status, id) {
    let updatedInfo = {
        id: id,
        statusType: status,
        resolverId: currentUser.id
    };

    fetch('http://localhost:8080/Project1/reimbursements/manager/update-ticket', {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info
        body: JSON.stringify(updatedInfo)
    })
        .then(resp => resp.json())
        .then(data => {
            view('');
        })
        .catch(err => {
            window.location = 'login.html';
        })
    }

    function createTicket(event){
        event.preventDefault();

    const amount = document.getElementById('amount').value;
    const reimbType = document.getElementById('expense-type').value;
    const description = document.getElementById('description').value;
    ticket = {
        amount: amount,
        reimb_type: reimbType,
        description: description,
        user: currentUser.id,
    }
    fetch('/Project1/reimbursements/employee/create-ticket', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info
        body: JSON.stringify(ticket)
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
