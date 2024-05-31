package pradio.ep.githubuser.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pradio.ep.githubuser.R
import pradio.ep.githubuser.databinding.ActivityUserDetailBinding
import pradio.ep.githubuser.domain.model.UserDetail
import pradio.ep.githubuser.domain.model.UserFavorite
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.load
import pradio.ep.githubuser.util.view.toast
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private val binding: ActivityUserDetailBinding by lazy {
        ActivityUserDetailBinding.inflate(layoutInflater)
    }

    private val userDetailViewModel: UserDetailViewModel by viewModels()

    private var userDetail: UserDetail? = null

    private var username: String? = null

    private var isFavoriteUser = false

    private var userFavorite: UserFavorite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleIntent()
        initObserver()
        fetchData()
        setupView()
    }

    fun getUsername(): String? {
        return username
    }

    private fun setupView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0f
            title = "$username\'s Profile"
        }
        binding.btnFavorite.setOnClickListener {
            if(isFavoriteUser) {
                userFavorite?.let {
                    userDetailViewModel.deleteUserFromDb(it)
                }
            } else {
                userDetail?.let {
                    UserFavorite.parse(it)
                }?.let {
                    userDetailViewModel.addUserToFavDB(it)
                }
            }
        }
        initPageAdapter()
    }

    private fun initPageAdapter() {
        val sectionPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun fetchData() {
        username?.let {
            userDetailViewModel.getUserDetailFromApi(it)
        }
    }

    private fun handleIntent() {
        username = intent.getStringExtra(USERNAME_KEY) as String
    }

    private fun initObserver() {
        with(userDetailViewModel) {
            state.observe(this@UserDetailActivity) {
                handleStateLoading(it)
            }
            userDetailResult.observe(this@UserDetailActivity) {
                handleResultUserDetail(it)
            }
            username?.let {
                getFavUserByUsername(it).observe(this@UserDetailActivity) {
                    handleUserDetailFromDb(it)
                }
            }
            resultInsertUserDb.observe(this@UserDetailActivity) { it ->
                if (it) {
                    username?.let {
                        userDetailViewModel.getFavUserByUsername(it)
                    }
                    toast(getString(R.string.success_add_favorite))
                }
            }
            resultDeleteFromDb.observe(this@UserDetailActivity) { it ->
                if (it) {
                    username?.let {
                        userDetailViewModel.getFavUserByUsername(it)
                    }
                    toast(getString(R.string.success_remove_favorite))
                }
            }
            error.observe(this@UserDetailActivity) {
                binding.btnFavorite.isEnabled = false
                toast(it)
            }
        }
    }

    private fun handleStateLoading(loading: LoadingState) {
        if (loading is LoadingState.Show) {
            binding.btnFavorite.gone()
        } else {
            binding.btnFavorite.visible()
        }
    }

    private fun handleResultUserDetail(data: UserDetail) {
        userDetail = data
        binding.apply {
            ivUser.load(data.avatarUrl)
            txtName.text = data.name ?: getString(R.string.no_name)
            txtBio.text = data.bio ?: getString(R.string.no_bio)
        }
    }

    private fun handleUserDetailFromDb(userFavorite: List<UserFavorite>) {
        if (userFavorite.isEmpty()) {
            isFavoriteUser = false
            binding.btnFavorite.setText(R.string.add_favorite)
        } else {
            this.userFavorite = userFavorite.first()
            isFavoriteUser = true
            binding.btnFavorite.setText(R.string.remove_favorite)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    companion object {
        const val USERNAME_KEY = "username_key"
    }
}