// login.js

document.getElementById('registerLink').addEventListener('click', function(e) {
    e.preventDefault();
    window.location.href = 'register.html';
});

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Get form data
        const loginData = {
            emailOrUsername: document.getElementById('loginEmailUsername').value,
            password: document.getElementById('loginPassword').value
        };

        // Send data to the login endpoint
        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Login successful:', data);

            // Store the token in local storage
            localStorage.setItem('token', data.token);

            // Redirect or perform other actions as needed
        })
        .catch(error => {
            console.error('Login failed:', error);
            // Handle error
        });
    });
});
