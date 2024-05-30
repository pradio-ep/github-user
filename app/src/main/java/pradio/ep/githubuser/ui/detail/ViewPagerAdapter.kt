package pradio.ep.githubuser.ui.detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pradio.ep.githubuser.R
import pradio.ep.githubuser.ui.detail.follower.FollowerFragment
import pradio.ep.githubuser.ui.detail.following.FollowingFragment
import pradio.ep.githubuser.ui.detail.repo.RepoFragment

class ViewPagerAdapter(
    private val mContext: Context,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.repo_title, R.string.follower_title, R.string.following_title)

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RepoFragment()
            1 -> FollowerFragment()
            2 -> FollowingFragment()
            else -> RepoFragment()
        }
    }

    override fun getCount(): Int = 3
}