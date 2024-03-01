document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('registrationForm');

  form.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior

    // Get form data
    const formData = new FormData(form);
    const userData = {};
    formData.forEach((value, key) => {
      userData[key] = value;
    });
    console.log("came to register");
    // Send data to the register endpoint
    fetch('/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    })
    .then(response => response.json())
    .then(data => {
      console.log('Registration successful:', data);

      // Store the token in local storage
      localStorage.setItem('token', data.token);

      // Redirect to registered.html
      window.location.href = 'registered.html';
    })
    .catch(error => {
      console.error('Registration failed:', error);
      // Handle error
    });
  });
});  
