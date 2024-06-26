package pradio.ep.githubuser.util.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import pradio.ep.githubuser.R

fun ImageView.load(imgResource : Any?) {
    Glide.with(context.applicationContext)
        .load(imgResource)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(getDrawableFactory()))
        .placeholder(R.drawable.ic_user)
        .into(this)
}

private fun getDrawableFactory() : DrawableCrossFadeFactory {
    return DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
}