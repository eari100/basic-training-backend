package com.example.basictrainingbackend.domain.board

import org.junit.jupiter.api.Assertions.assertEquals
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
}