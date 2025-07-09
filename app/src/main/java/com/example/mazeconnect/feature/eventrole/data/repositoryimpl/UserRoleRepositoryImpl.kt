package com.example.mazeconnect.feature.eventrole.data.repositoryimpl

import com.example.mazeconnect.feature.eventrole.domain.repository.RoleRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoleRepositoryImpl(
    private val firestore: FirebaseFirestore
) : RoleRepository {
    override suspend fun saveRole(userId: String, role: String) {
        val roleMap = mapOf("role" to role)
        firestore.collection("users").document(userId).set(roleMap).await()
    }
}
