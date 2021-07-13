# Poster Generator (Group 7) - DSL

This Poster Generator is made for users who want to create a “Professional Like” poster with few images and simple text in a short time and only a couple of lines of code. However, it also provides additional tools for users to show off their talent.

This generator allows users to dynamically interact with their input images and texts, such as utilizing for-loops to create patterns, setting conditions with if-else statements, and even storing them as variables, being able to reuse them later.

A potential use-case would be for those who have little programming experience, yet have no ideas about photo-editing. Then this generator will provide a chance for them to create their own version of posters, by manipulating the images and texts they import. 

--------------------------------------------------------------------
### A simple version of how our DSL would look like:

Create Panel 1000, 5000  
Insert \download\pic.jpg Named pic At 30,50 With BlackWhite  
Create Text “this is for example” Named temp At 100, 900 With Arial  
Move temp to 500, 900  
Rotate temp 90 Degree  
Change Filter for pic With Grey  

-----------------------------------------------------------------------

Text = {content: “Hello”, loop={times: 3, dir: horizontal, shift: 2} // duplicate “Hello”  3 times and each “Hello” is horizontally shifted left.    
Line = {length: 20, loop={times: 360, dir: circle, shift: 1} // create 360 lines begin from the same point and each line aligns with a degree, therefore they form a rough circle.    
Loop{content = listImages, action = Rotate 90} // all the images in the list will be rotated for 90 degrees.   

-----------------------------------------------------------------------

“If{text = “Hello”, action = font -> “Arial”}

-----------------------------------------------------------------------
As we can see from the above examples, __our DSL focuses on the following 3 dynamic features__:

__Looping__: it allows users to use for-loops to make images move in certain patterns; or we can operate a sequence of actions on a list of images or texts with the for-loop.    
__Conditional statement__: users may use if-statements to set up conditions for certain events/ actions.   
__Variables__: users may store the images and texts into some variables, and so they can reuse it later, such as changing the filter of an image, etc.

-----------------------------------------------------------------------
### Additional Features:  
__Filtering__:   
We provide few simple filters options like black and white, more colourful (Higher 
RGB), and so on. When user importing image, they can choose to use filter or not, 
and choose which one, we can automatically set a filter on that image during the 
import process.
adding filters is not necessary when inserting the image.   
__Font__: 
user can use any of the fonts we have when creating texts; if not specified, a default font will be selected.

-----------------------------------------------------------------------
#### Basically how it works is:   
Create Panels with width and height.   
Insert Image or Text with filter or font (Which is memory secured)   
Dynamically interaction like using for loops to make images move in certain patterns   
Export anytime to see the current result  
Adjust the image and text   
Export the final version into multiple file formats like PDF, jpg …  

