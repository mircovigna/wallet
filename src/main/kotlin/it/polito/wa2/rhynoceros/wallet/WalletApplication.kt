package it.polito.wa2.rhynoceros.wallet

import it.polito.wa2.rhynoceros.wallet.domain.Customer
import it.polito.wa2.rhynoceros.wallet.domain.Transaction
import it.polito.wa2.rhynoceros.wallet.domain.Wallet
import it.polito.wa2.rhynoceros.wallet.dto.TransactionDTO
import it.polito.wa2.rhynoceros.wallet.repositories.CustomerRepository
import it.polito.wa2.rhynoceros.wallet.repositories.TransactionRepository
import it.polito.wa2.rhynoceros.wallet.repositories.WalletRepository
import it.polito.wa2.rhynoceros.wallet.services.WalletServiceImpl
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SpringBootApplication
class WalletApplication{
    @Bean
    fun test(customerRepo: CustomerRepository,
             walletRepo: WalletRepository,
             transactionRepo: TransactionRepository): CommandLineRunner {
        return CommandLineRunner {
            val c1 = Customer(name = "mirco", surname = "vigna", email = "mirco@gmail.com", delivery_address = "roveda 29")
            val c2 = Customer(name = "andrea", surname = "vottero", email = "andrea@gmail.com", delivery_address = "roveda 29")
            val c3 = Customer(name = "martina", surname = "mancinelli", email = "martina@gmail.com", delivery_address = "roveda 29")
            val c4 = Customer(name = "irene", surname = "maldera", email = "irene@gmail.com", delivery_address = "roveda 29")
            customerRepo.save(c1)
            customerRepo.save(c2)
            customerRepo.save(c3)
            customerRepo.save(c4)
            val service = WalletServiceImpl(walletRepo, transactionRepo, customerRepo)
            println(service.addWalletToCustomer(1))
            println(service.addWalletToCustomer(1))
            println(service.addWalletToCustomer(2))
            val w1 = Wallet(current_amount = BigDecimal(100), owner = c3)
            walletRepo.save(w1)
            service.doTransaction(TransactionDTO(amount = BigDecimal(55), payer = 4, payee = 1,  date = Date()))
            service.doTransaction(TransactionDTO(amount = BigDecimal(15), payer = 1, payee = 2, date = Date()))
            service.doTransaction(TransactionDTO(amount = BigDecimal(5), payer = 2, payee = 3, date = Date()))
            service.doTransaction(TransactionDTO(amount = BigDecimal(10), payer = 1, payee = 4, date = Date()))
            service.doTransaction(TransactionDTO(amount = BigDecimal(20), payer = 4, payee = 2, date = Date()))
            println(service.getAllTransactions(4))
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY)
            transactionRepo.save(Transaction(
                amount = BigDecimal(17), payer = walletRepo.findById(4).get(),
                payee = walletRepo.findById(3).get(), date = formatter.parse("2021-03-12 12:00:00")))

            transactionRepo.save(Transaction(
                amount = BigDecimal(3), payer = walletRepo.findById(4).get(),
                payee = walletRepo.findById(3).get(), date = formatter.parse("2021-03-21 12:00:00")))

            transactionRepo.save(Transaction(
                amount = BigDecimal(2), payer = walletRepo.findById(3).get(),
                payee = walletRepo.findById(4).get(), date = formatter.parse("2021-03-15 12:00:00")))

            println(service.getTransactionRangeDate(4, formatter.parse("2021-03-13 01:00:00"), formatter.parse("2021-03-24 01:00:00")))

        }
    }
}



fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)

}
