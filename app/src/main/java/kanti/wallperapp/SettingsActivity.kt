package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kanti.wallperapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

	private lateinit var view: ActivitySettingsBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivitySettingsBinding.inflate(layoutInflater)
		setContentView(view.root)
	}

	companion object {

		fun startActivity(context: Context) {
			val intent = Intent(context, SettingsActivity::class.java)
			context.startActivity(intent)
		}

	}

}