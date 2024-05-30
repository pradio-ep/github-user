package pradio.ep.githubuser.ui.detail.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pradio.ep.githubuser.domain.model.UserRepo
import pradio.ep.githubuser.domain.usecase.UserUseCase
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoadingState>()
    val state : LiveData<LoadingState> = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _resultUserRepo = MutableLiveData<List<UserRepo>>()
    val resultUserRepo : LiveData<List<UserRepo>> = _resultUserRepo

    fun getUserRepo(username: String) {
        _state.value = LoadingState.Show
        viewModelScope.launch {
            userUseCase.getUserRepo(username).collect {
                _state.value = LoadingState.Hide
                when (it) {
                    is ResultState.Success -> _resultUserRepo.postValue(it.data)
                    is ResultState.Error -> _error.postValue(it.error)
                }
            }
        }
    }

}