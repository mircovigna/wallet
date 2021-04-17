package it.polito.wa2.rhynoceros.wallet.controllers

import it.polito.wa2.rhynoceros.wallet.dto.TransactionDTO
import it.polito.wa2.rhynoceros.wallet.dto.WalletDTO
import it.polito.wa2.rhynoceros.wallet.services.WalletService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.AbstractBindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/wallet")
class WalletController (val walletService: WalletService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addWallet(customerId: Long): WalletDTO? =
        walletService.addWalletToCustomer(customerId)


    @GetMapping("/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    fun getWallet(@PathVariable("walletId") walletId: Long): WalletDTO? =
        walletService.getWallet(walletId)

    @PostMapping("/{walletId}/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    fun doTransaction(@RequestBody @Valid transaction: TransactionDTO?, bindingResult: AbstractBindingResult): Int =
        if (transaction!=null){
            //TODO: check if walletId is the same for transaction.payer
            walletService.doTransaction(transaction)
        } else -1

    @GetMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.OK)
    fun getTransactionsRangeDate(@PathVariable("walletId") walletId: Long,
                                 @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") from: Date,
                                 @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") to:  Date): List<TransactionDTO> =
        walletService.getTransactionRangeDate(walletId, from, to)

    /*@GetMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.OK)
    fun getAllTransactions(@PathVariable("walletId") walletId: Long):List<TransactionDTO> =
        walletService.getAllTransactions(walletId)*/

    @GetMapping( "/{walletId}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTransaction(@PathVariable("walletId") walletId: Long,
                        @PathVariable("transactionId")  transactionId: Long): TransactionDTO? =
        walletService.getTransaction(walletId, transactionId)

}