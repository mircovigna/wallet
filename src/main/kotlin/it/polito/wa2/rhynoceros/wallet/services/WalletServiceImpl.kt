package it.polito.wa2.rhynoceros.wallet.services

import it.polito.wa2.rhynoceros.wallet.domain.Customer
import it.polito.wa2.rhynoceros.wallet.domain.Transaction
import it.polito.wa2.rhynoceros.wallet.domain.Wallet
import it.polito.wa2.rhynoceros.wallet.dto.TransactionDTO
import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import it.polito.wa2.rhynoceros.wallet.repositories.CustomerRepository
import it.polito.wa2.rhynoceros.wallet.repositories.TransactionRepository
import it.polito.wa2.rhynoceros.wallet.repositories.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
@Transactional
class WalletServiceImpl(val walletRepo: WalletRepository, val transactionRepo: TransactionRepository, val customerRepo: CustomerRepository) : WalletService {
    override fun addWalletToCustomer(customerId: Long): WalletDTO? {
        if (customerRepo.existsById(customerId)) {
            val owner: Customer = customerRepo.findById(customerId).get()
            val wallet = walletRepo.save(Wallet(owner = owner))
            return wallet.toWalletDTO()
        } else {
            System.err.println("Customer does not exist")
            return null
        }
    }

    override fun getWallet(walletId: Long): WalletDTO? {
        if (walletRepo.existsById(walletId)){
            return walletRepo.findById(walletId).get().toWalletDTO()
        } else {
            System.err.println("Wallet does not exist")
            return null
        }
    }

    override fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO? {
        if (walletRepo.existsById(walletId)){
            if (transactionRepo.existsById(transactionId)){
                val transactionDTO = transactionRepo.findById(transactionId).get().toTransactionDTO()
                if (transactionDTO.payer==walletId || transactionDTO.payee==walletId) {
                    return transactionDTO
                } else System.err.println("Transaction and wallet are not related")
            } else System.err.println("Transaction does not exist")
        } else System.err.println("Wallet does not exist")
        return null
    }


    override fun getAllTransactions(walletId: Long): List<TransactionDTO> {
        if (walletRepo.existsById(walletId)){
            var transactions = transactionRepo.findAllByPayerOrPayeeOrderByDateDesc(walletRepo.findById(walletId).get(), walletRepo.findById(walletId).get())
            return transactions.map{ t -> t.toTransactionDTO()}
        } else {
            System.err.println("Wallet does not exist")
            return listOf<TransactionDTO>()
        }
    }

    override fun getTransactionRangeDate(walletId: Long, fromDate: Date, toDate: Date): List<TransactionDTO> {
        if  (walletRepo.existsById(walletId)) {
            val transactions = transactionRepo.findAllByOwnerRangeDate(
                walletId , fromDate, toDate)
            return transactions.map { t -> t.toTransactionDTO() }
        } else {
            System.err.println("Wallet does not exist")
            return listOf<TransactionDTO>()
        }
    }

    override fun doTransaction(transaction: TransactionDTO): Int {
        if (walletRepo.existsById(transaction.payer) && walletRepo.existsById(transaction.payee)) {
            val payer = walletRepo.findById(transaction.payer).get()
            val payee = walletRepo.findById(transaction.payee).get()
            if (payer.current_amount >= transaction.amount) {
                transactionRepo.save(Transaction(amount = transaction.amount, payer = payer, payee = payee, date = Date()))
                payer.current_amount -= transaction.amount
                payee.current_amount += transaction.amount
                walletRepo.save(payer)
                walletRepo.save(payee)
                return 1
            } else {
                System.err.println("Payer does not have enough money")
                return -1
            }

        } else {
            System.err.println("Wallet does not exist")
            return -1
        }
    }
}