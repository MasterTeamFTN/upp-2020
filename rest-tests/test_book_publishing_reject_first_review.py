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
            "fieldValue": "false"
        },
        {
            "fieldId": "FormField_reason",
            "fieldValue": "Razlog zasto ne prihvatam knjigu"
        }
    ]
}

submit_response = requests.post('http://localhost:8080/book/first-review', json=form_data, headers=headers)

if submit_response.status_code == 200:
    print('Form for review title and synopsis is submitted')
else:
    print(submit_response.json())

print("FINISHED")
