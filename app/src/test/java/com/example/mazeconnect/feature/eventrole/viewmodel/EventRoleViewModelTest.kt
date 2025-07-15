package com.example.mazeconnect.feature.eventrole.viewmodel

import app.cash.turbine.test
import com.example.mazeconnect.feature.eventrole.domain.repository.RoleRepository
import com.google.firebase.auth.FirebaseAuth
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

    class EventRoleViewModelTest {
        @ExperimentalCoroutinesApi
        private lateinit var viewModel: EventRoleViewModel
        private lateinit var repository: RoleRepository

        @Before
        fun setup() {
            repository = mockk()
            viewModel = EventRoleViewModel(repository)
        }

        @Test
        fun `saveUserRole`() = runTest {
            // Given
            val fakeRole = "EventSeeker"
            val fakeUserId = "12345"
            mockkStatic(FirebaseAuth::class)
            every { FirebaseAuth.getInstance().currentUser?.uid } returns fakeUserId
            coEvery { repository.saveRole(fakeUserId, fakeRole) } just Runs

            // When
            viewModel.saveUserRole(mockk(relaxed = true), fakeRole)

            // Then
            viewModel.roleState.test {
                assertEquals(RoleState.Loading, awaitItem())
                assertEquals(RoleState.Success(fakeRole), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        @Test
        fun `saveUserRole emits Error when user is not authenticated`() = runTest {
            // Given
            mockkStatic(FirebaseAuth::class)
            every { FirebaseAuth.getInstance().currentUser?.uid } returns null

            // When
            viewModel.saveUserRole(mockk(), "EventSeeker")

            // Then
            assertEquals(RoleState.Error("User not authenticated"), viewModel.roleState.value)
        }

        @Test
        fun `saveUserRole emits Error when repository fails`() = runTest {
            val fakeRole = "EventOrganizer"
            val fakeUserId = "67890"
            mockkStatic(FirebaseAuth::class)
            every { FirebaseAuth.getInstance().currentUser?.uid } returns fakeUserId
            coEvery { repository.saveRole(fakeUserId, fakeRole) } throws Exception("Network error")

            viewModel.saveUserRole(mockk(relaxed = true), fakeRole)

            viewModel.roleState.test {
                assertEquals(RoleState.Loading, awaitItem())
                assertEquals(RoleState.Error("Network error"), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

