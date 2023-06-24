package com.example.basictrainingbackend.web.dto.todo

import com.example.basictrainingbackend.domain.board.Todo

data class GetTodoResDto(
    val id: Long,
    val item: String
) {
    constructor(todo: Todo) : this(todo.id, todo.item)
}