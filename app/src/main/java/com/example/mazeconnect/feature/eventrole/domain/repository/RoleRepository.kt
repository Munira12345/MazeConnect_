package com.example.mazeconnect.feature.eventrole.domain.repository

interface RoleRepository {
    suspend fun saveRole(userId: String, role: String)
}
