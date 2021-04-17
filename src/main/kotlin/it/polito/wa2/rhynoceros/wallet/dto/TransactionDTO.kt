package it.polito.wa2.rhynoceros.wallet.dto

import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min

data class TransactionDTO (
    var payer: Long,
    var payee: Long,
    @field:Min(0, message = "Amount should always be positive")
    var amount: BigDecimal,
    var date: Date = Date() ) {}