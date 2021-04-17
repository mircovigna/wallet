package it.polito.wa2.rhynoceros.wallet.dto

import java.math.BigDecimal

data class WalletDTO (
    var wallet_id: Long,
    var current_amount: BigDecimal ) {}

//Should include also list of transactions??