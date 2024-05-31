package pradio.ep.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pradio.ep.githubuser.domain.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel@Inject constructor(
    userUseCase: UserUseCase
) : ViewModel() {
    val fetchAllUserFavorite = userUseCase.fetchAllUserFavorite().asLiveData()
}