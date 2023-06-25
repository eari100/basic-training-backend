package com.example.basictrainingbackend.domain.board

import jakarta.persistence.*

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var item: String,
) {
    constructor() : this(0, "")

    fun update(item: String) {
        this.item = item
    }
}
