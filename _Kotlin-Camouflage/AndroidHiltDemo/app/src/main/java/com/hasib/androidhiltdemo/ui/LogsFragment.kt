package com.hasib.androidhiltdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hasib.androidhiltdemo.data.Log
import com.hasib.androidhiltdemo.data.LoggerLocalDataSource
import com.hasib.androidhiltdemo.databinding.FragmentLogsBinding
import com.hasib.androidhiltdemo.databinding.TextRowItemBinding
import com.hasib.androidhiltdemo.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */
@AndroidEntryPoint
class LogsFragment : Fragment() {

    @Inject
    lateinit var logger: LoggerLocalDataSource
    @Inject
    lateinit var dateFormatter: DateFormatter

    private lateinit var binding: FragmentLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()

        logger.getAllLogs {
            binding.recyclerView.adapter = LogsViewAdapter(it, dateFormatter)
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val dateFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    inner class LogsViewHolder(val binding: TextRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            TextRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.binding.text.text = "${log.msg}\n\t${dateFormatter.formatDate(log.timestamp)}"
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }
}