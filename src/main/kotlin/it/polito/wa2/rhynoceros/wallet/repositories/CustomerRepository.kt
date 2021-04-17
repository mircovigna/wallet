package it.polito.wa2.rhynoceros.wallet.repositories

import it.polito.wa2.rhynoceros.wallet.domain.Customer
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CustomerRepository: CrudRepository<Customer, Long> {
}