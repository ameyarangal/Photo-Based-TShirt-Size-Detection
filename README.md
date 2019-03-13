# Photo-Based-TShirt-Size-Detection
Application for detecting T-Shirt size based on uploaded image

## To run the application, follow below steps:

1) Install the application from the provided FindMyTshirtSize-app-debug.apk

2) Download test Images from "https://iu.box.com/s/z5ul1eygjv2v7abvy83jynnh5bym4loy"

3) Image have naming convention 'Size_{OriginalSize}_predicted_{PredictedSize}_{NumberIfSizeRepeated}.jpg'

3) Click on Get Image button on the home screen of the application.

4) Select one of the image from the recently downloaded images.

5) Click on Upload Image button.

6) Check the output.

## To run specific part of the code:

### Backend code

Folder structure:

main.py ---> Code to expose the get size function as API using Flask.
tshirt_size.py ----> Contains code to give measurement of the input image.
Folder also contains sample input images.

In order to run the backend code alone as a function in local, follow below steps:

Install following packages using pip:
Flask==0.12.1
opencv-python==3.4.0.12
numpy==1.14.0
imutils==0.4.6
scipy==1.0.0
Werkzeug==0.14.1
matplotlib==2.1.2


To test image follow below steps:

1) Add an Image which needs to be tested in the same directory which contains 'tshirt_size.py'

2) For example we have added an image 'Size_L_predicted_L.jpg' for testing purpose.

3) Edit 'tshirt_size.py' at line number 396 to give the same image name that needs to be tested.

4) Make 'debug' flag= False, if you don't want to see the interim images. This is the default behavior.

5) If 'debug'==True, then interim images can be seen in a python window and press Enter to continue. Press Enter on the window 2 times to see the output.

6) Run tshirt_size.py python file in terminal.

7) As an Output of our program you will see "Input Image size is 'S','M','L','XL','XXL', or 'XXXL'" depending on the input image at the last line of the output.

8) We have provided test images in the same folder. Names of these images is in following naming convention 'Size_{OriginalSize}_predicted_{PredictedSize}_{NumberIfSizeRepeated}.jpg'

### Android Application

Folder Structure:
App --> super folder that contains all the required android code.
src --> Source code.
	main --> Contains developer source code.
		res --> contains activity layouts.
	java --> packages
	test --> test framework, but this is not being used currently.
Gradle --> scripts to build application


To run the android application from the zip folder:

1) Open the 'AndroidApplication' folder in Android studio.

2) Build the project

3) This will create an app-debug.apk file under directory '~/TshirtSize/app/build/outputs/apk/debug'

4) If the device is connected to the system hit run which will install the application on phone.

C) To run the application from the provided FindMyTshirtSize-app-debug.apk file follow below steps:
In Phone install the FindMyTshirtSize-app-debug.apk file directly.


## Code Contribution:

Android code was contributed by Ninaad Joshi.





