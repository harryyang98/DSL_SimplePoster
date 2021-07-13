Milestone 3:
Logic of Design: 
Our language/ grammar design is based on the behaviours of people who might be trying to make posters online. Therefore, our grammar requires users to first create a panel, with its width and height specified. 
Then they will insert images by specifying the path, size of the images and whether to give variable names. Users are expected to (must) insert images before adding any texts. 
Any image insertions occurred after adding the first text would be considered illegal in our DSL. The reason we made this choice is that we want to make it more straight-forward to users in terms of debugging and viewing what they have implemented. 
With all images inserted in one place, and texts after, users may detect any potential issues more easily.

In terms of the syntax, we use “;” as a separator and force users to add this semicolon at the end of each instruction. This would be clear for users to view their code later and may improve the progress when debugging. 
In addition, all the keywords in our DSL are in capital letters. This allows users to clearly distinguish between fixed literals and user-defined types. 


EBNF Grammar:

Program ::= postPanel postImg* postText*

postPanel ::= “PANEL” “WIDTH:” Num “HEIGHT:” Num “VAR:” String

postImg ::= “IMG” “FILE_PATH:” String “POS:” “(“ Num “,” Num “)” “WIDTH:” Num “HEIGHT:” Num “VAR:” String

postText ::= “CONTENT:” String “POS:” “(“ Num “,” Num “)” “FONT_SIZE::” Num “VAR:” String

Num ::= [0-9]+
