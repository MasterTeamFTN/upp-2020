import requests

def login(username, password):
    login_data = {
        'username': username,
        'password': password
    }

    login_response = requests.post('http://localhost:8080/auth/login', json=login_data)
    jwt = login_response.json()['token']['accessToken']
    
    print('Login successfull')
    headers = {'Authorization': f'Bearer {jwt}' }
    
    return headers

# LOGIN
headers = login('john.doe', '123')

# START PROCESS

start_process_response = requests.get('http://localhost:8080/book/publish-start-process', headers=headers)
process_id = start_process_response.text
print(f'Process ID: {process_id}')

# GET INPUT FORM

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Input book form task ID: {input_book_task_id}')

# SUBMIT INPUT FORM

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_title",
            "fieldValue": "Ovo je moje ime knjige"
        },
        {
            "fieldId": "FormField_synopsis",
            "fieldValue": "Ovde treba da se nalazi kratak opis knjige koju sam napisao"
        },
        {
            "fieldId": "FormField_genre",
            "fieldValue": "1"
        }
    ]
}

submit_response = requests.post('http://localhost:8080/book/init-book-submit', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('Init book form is submitted')
    print('Chief editor should have received an email')
else:
    print(submit_response.json())

# REVIEWS BOOK TITLE AND SYNOPSIS

review_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
review_task_id = review_form_response.json()['taskId']
print(f'Form submit review for title and synopsis - task ID: {review_task_id}')


# LOGIN AS CHIEF EDITOR

headers = login('pera', '123')

print('Login as chief editor successfully')

# SUBMIT REVIEW FORM

form_data = {
    "taskId": f"{review_task_id}",
    "formData": [
        {
            "fieldId": "FormField_continueReview",
            "fieldValue": "true"
        },
        {
            "fieldId": "FormField_reason",
            "fieldValue": ""
        }
    ]
}

submit_response = requests.post('http://localhost:8080/book/first-review', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('Form for review title and synopsis is submitted')
else:
    print(submit_response.json())

# GET FORM FOR BOOK SUBMIT

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Input rest of book data form task ID: {input_book_task_id}')

# # SUBMIT FORM FOR REST OF THE DATA ABOUT BOOK

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_cowriters",
            "fieldValue": "Marko Markovic; Pera Peric"
        },
        {
            "fieldId": "FormField_keywords",
            "fieldValue": "kw1; kw2; kw3; kw4"
        },
        {
            "fieldId": "FormField_year",
            "fieldValue": "2020"
        },
        {
            "fieldId": "FormField_cityCountry",
            "fieldValue": "Novi Sad, Serbia"
        },
        {
            "fieldId": "FormField_numOfPages",
            "fieldValue": 143
        }
    ]
}

headers = login('john.doe', '123')

submit_response = requests.post('http://localhost:8080/book/submit-full-book', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('From for full book data is submitted')
else:
    print(submit_response.json())

## GET FORM - Check if book is plagiarized

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Check if book is plagiarized task ID: {input_book_task_id}')

## SUBMIT Check if book is plagiarized

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_isPlagiarised",
            "fieldValue": "false"
        },
        {
            "fieldId": "FormField_reason",
            "fieldValue": "Ovo je objasnjenje zasto je knjiga plagijat"
        }
    ]
}

headers = login('pera', '123')

submit_response = requests.post('http://localhost:8080/book/submit-plagiarism-form', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('From for full book data is submitted')
else:
    print(submit_response.json())


## GET FORM - Send to beta readers?

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Send to beta readers task ID: {input_book_task_id}')

## SUBMIT FORM - Send to beta readers

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_sendToBeta",
            "fieldValue": "true"
        }
    ]
}

headers = login('pera', '123')

submit_response = requests.post('http://localhost:8080/book/submit-send-to-beta', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('From send to beta readers submitted')
else:
    print(submit_response.json())

# GET FORM CHOOSE BETA READERS
input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Choose beta readers: {input_book_task_id}')

# SUBMIT CHOOSE BETA READERS

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_betaReaders",
            "fieldValue": [
                '3', '7'
            ]
        }
    ]
}

submit_response = requests.post('http://localhost:8080/book/submit-send-to-beta', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('From choose beta readers submitted')
else:
    print(submit_response.json())

# GET FORM TO SUBMIT COMMENT

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Get for for comment submit task ID: {input_book_task_id}')

## SUBMIT FORM - comment 

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "Ovo je neki moj komentar na tvoj rad od baby doe"
        }
    ]
}

headers = login('baby.doe', '123')

submit_response = requests.post('http://localhost:8080/book/submit-comment', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('Comment submitted')
else:
    print(submit_response.json())

# GET FORM TO SUBMIT COMMENT

input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Get for for comment submit task ID: {input_book_task_id}')

## SUBMIT FORM - comment 

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "Ovo je neki moj komentar na tvoj rad od baby2 doe"
        }
    ]
}

headers = login('baby.doe2', '123')

submit_response = requests.post('http://localhost:8080/book/submit-comment', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('Comment submitted')
else:
    print(submit_response.json())
