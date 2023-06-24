package com.example.basictrainingbackend.domain.board

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Test
    fun `Test save and findById`() {
        // Given
        val todo = Todo(0, "밥먹기")

        // When
        val savedTodo = todoRepository.save(todo)
        val foundTodo = todoRepository.findById(savedTodo.id).orElse(null)

        // Then
        assertEquals("밥먹기", foundTodo?.item)
    }

    @Test
    fun testUpdateTodo() {
        // Given
        val todo = Todo(0, "게임하기")

        // When
        val savedTodo = todoRepository.save(todo)
        val todoId = savedTodo.id
        val foundTodo = todoRepository.findById(todoId).orElse(null)
        foundTodo?.let {
            it.item = "공부하기"
            todoRepository.save(it)
        }

        // Then
        val updatedTodo = todoRepository.findById(todoId).orElse(null)
        assertEquals(updatedTodo?.item, "공부하기")
    }

    @Test
    fun testDeleteTodo() {
        // Given
        val todo = Todo(0, "숙제하기")
        val todoId = todo.id

        // When
        val savedTodo = todoRepository.save(todo)

        // When
        todoRepository.deleteById(savedTodo.id)

        // Then
        val deletedTodo = todoRepository.findById(todoId).orElse(null)
        assertNull(deletedTodo)
    }
}