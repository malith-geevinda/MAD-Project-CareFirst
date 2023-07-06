package com.example.firebasekotlin.Models

data class PaymentModel(
    var PaymentId:String? =null,
    var CardNumber:String? = null,
    var CardHolderName:String? = null,
    var ExpireMonth:String? = null,
    var ExpireYear:String? = null,
    var Cvv:String? = null
)