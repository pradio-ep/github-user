package pradio.ep.githubuser.ui.detail.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pradio.ep.githubuser.databinding.FragmentRepoBinding
import pradio.ep.githubuser.domain.model.UserRepo
import pradio.ep.githubuser.ui.detail.UserDetailActivity
import pradio.ep.githubuser.util.state.LoadingState
import pradio.ep.githubuser.util.view.gone
import pradio.ep.githubuser.util.view.toast
import pradio.ep.githubuser.util.view.visible

@AndroidEntryPoint
class RepoFragment : Fragment() {

    private val repoViewModel : RepoViewModel by viewModels()

    private var lists = mutableListOf<UserRepo>()

    private val repoAdapter: RepoAdapter by lazy {
        RepoAdapter(requireContext())
    }

    private val binding : FragmentRepoBinding by lazy {
        FragmentRepoBinding.inflate(layoutInflater)
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
        username?.let { repoViewModel.getUserRepo(it) }
    }

    private fun initRecyclerView() {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = repoAdapter
        }
    }

    private fun initObserver() {
        with(repoViewModel) {
            state.observe(viewLifecycleOwner) {
                handleStateLoading(it)
            }
            error.observe(this@RepoFragment) {
                it?.let {
                    toast(it)
                }
            }
            resultUserRepo.observe(viewLifecycleOwner) {
                handleResultUserRepo(it)
            }
        }
    }

    private fun handlingEmptyRepo(data: List<UserRepo>){
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

    private fun handleResultUserRepo(data: List<UserRepo>) {
        handlingEmptyRepo(data)
        lists.clear()
        lists.addAll(data)
        repoAdapter.setItems(lists)
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