package pradio.ep.githubuser.ui.detail.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pradio.ep.githubuser.domain.model.UserFollower
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import pradio.ep.githubuser.databinding.FragmentFollowerBinding
import pradio.ep.githubuser.ui.detail.UserDetailActivity
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.toast
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class FollowerFragment : Fragment() {

    private val followerViewModel: FollowerViewModel by viewModels()

    private var lists = mutableListOf<UserFollower>()

    private val followerAdapter: FollowerAdapter by lazy {
        FollowerAdapter(requireContext())
    }

    private val binding: FragmentFollowerBinding by lazy {
        FragmentFollowerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUserName()
        initObserver()
        initRecyclerView()
    }

    private fun handleUserName() {
        val activity = activity as UserDetailActivity
        val username: String? = activity.getUsername()
        followerViewModel.getUserFollowers(username!!)
    }

    private fun initRecyclerView() {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followerAdapter
        }
    }

    private fun initObserver() {
        with(followerViewModel) {
            state.observe(viewLifecycleOwner) {
                handleStateLoading(it)
            }
            error.observe(this@FollowerFragment) {
                it?.let {
                    toast(it)
                }
            }
            resultUserFollower.observe(viewLifecycleOwner) {
                handleUserFollower(it)
            }
        }
    }

    private fun handleEmptyFollower(data: List<UserFollower>) {
        if (data.isEmpty()) {
            binding.apply {
                baseEmptyFollower.root.visible()
                rcView.gone()
            }
        } else {
            binding.apply {
                baseEmptyFollower.root.gone()
                rcView.visible()
            }
        }
    }

    private fun handleStateLoading(loading: LoadingState) {
        if (loading is LoadingState.Show) {
            binding.apply {
                baseLoader.root.visible()
                rcView.gone()
            }
        } else {
            binding.apply {
                baseLoader.root.gone()
                rcView.visible()
            }
        }
    }

    private fun handleUserFollower(data: List<UserFollower>) {
        handleEmptyFollower(data)
        lists.clear()
        lists.addAll(data)
        followerAdapter.setItems(data = lists)
    }

}