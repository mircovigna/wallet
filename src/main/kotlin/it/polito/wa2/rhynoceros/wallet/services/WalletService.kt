package it.polito.wa2.rhynoceros.wallet.services

import it.polito.wa2.rhynoceros.wallet.dto.TransactionDTO
import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import java.math.BigDecimal
import java.util.*

interface WalletService {
    fun addWalletToCustomer(customerId: Long): WalletDTO?
    fun getWallet(walletId: Long):  WalletDTO?
    fun doTransaction(transaction: TransactionDTO): Int
    fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO?
    fun getAllTransactions(walletId: Long): List<TransactionDTO>
    fun getTransactionRangeDate(walletId: Long, fromDate: Date, toDate: Date): List<TransactionDTO>
}