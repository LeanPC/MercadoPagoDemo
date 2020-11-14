package com.example.leandropayments.domain

import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.networking.InteractorCardsIssuers
import com.example.leandropayments.data.networking.ResponseListener

class CardIssuerCase {
    fun getListCardsIssuers(listener: ResponseListener<CardIssuer>, methodId: String){
        InteractorCardsIssuers(listener).execute(methodId)
    }
}