package pradio.ep.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pradio.ep.githubuser.R
import pradio.ep.githubuser.databinding.ItemRowFavoriteUserBinding
import pradio.ep.githubuser.domain.model.UserFavorite
import pradio.ep.githubuser.ui.detail.UserDetailActivity
import pradio.ep.githubuser.util.view.load

class FavoriteUserAdapter(private val mContext: Context) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private var items: MutableList<UserFavorite> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowFavoriteUserBinding = ItemRowFavoriteUserBinding.bind(itemView)

        fun bind(userEntity: UserFavorite) {
            with(itemView) {
                binding.apply {
                    ivUser.load(userEntity.avatarUrl)
                    txtUsername.text = userEntity.username
                    txtBio.text = userEntity.bio ?: context.getString(R.string.no_bio)
                }
                itemView.setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            UserDetailActivity::class.java
                        ).apply {
                            putExtra(UserDetailActivity.USERNAME_KEY, userEntity.username)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                }
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: MutableList<UserFavorite>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_row_favorite_user, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}