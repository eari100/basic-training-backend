package com.example.basictrainingbackend.service

import com.example.basictrainingbackend.domain.board.Todo
import com.example.basictrainingbackend.domain.board.TodoRepository
import com.example.basictrainingbackend.web.dto.todo.GetTodoResDto
import com.example.basictrainingbackend.web.dto.todo.SaveTodoReqDto
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {
    fun getTodos(): List<GetTodoResDto> {
        return todoRepository.findAll().map { GetTodoResDto(it) }
    }

    fun getTodoById(id: Long): GetTodoResDto? {
        val todo = todoRepository.findById(id).orElseThrow {
            NoSuchElementException("Todo not found with id: $id")
        }

        return GetTodoResDto(todo)
    }

    fun saveTodo(saveTodoReqDto: SaveTodoReqDto): Long {
        val todo = Todo(saveTodoReqDto.id, saveTodoReqDto.item)

        return todoRepository.save(todo).id
    }

    fun updateTodo(id: Long, updatedItem: String): Long {
        val todo = todoRepository.findById(id).orElseThrow {
            NoSuchElementException("Todo not found with id: $id")
        }

        return todo.id
    }

    fun deleteTodoById(id: Long) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Todo not found with id: $id")
        }
    }
}

