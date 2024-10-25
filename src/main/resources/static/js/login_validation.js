const form = document.querySelector('form');
const errorText = document.querySelector('#error-text');
const errorBlock = document.querySelector('.error');
const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');

function hideError() {
    errorBlock.style.display = 'none';
}

function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function validatePassword(password) {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(password);
}


if (form) {
    form.addEventListener('submit', (event) => {
        event.preventDefault();

        errorBlock.style.display = 'none';

        if (!validateEmail(emailInput.value)) {
            errorText.textContent = 'Введіть коректний email';
            errorBlock.style.display = 'flex';
            return;
        }

        if (!validatePassword(passwordInput.value)) {
            errorText.textContent = 'Пароль повинен бути більше 8 символів, містити одну велику літеру, одну цифру та спеціальний символ ';
            errorBlock.style.display = 'flex';
            return;
        }

        form.submit();
    });
}