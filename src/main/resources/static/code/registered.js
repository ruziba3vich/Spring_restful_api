document.addEventListener('DOMContentLoaded', function() {
    // Check if the user has a token
    const token = localStorage.getItem('token');
    if (token) {
        // Redirect to registered.html
        window.location.href = 'registered.html';
    }
});
