// Scripts will be loaded and applied when the HTML pages are served by Spring Boot,
// allowing us to run client-side JavaScript.

// Get DOM elements
const form = document.getElementById('form');
const firstname_input = document.getElementById('firstname-input');
const lastname_input = document.getElementById('lastname-input');
const email_input = document.getElementById('email-input');
const password_input = document.getElementById('password-input');
const repeat_password_input = document.getElementById('repeat-password-input');
const phone_input = document.getElementById('phone-input');
const error_message = document.getElementById('error-message');
const fullname_input = document.getElementById('fullname-input');

// Handle form submission
form.addEventListener('submit', (e) => {
    let errors = [];

    // Check if we're on a signup form (has firstname input)
    if (firstname_input) {
        errors = getSignupFormErrors(
            firstname_input.value,
            lastname_input.value,
            email_input.value,
            password_input.value,
            repeat_password_input.value,
            phone_input.value
        );
    } else {
        // If no firstname input, we're in the login form
        errors = getLoginFormErrors(email_input.value, password_input.value);
    }

    // If there are errors, prevent form submission and show errors
    if (errors.length > 0) {
        e.preventDefault();
        error_message.innerText = errors.join(". ");
    }
});

// Signup validation
function getSignupFormErrors(firstname, lastname, email, password, repeatPassword, phone) {
    let errors = [];

    if (!firstname) {
        errors.push('First Name is required');
        firstname_input.parentElement.classList.add('incorrect');
    }
    if (!lastname) {
        errors.push('Last Name is required');
        lastname_input.parentElement.classList.add('incorrect');
    }
    if (!email) {
        errors.push('Email is required');
        email_input.parentElement.classList.add('incorrect');
    }
    if (!password) {
        errors.push('Password is required');
        password_input.parentElement.classList.add('incorrect');
    }
    if (password.length < 8) {
        errors.push('Password must have at least 8 characters');
        password_input.parentElement.classList.add('incorrect');
    }
    if (!validatePassword(password)) {
        errors.push('Password must have at least one uppercase, one lowercase, one number, and one special character');
        password_input.parentElement.classList.add('incorrect');
    }
    if (password !== repeatPassword) {
        errors.push('Password does not match repeated password');
        password_input.parentElement.classList.add('incorrect');
        repeat_password_input.parentElement.classList.add('incorrect');
    }
    if (!phone) {
        errors.push('Phone is required');
        phone_input.parentElement.classList.add('incorrect');
    }

    return errors;
}

// Password validation using regular expressions
function validatePassword(password) {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(password);
}

// Login validation
function getLoginFormErrors(email, password) {
    let errors = [];

    if (!email) {
        errors.push('Email is required');
        email_input.parentElement.classList.add('incorrect');
    }
    if (!password) {
        errors.push('Password is required');
        password_input.parentElement.classList.add('incorrect');
    }

    return errors;
}

// Remove "incorrect" class when the user starts typing
const allInputs = [firstname_input, lastname_input, email_input, password_input, repeat_password_input, phone_input, fullname_input].filter(input => input != null);

allInputs.forEach(input => {
    input.addEventListener('input', () => {
        if (input.parentElement.classList.contains('incorrect')) {
            input.parentElement.classList.remove('incorrect');
            error_message.innerText = '';
        }
    });
});

// Toggling a Navigation Menu (for mobile view/navigation, for example)
const menuButton = document.getElementById('menuButton');
const navMenu = document.getElementById('navMenu');

if (menuButton && navMenu) {
    menuButton.addEventListener('click', () => {
        navMenu.classList.toggle('active'); // Toggle visibility of the nav menu
    });
}

// Smooth Scrolling (for anchor links on the same page)
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// Displaying a Confirmation Popup (for example, on form submission)
const form = document.getElementById('form');
if (form) {
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        alert('Form has been submitted!');
        // Optionally, submit form after confirmation
        // form.submit(); // Uncomment to actually submit the form
    });
}

// Additional Features Examples

// Example of a button that shows a hidden section
const showButton = document.getElementById('showButton');
const hiddenSection = document.getElementById('hiddenSection');

