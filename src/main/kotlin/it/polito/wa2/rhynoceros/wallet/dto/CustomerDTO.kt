package it.polito.wa2.rhynoceros.wallet.dto

class CustomerDTO (
    var owner: String,
    var customer_id: Long,
    var email: String,
    var address: String ) {}

// Should include also a set of wallets??