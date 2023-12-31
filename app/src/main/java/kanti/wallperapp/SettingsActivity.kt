package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)
	}

	companion object {

		fun startActivity(context: Context) {
			val intent = Intent(context, SettingsActivity::class.java)
			context.startActivity(intent)
		}

	}

}