if (showButton && hiddenSection) {
    showButton.addEventListener('click', () => {
        hiddenSection.classList.toggle('visible'); // Show or hide the section
    });

// Form validation
const form = document.getElementById('form');
form.addEventListener('submit', function (event) {
    const nameInput = document.querySelector('input[name="name"]');
    if (!nameInput.value) {
        alert('Name is required!');
        event.preventDefault(); // Prevent form submission if validation fails
    }
});

// Toggle dark mode / light mode
const toggleButton = document.getElementById('themeToggle');
toggleButton.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode'); // Add/remove dark mode class
});

// Show/Hide password
const passwordInput = document.getElementById('password');
const togglePasswordButton = document.getElementById('togglePassword');

togglePasswordButton.addEventListener('click', () => {
    // Toggle the type attribute of the input field between "password" and "text"
    const type = passwordInput.type === 'password' ? 'text' : 'password';
    passwordInput.type = type;
});

// Auto-Save form data
const form = document.getElementById('form');
const nameInput = document.querySelector('input[name="name"]');

// Load saved data from localStorage (if available)
if (localStorage.getItem('name')) {
    nameInput.value = localStorage.getItem('name');
}

// Save form data to localStorage whenever the user types
nameInput.addEventListener('input', () => {
    localStorage.setItem('name', nameInput.value);
});

// Show/Hide functionality  ?????? DO WE NEED THIS ?????? SEE LOGIN LINE 44
// Get the button and the section that will be shown/hidden
const showButton = document.getElementById('showButton');
const hiddenSection = document.getElementById('hiddenSection');

// Add an event listener for the button click  ?????? DO WE NEED THIS ?????? SEE LOGIN LINE 44
showButton.addEventListener('click', function() {

    // Toggle the visibility of the hidden section by adding/removing the 'hidden' class
    hiddenSection.classList.toggle('hidden');
});

// Show/Hide loading spinner
const loadingSpinner = document.getElementById('loadingSpinner');

function showLoading() {
    loadingSpinner.style.display = 'block'; // Show spinner
}

function hideLoading() {
    loadingSpinner.style.display = 'none'; // Hide spinner
}

// Example usage: Show loading spinner when fetching data
showLoading();
fetch('/some-api')
    .then(response => response.json())
    .then(data => {
        hideLoading();
        // Process the data
    })
    .catch(error => {
        hideLoading();
        alert('Error fetching data');
    });

// Toggle password visibility
document.getElementById('togglePassword').addEventListener('click', function () {
    const passwordField = document.getElementById('password');
    const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordField.setAttribute('type', type);
});

// Example event date (Can set this dynamically or from the server)
// Set the date being counted down
const eventDate = new Date('2025-01-10T12:00:00').getTime(); // Set the event date and time

// Update the countdown every second
const countdown = document.getElementById('countdown');

function updateCountdown() {
    const now = new Date().getTime();
    const timeLeft = eventDate - now;

    // Calculate days, hours, minutes, seconds left
    const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

    // Display the result
    countdown.innerHTML = `${days}d ${hours}h ${minutes}m ${seconds}s`;

    // If the countdown is over
    if (timeLeft < 0) {
        clearInterval(interval);
        countdown.innerHTML = "The event has started!";
    }
}

// Update the countdown every 1 second
const interval = setInterval(updateCountdown, 1000);

}

/* AJAX Requests: If you need to make API calls or fetch data asynchronously
from your Spring Boot backend (using fetch or XMLHttpRequest),
you would put those in scripts.js. For instance,
if your backend exposes an endpoint for user profiles,
you might fetch that data using JavaScript. */

fetch('/api/user-profile')
    .then(response => response.json())
    .then(data => {
        // Populate profile info dynamically on the page
        document.getElementById('profileName').textContent = data.name;
    })
    .catch(error => console.error('Error fetching data:', error));


/* Example of submitting a form via AJAX (signup form)
Handling Form Submissions with AJAX: If we want to submit forms via AJAX
(to send data to Spring Boot without reloading the page),
the script that sends the form data asynchronously will be added to scripts.js. */

const signupForm = document.getElementById('signupForm');
signupForm.addEventListener('submit', function(e) {
    e.preventDefault();

    const formData = new FormData(signupForm);

    fetch('/api/signup', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Account created!');
        } else {
            alert('There was an error.');
        }
    })
    .catch(error => console.error('Error:', error));
});
