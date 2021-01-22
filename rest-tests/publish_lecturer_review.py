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
        print(submit_response.json())


########################### LOGIN
headers_writer = login('john.doe', '123')
headers_chief_editor = login('pera', '123')
headers_lecturer = login('jane.doe', '123')
headers_betareader1 = login('baby.doe', '123')
headers_betareader2 = login('baby.doe2', '123')

########################## START PROCESS

start_process_response = requests.get('http://localhost:8080/book/publish-start-process', headers=headers_writer)
process_id = start_process_response.text
print(f'Process started with ID: {process_id}')

########################## GET INPUT FORM

task_id = get_form(process_id)
print(f'Input book form - task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
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

submit_form(form_data, headers_writer)
print('Init book form is submitted')
print('\tChief editor should have received an email')

#####################  REVIEWS BOOK TITLE AND SYNOPSIS

task_id = get_form(process_id)
print(f'Form submit review for title and synopsis - task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
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

submit_form(form_data, headers_chief_editor) 
print('Form for review title and synopsis is submitted')

#################### GET FORM FOR BOOK SUBMIT

task_id = get_form(process_id)
print(f'Input rest of book data form task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
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

submit_form(form_data, headers_writer)
print('From for full book data is submitted')

################### SEND PDF

task_id = get_form(process_id)
print(f'Pdf file form task ID: {task_id}')

fin = open('example.pdf', 'rb')
files = {
    'file': fin
}

try:
  r = requests.post(f'http://localhost:8080/api/file/book?taskId={task_id}', files=files, headers=headers_writer)
finally:
	fin.close()

print('Sent pdf file')


# .w.s.m.s.DefaultHandlerExceptionResolver : 
# Resolved [org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException: 
# Failed to convert value of type 'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile' 
# to required type 'java.lang.String'; nested exception is java.lang.IllegalStateException: 
# Cannot convert value of type 'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile' 
# to required type 'java.lang.String': no matching editors or conversion strategy found]


#################### GET FORM - Check if book is plagiarized

task_id = get_form(process_id)
print(f'Check if book is plagiarized task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
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

submit_form(form_data, headers_chief_editor)
print('From for full book data is submitted')

##################### GET FORM - Send to beta readers?

task_id = get_form(process_id)
print(f'Send to beta readers task ID: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_sendToBeta",
            "fieldValue": "true"
        }
    ]
}

submit_form(form_data, headers_chief_editor)
print('From for full book data is submitted')

##################### Choose beta readers

task_id = get_form(process_id)
print(f'Choose beta readers task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_betaReaders",
            "fieldValue": [
                '3', '7'
            ]
        }
    ]
}

submit_form(form_data, headers_chief_editor)
print('Form choose beta readers submitted')

##################### BETA READER 1 SUBMIT

task_id = get_form(process_id)
print(f'Get form for beta readers comments submit: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "Ovo je neki moj komentar na tvoj rad od baby doe"
        }
    ]
}

submit_form(form_data, headers_betareader1)
print('Beta reader baby.doe submitted comments')

###################### BETA READER 2 SUBMIT

task_id = get_form(process_id)
print(f'Get form for beta readers comments submit: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "Ovo je neki moj komentar na tvoj rad od baby doe2"
        }
    ]
}

submit_form(form_data, headers_betareader2)
print('Beta reader baby.doe2 submitted comments')

###################### USER WANTS TO MAKE CHANGES ?

task_id = get_form(process_id)
print(f'Get form for user want to make changes task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_makeChanges",
            "fieldValue": "false"
        }
    ]
}

submit_form(form_data, headers_writer)
print('User want to make changes form submitted')

##################### LECTURER REVIEW

task_id = get_form(process_id)
print(f'Lecturer review task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "ovo je komentar od lecturera na tvoju knjigu"
        },
        {
            "fieldId": "FormField_moreChanges",
            "fieldValue": "false"
        }
    ]
}

submit_form(form_data, headers_lecturer)
print('Form for lecturers review is submitted')

####################### Chief editor review

task_id = get_form(process_id)
print(f'Chief editor review task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_comment",
            "fieldValue": "ovo je komentar od chief editora na tvoju knjigu"
        },
        {
            "fieldId": "FormField_moreChanges",
            "fieldValue": "true"
        }
    ]
}

submit_form(form_data, headers_chief_editor)
print('Form for chief editor review is submitted')

#################### SEND NEW VERSION

import sys
sys.exit()

task_id = get_form(process_id)
print(f'Pdf file form task ID: {task_id}')

fin = open('files/example.pdf', 'rb')
files = {
    'file': fin
}

try:
  r = requests.post(f'http://localhost:8080/api/file/book?taskId={task_id}', files=files, headers=headers_writer)
finally:
	fin.close()

print('Sent pdf file')

########################## CHIEF EDITOR - NO MORE CHANGES
task_id = get_form(process_id)
print(f'Chief editor review task id: {task_id}')

form_data = {
    "taskId": f"{task_id}",
    "formData": [
        {
            "fieldId": "FormField_moreChanges",
            "fieldValue": "false"
        }
    ]
}

submit_form(form_data, headers_chief_editor)
print('Form for chief editor review is submitted')
