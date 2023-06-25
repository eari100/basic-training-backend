package com.example.basictrainingbackend.web.controller

import com.example.basictrainingbackend.service.TodoService
import com.example.basictrainingbackend.web.dto.todo.GetTodoResDto
import com.example.basictrainingbackend.web.dto.todo.SaveTodoReqDto
import com.example.basictrainingbackend.web.dto.todo.UpdateTodoReqDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(TodoRestController::class)
class TodoRestControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var todoService: TodoService


    @Test
    fun `todo 리스트 조회`() {
        val todos = listOf(
            GetTodoResDto(1L, "칼바람 하기"),
            GetTodoResDto(2L, "롤토체스 하기"),
        )

        `when`(todoService.getTodos()).thenReturn(todos)

        mockMvc.perform(get("/v1/todos"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(todos.size))
            .andExpect(jsonPath("$[0].id").value(todos[0].id))
            .andExpect(jsonPath("$[0].item").value(todos[0].item))
            .andExpect(jsonPath("$[1].id").value(todos[1].id))
            .andExpect(jsonPath("$[1].item").value(todos[1].item))
    }

    @Test
    fun `id로 조회`() {
        val todo = GetTodoResDto(1L, "밥먹기")

        `when`(todoService.getTodoById(1L)).thenReturn(todo)

        mockMvc.perform(get("/v1/todos/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(todo.id))
            .andExpect(jsonPath("$.item").value(todo.item))
    }

    @Test
    fun `id 조회 시 404 반환`() {
        `when`(todoService.getTodoById(1L)).thenReturn(null)

        mockMvc.perform(get("/v1/todos/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `저장하기`() {
        val saveTodoReqDto = SaveTodoReqDto("할일 추가")

        `when`(todoService.saveTodo(saveTodoReqDto)).thenReturn(1L)

        mockMvc.perform(post("/v1/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveTodoReqDto))
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `수정하기`() {
        val updateTodoReqDto = UpdateTodoReqDto("공부하기")
        val updatedId = 1L

        `when`(todoService.updateTodo(updatedId, updateTodoReqDto.item)).thenReturn(updatedId)

        mockMvc.perform(patch("/v1/todos/{id}", updatedId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateTodoReqDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").value(updatedId))
    }

    @Test
    fun `삭제하기`() {
        val deletedId = 1L

        mockMvc.perform(delete("/v1/todos/{id}", deletedId))
            .andExpect(status().isNoContent)

        Mockito.verify(todoService).deleteTodoById(deletedId)
    }
}