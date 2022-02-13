package ru.somarov.berte.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

// TODO: cannot save new entioty with passed UUID
@Table("berte_user")
class User(@Id val id: UUID, val email: String)