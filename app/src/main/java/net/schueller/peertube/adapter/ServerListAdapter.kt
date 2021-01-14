/*
 * Copyright (C) 2020 Stefan Schüller <sschueller@techdroid.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.schueller.peertube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.schueller.peertube.adapter.ServerListAdapter.ServerViewHolder
import net.schueller.peertube.database.Server
import net.schueller.peertube.databinding.RowServerAddressBookBinding
import net.schueller.peertube.utils.visibleIf

class ServerListAdapter(private val mServers: MutableList<Server>, private val onClick: (Server) -> Unit) : RecyclerView.Adapter<ServerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {

        val binding = RowServerAddressBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {

        holder.bind(mServers[position])

//
//        holder.itemView.setOnLongClickListener(v -> {
//            Log.v("ServerListAdapter", "setOnLongClickListener " + position);
//            return true;
//        });
    }

    fun setServers(servers: List<Server>) {
        mServers.clear()
        mServers.addAll(servers)

        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mServers has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return mServers.size
    }

    inner class ServerViewHolder (private val binding: RowServerAddressBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(server: Server) {

            binding.serverLabelRow.text = server.serverName
            binding.serverUrlRow.text = server.serverHost
            binding.sbRowHasLoginIcon.visibleIf { server.username.isNullOrBlank().not() }

            binding.root.setOnClickListener { onClick(server) }
        }
    }

    fun getServerAtPosition(position: Int): Server {
        return mServers[position]
    }
}