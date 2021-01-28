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
    submit_response = requests.post('http://localhost:8080/process/submit', json=form_data, headers=headers)

    if submit_response.status_code != 200:
        print("ERROR!")
        print(submit_response)


########################### LOGIN
headers_writer = login('john.doe', '123')
headers_chief_editor = login('pera', '123')
headers_editor1 = login('ed1', '123')
headers_editor2 = login('ed2', '123')
headers_board_member1 = login('board.member', '123')
headers_board_member2 = login('member.board', '123')

########################## START PROCESS

start_process_response = requests.get('http://localhost:8080/book/plagiarism-start-process', headers=headers_writer)
process_id = start_process_response.text
print(f'Process started with ID: {process_id}')

########################## SEND PLAGIARISM FORM

task_id = get_form(process_id)
print(f'Plagiarism form - task ID: {task_id}')

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

############################ CHOOSE EDITORS FORM

task_id = get_form(process_id)
print(f'Choose editors form task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_editors",
            "fieldValue": ['10', '11']
        }
    ]
}

submit_form(form_data, headers_chief_editor)
print('Choose editors form submitted')

################################## GET FORM EDITORS COMMENT 1
task_id = get_form(process_id)
print(f'Get comment form task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_note",
            "fieldValue": "ovo je komentar od prvog editora"
        }
    ]
}

submit_form(form_data, headers_editor1)
print('Editors 1 notes form submitted')

################################ GET FORM EDITORS COMMENT 2
task_id = get_form(process_id)
print(f'Get comment form task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_note",
            "fieldValue": "ovo je komentar od drugog editora"
        }
    ]
}

submit_form(form_data, headers_editor2)
print('Editors 2 notes form submitted')

############################## BOARD MEMBERS VOTE 1

task_id = get_form(process_id)
print(f'Board members vote form - task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_decision",
            "fieldValue": "no"
        }
    ]
}

submit_form(form_data, headers_board_member1)
print('Board member 1 vote form is submitted')

############################### BOARD MEMBERS VOTE 2

task_id = get_form(process_id)
print(f'Board members vote form form - task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_decision",
            "fieldValue": "yes"
        }
    ]
}

submit_form(form_data, headers_board_member2)
print('Board member 2 vote form is submitted')
