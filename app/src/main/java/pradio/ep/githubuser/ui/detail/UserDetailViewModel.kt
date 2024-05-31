package pradio.ep.githubuser.ui.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pradio.ep.githubuser.domain.model.UserDetail
import pradio.ep.githubuser.domain.model.UserFavorite
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

    private val _resultInsertUserToDb = MutableLiveData<Boolean>()
    val resultInsertUserDb : LiveData<Boolean> = _resultInsertUserToDb

    private val _resultDeleteFromDb = MutableLiveData<Boolean>()
    val resultDeleteFromDb : LiveData<Boolean> = _resultDeleteFromDb

    private val _state = MutableLiveData<LoadingState>()
    val state : LiveData<LoadingState> = _state

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserDetailFromApi(username: String) {
        _state.value = LoadingState.Show
        viewModelScope.launch {
            userUseCase.getUserDetail(username).collect {
                when(it) {
                    is ResultState.Success -> {
                        _userDetailResult.postValue(it.data)
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

    fun addUserToFavDB(userFavoriteEntity: UserFavorite) {
        viewModelScope.launch {
            try {
                userUseCase.addUserToFavDB(userFavoriteEntity)
                _resultInsertUserToDb.postValue(true)
            }catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

    fun deleteUserFromDb(userFavoriteEntity: UserFavorite) {
        viewModelScope.launch {
            try {
                userUseCase.deleteUserFromDb(userFavoriteEntity)
                _resultDeleteFromDb.postValue(true)
            }catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

    fun getFavUserByUsername(username: String) = userUseCase.getFavUserByUsername(username).asLiveData()
}