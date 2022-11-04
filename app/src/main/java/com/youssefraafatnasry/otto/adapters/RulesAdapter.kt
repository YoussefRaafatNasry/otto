package com.youssefraafatnasry.otto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youssefraafatnasry.otto.adapters.RulesAdapter.RuleHolder
import com.youssefraafatnasry.otto.databinding.ItemRuleBinding
import com.youssefraafatnasry.otto.models.ReplyRule

class RulesAdapter(private val rules: Array<ReplyRule>) : RecyclerView.Adapter<RuleHolder>() {
    inner class RuleHolder(val binding: ItemRuleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRuleBinding.inflate(inflater, parent, false)
        return RuleHolder(binding)
    }

    override fun getItemCount() = rules.size

    override fun onBindViewHolder(holder: RuleHolder, position: Int) {

        val rule = rules[position]
        holder.binding.patternTextView.text = rule.pattern
        holder.binding.replyTextView.text = rule.replies.joinToString("\n— or —\n")

        if (rule.options == null) {
            holder.binding.templateLinearLayout.visibility = View.GONE
        } else {
            holder.binding.templateLinearLayout.visibility = View.VISIBLE
            holder.binding.templateTextView.text = rule.options.toString()
        }

        if (rule.command == null) {
            holder.binding.commandLinearLayout.visibility = View.GONE
        } else {
            holder.binding.commandLinearLayout.visibility = View.VISIBLE
            holder.binding.commandTextView.text = rule.command.name
        }
    }
}