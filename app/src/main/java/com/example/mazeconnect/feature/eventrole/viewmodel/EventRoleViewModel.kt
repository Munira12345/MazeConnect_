package com.example.mazeconnect.feature.eventrole.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazeconnect.SharedPrefsUtils
import com.example.mazeconnect.feature.eventrole.domain.repository.RoleRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RoleState {
    object Idle : RoleState()
    object Loading : RoleState()
    data class Success(val role: String) : RoleState()
    data class Error(val message: String) : RoleState()
}

class EventRoleViewModel(
    private val repository: RoleRepository
) : ViewModel() {

    private val _roleState = MutableStateFlow<RoleState>(RoleState.Idle)
    val roleState: StateFlow<RoleState> = _roleState

    fun saveUserRole(context: Context, role: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            _roleState.value = RoleState.Error("User not authenticated")
            return
        }

        viewModelScope.launch {
            _roleState.value = RoleState.Loading
            try {
                repository.saveRole(userId, role)
                SharedPrefsUtils.saveRole(context, role)
                _roleState.value = RoleState.Success(role)
            } catch (e: Exception) {
                _roleState.value = RoleState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
