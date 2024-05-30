package pradio.ep.githubuser.ui.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pradio.ep.githubuser.databinding.FragmentFollowingBinding
import pradio.ep.githubuser.domain.model.UserFollowing
import pradio.ep.githubuser.ui.detail.UserDetailActivity
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.toast
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val followingViewModel : FollowingViewModel by viewModels()

    private var lists = mutableListOf<UserFollowing>()

    private val followingAdapter: FollowingAdapter by lazy {
        FollowingAdapter(requireContext())
    }

    private val binding : FragmentFollowingBinding by lazy {
        FragmentFollowingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUserName()
        initObserver()
        initRecyclerView()
    }

    private fun handleUserName() {
        val activity = activity as UserDetailActivity
        val username : String? = activity.getUsername()
        username?.let { followingViewModel.getUserFollowing(it) }
    }

    private fun initRecyclerView() {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followingAdapter
        }
    }

    private fun initObserver() {
        with(followingViewModel) {
            state.observe(viewLifecycleOwner) {
                handleStateLoading(it)
            }
            error.observe(this@FollowingFragment) {
                it?.let {
                    toast(it)
                }
            }
            resultUserFollowing.observe(viewLifecycleOwner) {
                handleResultUserFollowing(it)
            }
        }
    }

    private fun handlingEmptyFollowing(data: List<UserFollowing>){
        if (data.isEmpty()) {
            binding.apply {
                baseEmptyFollowing.root.visible()
                rcView.gone()
            }
        } else {
            binding.apply {
                baseEmptyFollowing.root.gone()
                rcView.visible()
            }
        }
    }

    private fun handleResultUserFollowing(data: List<UserFollowing>) {
        handlingEmptyFollowing(data)
        lists.clear()
        lists.addAll(data)
        followingAdapter.setItems(lists)
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

}