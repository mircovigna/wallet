package it.polito.wa2.rhynoceros.wallet.repositories

import it.polito.wa2.rhynoceros.wallet.domain.Transaction
import it.polito.wa2.rhynoceros.wallet.domain.Wallet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TransactionRepository: CrudRepository<Transaction, Long> {
    fun findAllByPayerOrPayeeOrderByDateDesc (walletFrom: Wallet, walletTo: Wallet): List<Transaction>

    @Query(value = "SELECT * FROM transactions WHERE (payer_id=?1 OR payee_id=?1) AND date BETWEEN ?2 AND ?3",
            nativeQuery = true )
    fun findAllByOwnerRangeDate (owner: Long, fromDate: Date, toDate: Date): List<Transaction>
}