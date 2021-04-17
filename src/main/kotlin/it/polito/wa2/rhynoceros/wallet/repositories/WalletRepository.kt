package it.polito.wa2.rhynoceros.wallet.repositories

import it.polito.wa2.rhynoceros.wallet.domain.Wallet
import org.springframework.data.repository.CrudRepository

interface WalletRepository: CrudRepository<Wallet, Long> {
}