package it.polito.wa2.rhynoceros.wallet.domain

import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import org.hibernate.validator.constraints.Range
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min

@Entity
@Table(name="wallets")
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var wallet_id: Long? = null,
    @field:Min(0, message = "Amount should always be positive")
    var current_amount: BigDecimal = BigDecimal(0.0),
    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "customer_id")
    var owner: Customer,
    @OneToMany(mappedBy = "payer", targetEntity = Transaction::class, fetch=FetchType.LAZY)
    var recharge_transactions: MutableList<Transaction> = mutableListOf<Transaction>(),
    @OneToMany(mappedBy = "payee", targetEntity = Transaction::class, fetch=FetchType.LAZY)
    var purchase_transactions: MutableList<Transaction> = mutableListOf<Transaction>()) {

    fun toWalletDTO() = WalletDTO(wallet_id!!, current_amount)
}