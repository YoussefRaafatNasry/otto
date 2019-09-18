package com.youssefraafatnasry.otto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.youssefraafatnasry.otto.R
import com.youssefraafatnasry.otto.adapters.RulesAdapter.RuleHolder
import com.youssefraafatnasry.otto.models.ReplyRule

class RulesAdapter(private val rules: Array<ReplyRule>) : RecyclerView.Adapter<RuleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleHolder =
        RuleHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rule, parent, false))

    override fun getItemCount() = rules.size

    override fun onBindViewHolder(holder: RuleHolder, position: Int) {

        val rule = rules[position]
        holder.patternTextView.text = rule.pattern
        holder.replyTextView.text   = rule.replies.joinToString("\n— or —\n")

        if (rule.options == null) {
            holder.templateLayout.visibility = View.GONE
        } else {
            holder.templateLayout.visibility = View.VISIBLE
            holder.templateTextView.text = rule.options.toString()
        }

        if (rule.command == null) {
            holder.commandLayout.visibility = View.GONE
        } else {
            holder.commandLayout.visibility = View.VISIBLE
            holder.commandTextView.text = rule.command.name
        }

    }

    inner class RuleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var patternTextView:    TextView = itemView.findViewById(R.id.pattern_text_view)
        internal var replyTextView:      TextView = itemView.findViewById(R.id.reply_text_view)
        internal var templateTextView:   TextView = itemView.findViewById(R.id.template_text_view)
        internal var commandTextView:    TextView = itemView.findViewById(R.id.command_text_view)
        internal var templateLayout: LinearLayout = itemView.findViewById(R.id.template_linear_layout)
        internal var commandLayout:  LinearLayout = itemView.findViewById(R.id.command_linear_layout)
    }

}