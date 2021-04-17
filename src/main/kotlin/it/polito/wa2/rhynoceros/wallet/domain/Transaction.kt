package it.polito.wa2.rhynoceros.wallet.domain

import it.polito.wa2.rhynoceros.wallet.dto.TransactionDTO
import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(name = "transactions")
class Transaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var transaction_id: Long? = null,
    @field:Min(0, message = "Amount should always be positive")
    var amount: BigDecimal,
    @Temporal(TemporalType.TIMESTAMP)
    var date: Date,
    @ManyToOne
    @JoinColumn(name = "payer_id", referencedColumnName = "wallet_id")
    var payer: Wallet,
    @ManyToOne
    @JoinColumn(name = "payee_id", referencedColumnName = "wallet_id")
    var payee: Wallet ) {

    fun toTransactionDTO() = TransactionDTO(payer = payer.wallet_id!!, payee = payee.wallet_id!!,
                                            amount = amount, date = date)
}
