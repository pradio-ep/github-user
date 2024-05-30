package pradio.ep.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pradio.ep.githubuser.domain.model.UserSearch
import pradio.ep.githubuser.domain.usecase.UserUseCase
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _userResult = MutableLiveData<List<UserSearch>>()
    val userResult: LiveData<List<UserSearch>> = _userResult

    private val _state = MutableLiveData<LoadingState>()
    val state: LiveData<LoadingState> = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUser(query: String) {
        _state.value = LoadingState.Show
        viewModelScope.launch {
            userUseCase.getUser(query).collect {
                when (it) {
                    is ResultState.Success -> {
                        _userResult.postValue(it.data)
                        _state.value = LoadingState.Hide
                    }
                    is ResultState.Error -> {
                        _error.postValue(it.error)
                        _state.value = LoadingState.Hide
                    }
                }
            }
        }
    }
}