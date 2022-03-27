package ru.somarov.berte.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("berte_user") class User(@Id val id: UUID?, val email: String)