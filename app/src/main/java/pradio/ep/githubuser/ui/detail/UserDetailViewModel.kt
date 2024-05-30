package pradio.ep.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pradio.ep.githubuser.domain.model.UserDetail
import pradio.ep.githubuser.domain.usecase.UserUseCase
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userDetailResult = MutableLiveData<UserDetail>()
    val userDetailResult : LiveData<UserDetail> = _userDetailResult

    private val _state = MutableLiveData<LoadingState>()
    val state : LiveData<LoadingState> = _state

    private val _error = MutableLiveData<String>()


    fun getUserDetailFromApi(username: String) {
        _state.value = LoadingState.Show
        viewModelScope.launch {
            userUseCase.getUserDetail(username).collect {
                _state.value = LoadingState.Hide
                when(it) {
                    is ResultState.Success -> _userDetailResult.postValue(it.data)
                    is ResultState.Error -> _error.postValue(it.error)
                }
            }

        }
    }
}