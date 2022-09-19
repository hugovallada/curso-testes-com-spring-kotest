package com.github.hugovallada.starwars.domain

import org.apache.commons.lang3.builder.EqualsBuilder
import javax.persistence.*

@Entity
@Table(name = "planets")
class Planet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val terrain: String,
    val climate: String
) {
    override fun equals(other: Any?) = EqualsBuilder.reflectionEquals(other, this)

}