const form = document.getElementById('form')
const firstname_input = document.getElementById('firstname-input')
const lastname_input = document.getElementById('lastname-input')
const email_input = document.getElementById('email-input')
const password_input = document.getElementById('password-input')
const repeat_password_input = document.getElementById('repeat-password-input')
const phone_input = document.getElementById('phone-input')
const error_message = document.getElementById('error-message')
const fullname_input = document.getElementById('fullname-input')

form.addEventListener('submit', (e) => {

    let errors = []

    if(firstname_input){
        // If we have a firstname input then we are in the signup
        errors = getSignupFormErrors(firstname_input.value, lastname_input.value, email_input.value, password_input.value, repeat_password_input.value, phone_input.value)
    }
    else{
        // If we don't have a firstname input then we are in the login
        errors = getLoginFormErrors(email_input.value, password_input.value)
    }

    if(errors.length > 0){
        // If there are any errors in the array, then we want to prevent the submitting of the form; so we call the e.preventDefault() method
        e.preventDefault()
        error_message.innerText = errors.join(". ")
    }
})

function getSignupFormErrors(firstname, lastname, email, password, repeatPassword, phone) {
    let errors = [] // Empty array for errors to collect all the errors that occur and need to be fixed

    if(firstname === '' || firstname == null){ // If click submit and nothing entered, then push below message into errors array
        errors.push('First Name is required') // Take the errors array; and push message 'First Name is required'
        firstname_input.parentElement.classList.add('incorrect') // first name input has a parent element which is the div where the class needs to be where we use class list. Add incorrect as we want to make every input element required
    }
    if(lastname === '' || lastname == null){
        errors.push('Last Name is required')
        lastname_input.parentElement.classList.add('incorrect')
    }
    if(email === '' || email == null){
        errors.push('Email is required')
        email_input.parentElement.classList.add('incorrect')
    }
    if(password === '' || password == null){
        errors.push('Password is required')
        password_input.parentElement.classList.add('incorrect')
    }
    if(password.length < 8){
        errors.push('Password must have at least 8 characters')
        password_input.parentElement.classList.add('incorrect')
    }
    if(!validatePassword(password)){
        errors.push('Password must have at least one uppercase, one lowercase, one number, and one special character')
        password_input.parentElement.classList.add('incorrect')
    }

    if(password !== repeatPassword){
        errors.push('Password does not match repeated password')
        password_input.parentElement.classList.add('incorrect')
        repeat_password_input.parentElement.classList.add('incorrect')
    }
    if(phone === '' || phone == null){
        errors.push('Phone is required')
        phone_input.parentElement.classList.add('incorrect')
    }
    return errors;
}

function validatePassword(password) {

    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    return regex.test(password);

  }

function getLoginFormErrors(email, password){
    let errors = []

    if(email === '' || email == null){
        errors.push('Email is required')
        email_input.parentElement.classList.add('incorrect')
    }
    if(password === '' || password == null){
        errors.push('Password is required')
        password_input.parentElement.classList.add('incorrect')
    }
    return errors;
}
const allInputs = [firstname_input, lastname_input, email_input, password_input, repeat_password_input, phone_input, fullname_input].filter(input => input != null)

allInputs.forEach(input => {
    input.addEventListener('input', () => {
        if(input.parentElement.classList.contains('incorrect')){
            input.parentElement.classList.remove('incorrect')
            error_message.innerText = ''
        }
    })
})
