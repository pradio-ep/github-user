package pradio.ep.githubuser.ui.detail.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pradio.ep.githubuser.domain.model.UserFollower
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pradio.ep.githubuser.domain.usecase.UserUseCase
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoadingState>()
    val state: LiveData<LoadingState> = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _resultUserFollower = MutableLiveData<List<UserFollower>>()
    val resultUserFollower: LiveData<List<UserFollower>> = _resultUserFollower

    fun getUserFollowers(username: String) {
        _state.value = LoadingState.Show
        viewModelScope.launch {
            userUseCase.getUserFollowers(username).collect {
                _state.value = LoadingState.Hide
                when (it) {
                    is ResultState.Success -> _resultUserFollower.postValue(it.data)
                    is ResultState.Error -> _error.postValue(it.error)
                }
            }
        }
    }
}