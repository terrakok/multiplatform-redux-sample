package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.ViewIconListItemBinding
import ch.dreipol.multiplatform.reduxsample.databinding.ViewToggleListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateAddNotification
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateRemindTime
import ch.dreipol.multiplatform.reduxsample.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter

enum class NotificationListTheme(
    @ColorRes val selectableBackgroundColor: Int,
    @ColorRes val textColor: Int,
    @ColorRes val secondaryColor: Int
) {
    BLUE(R.color.blue_clickable_background, R.color.white_disabled_selector, R.color.green_disabled_blue_selector),
    WHITE(R.color.transparent_clickable_background, R.color.blue_disabled_selector, R.color.green_disabled_black_selector);
}

class NotificationListAdapter(
    private val context: Context,
    var remindTimes: List<Pair<RemindTime, Boolean>>,
    var notificationEnabled: Boolean,
    val theme: NotificationListTheme = NotificationListTheme.BLUE,
    private val onRemindTimeSelected: (remindTime: RemindTime) -> Unit = { rootDispatch(UpdateRemindTime(it)) },
    private val onNotificationToggled: (notificationEnabled: Boolean) -> Unit = { rootDispatch(UpdateAddNotification(it)) }
) :
    GroupedListAdapter<Pair<RemindTime, Boolean>, Boolean, Boolean, ViewToggleListItemBinding, ViewIconListItemBinding>() {

    private val textColor = context.resources.getColorStateList(theme.textColor, null)
    private val secondaryColor = context.resources.getColorStateList(theme.secondaryColor, null)

    override fun configureDataItemBinding(binding: ViewIconListItemBinding, model: Pair<RemindTime, Boolean>) {
        binding.root.setBackgroundResource(theme.selectableBackgroundColor)
        binding.root.isEnabled = notificationEnabled
        binding.text.isEnabled = notificationEnabled
        binding.icon.isEnabled = notificationEnabled
        binding.separator.isEnabled = notificationEnabled
        binding.separator.setImageResource(theme.secondaryColor)
        binding.text.text = context.getString(model.first.descriptionKey)
        binding.text.setTextColor(textColor)
        binding.icon.visibility = if (model.second) View.VISIBLE else View.INVISIBLE
        binding.icon.imageTintList = secondaryColor
        binding.root.setOnClickListener { onRemindTimeSelected.invoke(model.first) }
    }

    override fun configureHeaderBinding(binding: ViewToggleListItemBinding, model: Boolean) {
        binding.toggle.setOnCheckedChangeListener { _, _ -> }
        binding.toggle.isChecked = model
        binding.toggle.setOnCheckedChangeListener { _, isChecked -> onNotificationToggled.invoke(isChecked) }
        binding.separator.isEnabled = model
        binding.separator.setImageResource(theme.secondaryColor)
        binding.text.setText(R.string.onboarding_pushes)
        binding.text.setTextColor(textColor)
        binding.icon.visibility = View.GONE
    }

    override fun createDataItemBinding(parent: ViewGroup): ViewIconListItemBinding {
        return ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun createHeaderBinding(parent: ViewGroup): ViewToggleListItemBinding {
        return ViewToggleListItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun getData(): List<Pair<RemindTime, Boolean>> {
        return remindTimes
    }

    override fun getGroupByProperty(dataModel: Pair<RemindTime, Boolean>): Boolean {
        return true
    }

    override fun getHeaderModel(dataModel: Pair<RemindTime, Boolean>): Boolean {
        return notificationEnabled
    }

    override fun getSortComperator(): Comparator<Pair<RemindTime, Boolean>> {
        return Comparator { _, _ -> 0 }
    }
}