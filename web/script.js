// Login System JavaScript
class LoginSystem {
    constructor() {
        this.apiBase = '/api';
        this.currentUser = null;
        this.init();
    }

    init() {
        this.bindEvents();
        this.checkExistingSession();
    }

    bindEvents() {
        const loginForm = document.getElementById('loginForm');
        if (loginForm) {
            loginForm.addEventListener('submit', (e) => this.handleLogin(e));
        }

        // Add enter key support for form fields
        const inputs = document.querySelectorAll('input');
        inputs.forEach(input => {
            input.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    this.handleLogin(e);
                }
            });
        });
    }

    async handleLogin(event) {
        event.preventDefault();
        
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        const loginBtn = document.getElementById('loginBtn');
        const btnText = loginBtn.querySelector('.btn-text');
        const btnLoader = loginBtn.querySelector('.btn-loader');

        // Validation
        if (!username || !password) {
            this.showMessage('Please enter both username and password', 'error');
            return;
        }

        // Show loading state
        this.setLoadingState(true);
        btnText.style.display = 'none';
        btnLoader.style.display = 'inline';

        try {
            const response = await fetch(`${this.apiBase}/login`, {
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

            if (data.success) {
                this.currentUser = {
                    username: username,
                    role: data.role,
                    token: data.token
                };
                
                // Store session
                localStorage.setItem('loginSession', JSON.stringify(this.currentUser));
                
                this.showSuccess(data.message);
            } else {
                this.showMessage(data.message || 'Login failed', 'error');
            }

        } catch (error) {
            console.error('Login error:', error);
            this.showMessage('Connection error. Please try again.', 'error');
        } finally {
            // Reset loading state
            this.setLoadingState(false);
            btnText.style.display = 'inline';
            btnLoader.style.display = 'none';
        }
    }

    setLoadingState(loading) {
        const loginBtn = document.getElementById('loginBtn');
        const inputs = document.querySelectorAll('input');
        
        loginBtn.disabled = loading;
        inputs.forEach(input => input.disabled = loading);
    }

    showMessage(message, type) {
        const messageDiv = document.getElementById('message');
        messageDiv.textContent = message;
        messageDiv.className = `message ${type}`;
        
        // Auto-hide success messages after 3 seconds
        if (type === 'success') {
            setTimeout(() => {
                messageDiv.textContent = '';
                messageDiv.className = 'message';
            }, 3000);
        }
    }

    showSuccess(message) {
        const loginCard = document.querySelector('.login-card');
        const successPanel = document.getElementById('successPanel');
        const userInfo = document.getElementById('userInfo');

        // Hide login form
        loginCard.style.display = 'none';
        
        // Show success panel
        successPanel.style.display = 'block';
        
        // Update user info
        userInfo.innerHTML = `
            <p><strong>Username:</strong> ${this.currentUser.username}</p>
            <p><strong>Role:</strong> ${this.currentUser.role}</p>
            <p><strong>Token:</strong> ${this.currentUser.token.substring(0, 20)}...</p>
            <p><strong>Login Time:</strong> ${new Date().toLocaleString()}</p>
        `;

        this.showMessage(message, 'success');
    }

    checkExistingSession() {
        const session = localStorage.getItem('loginSession');
        if (session) {
            try {
                this.currentUser = JSON.parse(session);
                this.showSuccess('Welcome back!');
            } catch (error) {
                localStorage.removeItem('loginSession');
            }
        }
    }

    async checkHealth() {
        try {
            const response = await fetch(`${this.apiBase}/health`);
            const data = await response.json();
            console.log('Health check:', data);
            return data;
        } catch (error) {
            console.error('Health check failed:', error);
            return null;
        }
    }
}

// Utility functions
function fillCredentials(username, password) {
    document.getElementById('username').value = username;
    document.getElementById('password').value = password;
    
    // Add visual feedback
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
        input.style.background = '#e8f5e8';
        setTimeout(() => {
            input.style.background = '';
        }, 1000);
    });
}

function logout() {
    // Clear session
    localStorage.removeItem('loginSession');
    
    // Reset UI
    const loginCard = document.querySelector('.login-card');
    const successPanel = document.getElementById('successPanel');
    const messageDiv = document.getElementById('message');
    const form = document.getElementById('loginForm');
    
    loginCard.style.display = 'block';
    successPanel.style.display = 'none';
    messageDiv.textContent = '';
    messageDiv.className = 'message';
    form.reset();
    
    // Reset current user
    if (window.loginSystem) {
        window.loginSystem.currentUser = null;
    }
}

// API Testing functions
async function testAPI() {
    console.log('Testing API endpoints...');
    
    // Test health endpoint
    try {
        const healthResponse = await fetch('/api/health');
        const healthData = await healthResponse.json();
        console.log('Health check:', healthData);
    } catch (error) {
        console.error('Health check failed:', error);
    }
    
    // Test login endpoint with demo credentials
    try {
        const loginResponse = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: 'demo',
                password: 'demo'
            })
        });
        const loginData = await loginResponse.json();
        console.log('Demo login test:', loginData);
    } catch (error) {
        console.error('Login test failed:', error);
    }
}

// Initialize the login system when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    window.loginSystem = new LoginSystem();
    
    // Test API on load (for debugging)
    window.loginSystem.checkHealth().then(health => {
        if (health) {
            console.log('✅ Backend is running:', health);
        } else {
            console.log('❌ Backend connection failed');
        }
    });
    
    // Add keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Ctrl+L to focus username field
        if (e.ctrlKey && e.key === 'l') {
            e.preventDefault();
            document.getElementById('username').focus();
        }
        
        // Escape to logout
        if (e.key === 'Escape' && window.loginSystem.currentUser) {
            logout();
        }
    });
});

// Expose testAPI function globally for debugging
window.testAPI = testAPI;