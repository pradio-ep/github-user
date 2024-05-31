package pradio.ep.githubuser.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pradio.ep.githubuser.databinding.ActivityMainBinding
import pradio.ep.githubuser.domain.model.UserSearch
import pradio.ep.githubuser.ui.favorite.FavoriteUserActivity
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.toast
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val items = mutableListOf<UserSearch>()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this)
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        searchUsers()
        initRecyclerView()
        initObserver()
    }

    private fun initToolbar() {
        supportActionBar?.hide()
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteUserActivity::class.java))
        }
    }

    private fun searchUsers() {
        binding.apply {
            binding.edtSearch.setOnKeyListener { _, key, event ->
                if (event.action == KeyEvent.ACTION_DOWN && key == KeyEvent.KEYCODE_ENTER) {
                    mainViewModel.getUser(binding.edtSearch.text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun initObserver() {
        with(mainViewModel) {
            state.observe(this@MainActivity) {
                it?.let {
                    handleStateLoading(it)
                }
            }
            userResult.observe(this@MainActivity) {
                it?.let {
                    handleUserFromApi(it)
                }
            }
            error.observe(this@MainActivity) {
                it?.let {
                    toast(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
        }
        mainAdapter.setActivity(this)
    }

    private fun handleUserFromApi(result: List<UserSearch>) {
        items.clear()
        items.addAll(result)
        mainAdapter.setItems(items)
    }

    private fun handleStateLoading(loading: LoadingState) {
        with(binding) {
            if (loading is LoadingState.Show) {
                baseLoading.root.visible()
                baseEmpty.root.gone()
                rvUser.gone()
            } else {
                baseLoading.root.gone()
                baseEmpty.root.gone()
                rvUser.visible()
            }
        }

    }
}