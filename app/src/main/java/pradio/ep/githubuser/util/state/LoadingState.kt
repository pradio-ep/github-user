package pradio.ep.githubuser.util.state

sealed class LoadingState {
    object Show: LoadingState()
    object Hide: LoadingState()
}