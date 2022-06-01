package com.example.amistadzadanie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.amistadzadanie.data.model.Route
import com.example.amistadzadanie.databinding.RowRouteBinding


class RouteAdapter(val clickCallback: (Route) -> Unit) :
    RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    inner class RouteViewHolder(val binding: RowRouteBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Route>() {
        override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var routes: MutableList<Route>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = routes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        return RouteViewHolder(
            RowRouteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.binding.apply {
            val route = routes[position]
            tvTitle.text = route.name
            tvDistance.text = route.distance.toString()
            ivPhoto.load("https://www.traseo.pl/media/photo/${route.id / 1000}/${route.id}/xxlarge.jpg")
            root.setOnClickListener {
                clickCallback(route)
            }

        }
    }
}