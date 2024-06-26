package pradio.ep.githubuser.ui.detail.follower

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pradio.ep.githubuser.R
import pradio.ep.githubuser.databinding.ItemRowUserBinding
import pradio.ep.githubuser.domain.model.UserFollower
import pradio.ep.githubuser.ui.detail.UserDetailActivity
import pradio.ep.githubuser.util.view.load

class FollowerAdapter(private val mContext: Context) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private var items = mutableListOf<UserFollower>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowUserBinding = ItemRowUserBinding.bind(itemView)

        fun bind(data: UserFollower) {
            binding.apply {
                ivUser.load(data.avatarUrl)
                txtUsername.text = data.login
            }
            with(itemView) {
                setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            UserDetailActivity::class.java
                        ).apply {
                            putExtra(UserDetailActivity.USERNAME_KEY, data.login)
                        })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_row_user, parent, false)
        )
    }

    fun setItems(data: MutableList<UserFollower>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}