package com.example.basictrainingbackend.web.controller

import com.example.basictrainingbackend.service.TodoService
import com.example.basictrainingbackend.web.dto.todo.GetTodoResDto
import com.example.basictrainingbackend.web.dto.todo.SaveTodoReqDto
import com.example.basictrainingbackend.web.dto.todo.UpdateTodoReqDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/todos")
class TodoRestController(private val todoService: TodoService) {
    @GetMapping
    fun getTodos(): ResponseEntity<List<GetTodoResDto>> {
        val todos = todoService.getTodos()
        return ResponseEntity.ok(todos)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<GetTodoResDto> {
        val todo = todoService.getTodoById(id)

        return if (todo != null) {
            ResponseEntity.ok(todo)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun saveTodo(@RequestBody saveTodoReqDto: SaveTodoReqDto): ResponseEntity<Long> {
        val id = todoService.saveTodo(saveTodoReqDto)

        return ResponseEntity.ok(id)
    }

    @PatchMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody updateTodoReqDto: UpdateTodoReqDto): ResponseEntity<Long> {
        val id = todoService.updateTodo(id, updateTodoReqDto.item)

        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Unit> {
        todoService.deleteTodoById(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}