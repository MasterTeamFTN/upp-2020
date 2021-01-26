import requests

def login(username, password):
    login_data = {
        'username': username,
        'password': password
    }

    login_response = requests.post('http://localhost:8080/auth/login', json=login_data)
    jwt = login_response.json()['token']['accessToken']
    
    print(f'Login successfull as {username}')
    headers = {'Authorization': f'Bearer {jwt}' }
    
    return headers

def get_form(process_id):
    form = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
    return form.json()['taskId']

def submit_form(form_data, headers):
    submit_response = requests.post('http://localhost:8080/process/public/submit', json=form_data, headers=headers)

    if submit_response.status_code != 200:
        print("ERROR!")
        print(submit_response.json())


########################### LOGIN
headers_writer = login('john.doe', '123')
headers_chief_editor = login('pera', '123')
headers_lecturer = login('jane.doe', '123')

########################## START PROCESS

start_process_response = requests.get('http://localhost:8080/book/plagiarism-start-process', headers=headers_writer)
process_id = start_process_response.text
print(f'Process started with ID: {process_id}')

########################## GET INPUT FORM

task_id = get_form(process_id)
print(f'Plagiarism form - task ID: {task_id}')
print(task_id)
form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_originalBook",
            "fieldValue": "1"
        },
        {
            "fieldId": "FormField_plagiarismBook",
            "fieldValue": "2"
        }
    ]
}

submit_form(form_data, headers_writer)
print('Init plagiarism form is submitted')
print('\tChief editor should have received an email')

# GET FORM CHOOSE EDITORS
input_book_form_response = requests.get(f'http://localhost:8080/process/public/form/{process_id}')
input_book_task_id = input_book_form_response.json()['taskId']
print(f'Choose editors: {input_book_task_id}')

# SUBMIT CHOOSE EDITORS

form_data = {
    "taskId": f"{input_book_task_id}",
    "formData": [
        {
            "fieldId": "FormField_editors",
            "fieldValue": [
                '10', '11','12'
            ]
        }
    ]
}

submit_response = requests.post('http://localhost:8080/book/submit-editors', json=form_data, headers=headers_chief_editor)

if submit_response.status_code == 200:
    print('From choose beta readers submitted')
else:
    print(submit_response.json())