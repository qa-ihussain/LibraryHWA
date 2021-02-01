'use strict';
const users = document.querySelector('#users');
const readUserBtn = document.querySelector('#readUser');
const createUserBtn = document.querySelector('#createUser');
const updateUserBtn = document.querySelector('#updateUser');
const deleteUserbtn = document.querySelector('#delUser');

const firstName = document.querySelector('#firstName');
const lastName = document.querySelector('#lastName');
const emailAddress = document.querySelector('#email');
const userName = document.querySelector('#userName');
const password = document.querySelector('#password');
const library = document.querySelector('#libraryId')

const userId = document.querySelector('#userId');
const updateFirstName = document.querySelector('#updateFirstName');
const updateLastName = document.querySelector('#updateLastName');
const updateEmailAddress = document.querySelector('#updateEmail');
const updateUserName = document.querySelector('#updateUserName');
const updatePassword = document.querySelector('#updatePassword');


const delUser = document.querySelector('#deleteUser');


const BASE_URL = "http://localhost:8081/users/readAll";
const POST_URL = "http://localhost:8081/users/createUser";


const retrieveTrackData = () => {
    fetch(BASE_URL)
        .then((response) => {
            console.log(response);
            if (response.status !== 200) {
                console.error('Looks like there was a problem. Status code: ' +
                    response.status);

                return;
            } else {
                response.json().then(json => {
                    console.log(json);

                    for (let i = 0; i < json.length; i++) {
                        let p = document.createElement("p");
                        p.setAttribute("class", "data");
                        let info = document.createTextNode(json[i].id + " " + json[i].firstName + " " + json[i].lastName + " " + json[i].emailAddress + " " + json[i].userName + " " + json[i].password);
                        p.appendChild(info);
                        tracks.appendChild(p);
                    }
                })
            }
        })
        .catch(err => console.error(`Stop! ${err}`));
}

const createUser = () => {
    console.log(JSON.stringify({
        firstName: firstName.value,
        lastName: lastName.value,
        emailAddress: emailAddress.value,
        userName: userName.value,
        password: password.value,
        library: { id: libraryId.value }
    }))
    fetch(POST_URL, {
            method: 'POST',
            body: JSON.stringify({
                firstName: firstName.value,
                lastName: lastName.value,
                emailAddress: emailAddress.value,
                userName: userName.value,
                password: password.value,
                library: { id: libraryId.value }
            }),
            headers: {
                'Content-Type': "application/json"
            }
        })
        .then(response => response.json())
        .then(json => console.log(json))
        .catch(err => console.error(`Something went wrong` + err));
}

createBtn.addEventListener('click', createUser);

const updateUser = () => {
    console.log(JSON.stringify({
        firstName: firstName.value,
        lastName: lastName.value,
        emailAddress: emailAddress.value,
        userName: userName.value,
        password: password.value,
        library: { id: libraryId.value }
    }))
    fetch("http://localhost:8081/users/updateUser/" + trackId.value, {
            method: 'PUT',
            body: JSON.stringify({
                trackArtist: updatedTrackArtist.value,
                firstName: firstName.value,
                lastName: lastName.value,
                emailAddress: emailAddress.value,
                userName: userName.value,
                password: password.value,
                library: { id: libraryId.value }
            }),
            headers: {
                'Content-Type': "application/json"
            }
        })
        .then(response => response.json())
        .then(json => console.log(json))
        .catch(err => console.error(`Something went wrong` + err))
        .catch(alert(`INCORRECT VALUE PASSED`));

}

const deleteUser = () => {
    fetch("http://localhost:8081/users/deleteUser/" + delUser.value, {
            method: 'DELETE',
            body: JSON.stringify({
                id: delUser.value
            }),
            headers: {
                'Content-Type': "application/json"
            }
        })
        .then(response => response.json())
        .then(json => console.log(json))
        .catch(err => console.error(`Something went wrong` + err));
}