
access_matrix = [ ["",       "Photo.png", "Readme.txt",   "Program.exe" ],
                  ["vilmar", "read",      "write",        ""],
                  ["maria",  "read",      "",             "execute"],
                  ["pedro",  "read",      "write",        "execute"]]

typed_user = "vilmar"
typed_action = "read"
typed_resource = "Photo.png"

access = False
for i in range(1, len(access_matrix)):
    matrix_user = access_matrix[i][0]
    if typed_user == matrix_user:
        # The user is valid
        for j in range(1, len(access_matrix[0])):
            matrix_resource = access_matrix[0][j]
            if typed_resource == matrix_resource:
                matrix_action = access_matrix[i][j]
                if typed_action == matrix_action:
                    access = True

if access:
    print("Access granted!")
else:
    print("Access denied!")
