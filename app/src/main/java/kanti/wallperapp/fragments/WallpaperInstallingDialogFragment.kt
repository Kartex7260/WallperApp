package kanti.wallperapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kanti.wallperapp.R
import kanti.wallperapp.viewmodel.WallpaperInstallingViewModel

class WallpaperInstallingDialogFragment : DialogFragment() {

	private val viewModel by activityViewModels<WallpaperInstallingViewModel>()

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		return activity?.run {
			AlertDialog.Builder(this)
				.setItems(R.array.dialog_wallpaper_set) { _, which ->
					viewModel.installWallpaperTo(requireContext(), which)
				}
				.create()
		} ?: throw IllegalStateException("Activity cannot be null")
	}

}