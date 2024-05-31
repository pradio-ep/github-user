package pradio.ep.githubuser.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pradio.ep.githubuser.R
import pradio.ep.githubuser.databinding.ActivityFavoriteUserBinding
import pradio.ep.githubuser.domain.model.UserFavorite
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class FavoriteUserActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteUserBinding by lazy {
        ActivityFavoriteUserBinding.inflate(layoutInflater)
    }

    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels()

    private val listUser = mutableListOf<UserFavorite>()

    private val favoriteUserAdapter: FavoriteUserAdapter by lazy {
        FavoriteUserAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
        initRecyclerView()
        initToolbar()
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0f
            title = getString(R.string.favorite_list)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initRecyclerView() {
        binding.rcUser.apply {
            layoutManager =
                LinearLayoutManager(this@FavoriteUserActivity, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteUserAdapter
        }
    }

    private fun initObserver() {
        favoriteUserViewModel.fetchAllUserFavorite.observe(this) {
            handleUserFromDb(it)
        }
    }

    override fun onRestart() {
        super.onRestart()
        favoriteUserViewModel.fetchAllUserFavorite
    }

    private fun handleUserFromDb(userEntity: List<UserFavorite>) {
        handleEmptyUser(userEntity)
        listUser.clear()
        listUser.addAll(userEntity)
        favoriteUserAdapter.setItems(listUser)
    }

    private fun handleEmptyUser(userEntity: List<UserFavorite>) {
        if (userEntity.isEmpty()) {
            binding.rcUser.gone()
            binding.baseEmpty.root.visible()
        } else {
            binding.rcUser.visible()
            binding.baseEmpty.root.gone()
        }
    }
}