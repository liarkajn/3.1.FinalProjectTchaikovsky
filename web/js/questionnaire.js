function isValid(event) {
    var element = event.currentTarget;
    if (!element.checkValidity()) {
        element.classList.remove("is-valid");
        element.classList.add("is-invalid");
    } else {
        element.classList.remove("is-invalid");
        element.classList.add("is-valid");
    }
}