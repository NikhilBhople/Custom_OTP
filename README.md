# Custom_OTP
Custom OTP view 

A simple solution for creating custom OTP component.
It will give you the solution for 
-  How to create custom OTP view 
-  When you have to show Number to user for perticular time
-  Autofill OTP when recuived by sms
-  You can decide if you want to show typed number or directly masked it

<a href="https://imgflip.com/gif/525u1i"><img src="https://imgflip.com/gif/525u1i.gif" title="When you have to show OTP before masking."/></a>
<a href="https://imgflip.com/gif/525v93"><img src="https://imgflip.com/gif/525v93.gif" title="Deletion for OTP."/></a>


If you don't want to show typed number of user then
Just refactor changeInputType method like below

 private fun changeInputType(otp: AppCompatEditText) {
     otp.transformationMethod = BiggerDotPasswordTransformationMethod
 }
 
 The result you can see it below
 <a href="https://imgflip.com/gif/525vy7"><img src="https://imgflip.com/gif/525vy7.gif" title="Masked OTP view"/></a>


