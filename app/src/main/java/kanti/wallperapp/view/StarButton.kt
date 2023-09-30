package kanti.wallperapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import kanti.wallperapp.R

class StarButton(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {

	private var mChecked: Boolean

	init {
		context.theme.obtainStyledAttributes(
			attrs,
			R.styleable.StarButton,
			0, 0
		).apply {
			try {
				mChecked = getBoolean(R.styleable.StarButton_checked, false)
			} finally {
				recycle()
			}
		}
		scaleType = ScaleType.CENTER_CROP
		updateIcon()
	}

	override fun setOnClickListener(l: OnClickListener?) {
		super.setOnClickListener {
			checked = !checked
			l?.onClick(it)
		}
	}

	var checked: Boolean
		get() {
			return mChecked
		}
		set(value) {
			mChecked = value
			updateIcon()
		}

	private fun updateIcon() {
		if (mChecked) {
			setImageResource(R.drawable.baseline_star_24)
		} else {
			setImageResource(R.drawable.baseline_star_outline_24)
		}
	}

}