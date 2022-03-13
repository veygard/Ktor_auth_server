package com.veygard.auth.models.auth.database

data  class CheckOtpDbRespond(val phoneNumber:String, val otp:String, val expiredAt:String)