class LoginManager {
    constructor() {
        // Auto-detect backend URL based on environment
        // For local development, use localhost:8080
        // For production, you can modify this URL
        this.apiUrl = this.getBackendUrl();
        this.init();
    }

    getBackendUrl() {
        // Since frontend and backend are served from the same Java application,
        // we can use relative URLs or construct the URL based on current location
        const protocol = window.location.protocol;
        const hostname = window.location.hostname;
        const port = window.location.port;
        
        // Construct the API base URL using current location
        return `${protocol}//${hostname}${port ? ':' + port : ''}/api`;
    }

    init() {
        const form = document.getElementById('loginForm');
        form.addEventListener('submit', (e) => this.handleLogin(e));
    }

    async handleLogin(event) {
        event.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const messageDiv = document.getElementById('message');
        const form = document.getElementById('loginForm');

        // Show loading state
        form.classList.add('loading');
        this.showMessage('Logging in...', 'info');

        try {
            const response = await fetch(`${this.apiUrl}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            const data = await response.json();

            if (response.ok) {
                this.showMessage(data.message || 'Login successful!', 'success');
                // Store token if provided
                if (data.token) {
                    localStorage.setItem('authToken', data.token);
                }
                // Redirect or perform post-login actions
                setTimeout(() => {
                    this.handleSuccessfulLogin(data);
                }, 1500);
            } else {
                this.showMessage(data.message || 'Login failed. Please try again.', 'error');
            }
        } catch (error) {
            console.error('Login error:', error);
            this.showMessage('Network error. Please check your connection and try again.', 'error');
        } finally {
            form.classList.remove('loading');
        }
    }

    showMessage(text, type) {
        const messageDiv = document.getElementById('message');
        messageDiv.textContent = text;
        messageDiv.className = `message ${type}`;
        messageDiv.style.display = 'block';

        // Auto-hide message after 5 seconds for non-success messages
        if (type !== 'success') {
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 5000);
        }
    }

    handleSuccessfulLogin(data) {
        // You can customize this based on your needs
        console.log('Login successful:', data);
        alert(`Welcome, ${data.user?.username || 'User'}!`);
        
        // Example: redirect to dashboard
        // window.location.href = '/dashboard.html';
        
        // For demo purposes, just clear the form
        document.getElementById('loginForm').reset();
    }
}

// Initialize the login manager when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new LoginManager();
});

// Utility function to check if user is logged in
function isLoggedIn() {
    return localStorage.getItem('authToken') !== null;
}

// Utility function to logout
function logout() {
    localStorage.removeItem('authToken');
    window.location.reload();
}