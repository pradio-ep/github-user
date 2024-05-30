package pradio.ep.githubuser.ui.detail.repo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pradio.ep.githubuser.R
import pradio.ep.githubuser.databinding.ItemRowRepoBinding
import pradio.ep.githubuser.domain.model.UserRepo

class RepoAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private var items = mutableListOf<UserRepo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowRepoBinding = ItemRowRepoBinding.bind(itemView)

        fun bind(data: UserRepo) {
            binding.apply {
                txtName.text = data.name
                txtDescription.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_row_repo, parent, false)
        )
    }

    fun setItems(data: MutableList<UserRepo>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}