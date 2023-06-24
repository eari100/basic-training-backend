package com.example.basictrainingbackend.domain.board

import jakarta.persistence.*

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val item: String,
) {
    constructor() : this(0, "")
}
