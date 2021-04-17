package it.polito.wa2.rhynoceros.wallet.domain

import it.polito.wa2.rhynoceros.wallet.dto.CustomerDTO
import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import javax.persistence.*

@Entity
@Table(name = "customers")
class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var customer_id: Long?  = null,
    @Column(unique = true)
    var email: String,
    var name: String,
    var surname: String,
    var delivery_address: String,
    @OneToMany(mappedBy = "owner", targetEntity = Wallet::class)
    var wallets: MutableSet<Wallet>? = null ){

    fun toCustomerDTO() = CustomerDTO(owner = "$name $surname", email = email,
                                    address = delivery_address, customer_id = customer_id!!)
}